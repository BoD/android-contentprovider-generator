/*
 * This source is part of the
 *      _____  ___   ____
 *  __ / / _ \/ _ | / __/___  _______ _
 * / // / , _/ __ |/ _/_/ _ \/ __/ _ `/
 * \___/_/|_/_/ |_/_/ (_)___/_/  \_, /
 *                              /___/
 * repository.
 *
 * Copyright (C) 2012-2014 Benoit 'BoD' Lubek (BoD@JRAF.org)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.jraf.androidcontentprovidergenerator;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.jraf.androidcontentprovidergenerator.model.Constraint;
import org.jraf.androidcontentprovidergenerator.model.Entity;
import org.jraf.androidcontentprovidergenerator.model.EnumValue;
import org.jraf.androidcontentprovidergenerator.model.Field;
import org.jraf.androidcontentprovidergenerator.model.Field.OnDeleteAction;
import org.jraf.androidcontentprovidergenerator.model.ForeignKey;
import org.jraf.androidcontentprovidergenerator.model.Model;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.beust.jcommander.JCommander;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class Main {
    private static String TAG = Constants.TAG + Main.class.getSimpleName();

    private static String FILE_CONFIG = "_config.json";

    public static class Json {
        public static final String SYNTAX_VERSION = "syntaxVersion";
        public static final String SYNTAX_VERSION_LEGACY = "toolVersion";
        public static final String PROJECT_PACKAGE_ID = "projectPackageId";
        public static final String PROVIDER_JAVA_PACKAGE = "providerJavaPackage";
        public static final String PROVIDER_CLASS_NAME = "providerClassName";
        public static final String SQLITE_OPEN_HELPER_CLASS_NAME = "sqliteOpenHelperClassName";
        public static final String SQLITE_OPEN_HELPER_CALLBACKS_CLASS_NAME = "sqliteOpenHelperCallbacksClassName";
        public static final String AUTHORITY = "authority";
        public static final String DATABASE_FILE_NAME = "databaseFileName";
        public static final String DATABASE_VERSION = "databaseVersion";
        public static final String ENABLE_FOREIGN_KEY = "enableForeignKeys";
    }

    private Configuration mFreemarkerConfig;
    private JSONObject mConfig;

    private Configuration getFreeMarkerConfig() {
        if (mFreemarkerConfig == null) {
            mFreemarkerConfig = new Configuration();
            mFreemarkerConfig.setClassForTemplateLoading(getClass(), "");
            mFreemarkerConfig.setObjectWrapper(new DefaultObjectWrapper());
        }
        return mFreemarkerConfig;
    }

    private void loadModel(File inputDir) throws IOException, JSONException {
        File[] entityFiles = inputDir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return !pathname.getName().startsWith("_") && pathname.getName().endsWith(".json");
            }
        });
        for (File entityFile : entityFiles) {
            if (Config.LOGD) Log.d(TAG, entityFile.getCanonicalPath());
            String entityName = FilenameUtils.getBaseName(entityFile.getCanonicalPath());
            if (Config.LOGD) Log.d(TAG, "entityName=" + entityName);
            Entity entity = new Entity(entityName);
            String fileContents = FileUtils.readFileToString(entityFile);
            JSONObject entityJson = new JSONObject(fileContents);

            // Implicit _id field
            Field field = new Field(entity, "_id", "Long", true, false, false, null, null, null, null);
            entity.addField(field);

            // Fields
            JSONArray fieldsJson = entityJson.getJSONArray("fields");
            int len = fieldsJson.length();
            for (int i = 0; i < len; i++) {
                JSONObject fieldJson = fieldsJson.getJSONObject(i);
                if (Config.LOGD) Log.d(TAG, "fieldJson=" + fieldJson);
                String name = fieldJson.getString(Field.Json.NAME);
                String type = fieldJson.getString(Field.Json.TYPE);
                boolean isIndex = fieldJson.optBoolean(Field.Json.INDEX, false);
                boolean isNullable = fieldJson.optBoolean(Field.Json.NULLABLE, true);
                String defaultValue = fieldJson.optString(Field.Json.DEFAULT_VALUE);
                String defaultValueLegacy = fieldJson.optString(Field.Json.DEFAULT_VALUE_LEGACY);
                String enumName = fieldJson.optString(Field.Json.ENUM_NAME);
                JSONArray enumValuesJson = fieldJson.optJSONArray(Field.Json.ENUM_VALUES);
                List<EnumValue> enumValues = new ArrayList<>();
                if (enumValuesJson != null) {
                    int enumLen = enumValuesJson.length();
                    for (int j = 0; j < enumLen; j++) {
                        Object enumValue = enumValuesJson.get(j);
                        if (enumValue instanceof String) {
                            // Name only
                            enumValues.add(new EnumValue((String) enumValue, null));
                        } else {
                            // Name and Javadoc
                            JSONObject enumValueJson = (JSONObject) enumValue;
                            String enumValueName = (String) enumValueJson.keys().next();
                            String enumValueJavadoc = enumValueJson.getString(enumValueName);
                            enumValues.add(new EnumValue(enumValueName, enumValueJavadoc));
                        }
                    }
                }
                JSONObject foreignKeyJson = fieldJson.optJSONObject(Field.Json.FOREIGN_KEY);
                ForeignKey foreignKey = null;
                if (foreignKeyJson != null) {
                    String table = foreignKeyJson.getString(Field.Json.FOREIGN_KEY_TABLE);
                    String foreignKeyField = foreignKeyJson.getString(Field.Json.FOREIGN_KEY_FIELD);
                    OnDeleteAction onDeleteAction = OnDeleteAction.fromJsonName(foreignKeyJson.getString(Field.Json.FOREIGN_KEY_ON_DELETE_ACTION));
                    foreignKey = new ForeignKey(table, foreignKeyField, onDeleteAction);
                }
                field = new Field(entity, name, type, false, isIndex, isNullable, defaultValue != null ? defaultValue : defaultValueLegacy, enumName,
                        enumValues, foreignKey);
                entity.addField(field);
            }

            // Constraints (optional)
            JSONArray constraintsJson = entityJson.optJSONArray("constraints");
            if (constraintsJson != null) {
                len = constraintsJson.length();
                for (int i = 0; i < len; i++) {
                    JSONObject constraintJson = constraintsJson.getJSONObject(i);
                    if (Config.LOGD) Log.d(TAG, "constraintJson=" + constraintJson);
                    String name = constraintJson.getString(Constraint.Json.NAME);
                    String definition = constraintJson.getString(Constraint.Json.DEFINITION);
                    Constraint constraint = new Constraint(name, definition);
                    entity.addConstraint(constraint);
                }
            }

            Model.get().addEntity(entity);
        }
        // Header (optional)
        File headerFile = new File(inputDir, "header.txt");
        if (headerFile.exists()) {
            String header = FileUtils.readFileToString(headerFile).trim();
            Model.get().setHeader(header);
        }
        if (Config.LOGD) Log.d(TAG, Model.get().toString());
    }

    private JSONObject getConfig(File inputDir) throws IOException, JSONException {
        if (mConfig == null) {
            File configFile = new File(inputDir, FILE_CONFIG);
            String fileContents = FileUtils.readFileToString(configFile);
            mConfig = new JSONObject(fileContents);
        }

        validateConfig();

        return mConfig;
    }

    private void validateConfig() {
        // Ensure the input files are compatible with this version of the tool
        String syntaxVersion;
        try {
            syntaxVersion = mConfig.getString(Json.SYNTAX_VERSION);
        } catch (JSONException e) {
            try {
                // Try the old name of this attribute
                syntaxVersion = mConfig.getString(Json.SYNTAX_VERSION_LEGACY);
            } catch (JSONException e2) {
                throw new IllegalArgumentException("Could not find '" + Json.SYNTAX_VERSION
                        + "' field in _config.json, which is mandatory and must be equal to '" + Constants.SYNTAX_VERSION + "'.");
            }
        }
        if (!syntaxVersion.startsWith(Constants.SYNTAX_VERSION)) {
            throw new IllegalArgumentException("Invalid '" + Json.SYNTAX_VERSION + "' value in _config.json: found '" + syntaxVersion + "' but expected '"
                    + Constants.SYNTAX_VERSION + "'.");
        }

        // Ensure mandatory fields are present
        ensureString(Json.PROJECT_PACKAGE_ID);
        ensureString(Json.PROVIDER_JAVA_PACKAGE);
        ensureString(Json.PROVIDER_CLASS_NAME);
        ensureString(Json.SQLITE_OPEN_HELPER_CLASS_NAME);
        ensureString(Json.SQLITE_OPEN_HELPER_CALLBACKS_CLASS_NAME);
        ensureString(Json.AUTHORITY);
        ensureString(Json.DATABASE_FILE_NAME);
        ensureInt(Json.DATABASE_VERSION);
        ensureBoolean(Json.ENABLE_FOREIGN_KEY);
    }

    private void ensureString(String field) {
        try {
            mConfig.getString(field);
        } catch (JSONException e) {
            throw new IllegalArgumentException("Could not find '" + field + "' field in _config.json, which is mandatory and must be a string.");
        }
    }

    private void ensureBoolean(String field) {
        try {
            mConfig.getBoolean(field);
        } catch (JSONException e) {
            throw new IllegalArgumentException("Could not find '" + field + "' field in _config.json, which is mandatory and must be a boolean.");
        }
    }

    private void ensureInt(String field) {
        try {
            mConfig.getInt(field);
        } catch (JSONException e) {
            throw new IllegalArgumentException("Could not find '" + field + "' field in _config.json, which is mandatory and must be an int.");
        }
    }

    private void generateColumns(Arguments arguments) throws IOException, JSONException, TemplateException {
        Template template = getFreeMarkerConfig().getTemplate("columns.ftl");
        JSONObject config = getConfig(arguments.inputDir);
        String providerJavaPackage = config.getString(Json.PROVIDER_JAVA_PACKAGE);

        File providerDir = new File(arguments.outputDir, providerJavaPackage.replace('.', '/'));
        Map<String, Object> root = new HashMap<>();
        root.put("config", getConfig(arguments.inputDir));
        root.put("header", Model.get().getHeader());

        // Entities
        for (Entity entity : Model.get().getEntities()) {
            File outputDir = new File(providerDir, entity.getPackageName());
            outputDir.mkdirs();
            File outputFile = new File(outputDir, entity.getNameCamelCase() + "Columns.java");
            Writer out = new OutputStreamWriter(new FileOutputStream(outputFile));

            root.put("entity", entity);

            template.process(root, out);
            IOUtils.closeQuietly(out);
        }
    }

    private void generateWrappers(Arguments arguments) throws IOException, JSONException, TemplateException {
        JSONObject config = getConfig(arguments.inputDir);
        String providerJavaPackage = config.getString(Json.PROVIDER_JAVA_PACKAGE);
        File providerDir = new File(arguments.outputDir, providerJavaPackage.replace('.', '/'));
        File baseClassesDir = new File(providerDir, "base");
        baseClassesDir.mkdirs();

        Map<String, Object> root = new HashMap<>();
        root.put("config", getConfig(arguments.inputDir));
        root.put("header", Model.get().getHeader());

        // AbstractCursor
        Template template = getFreeMarkerConfig().getTemplate("abstractcursor.ftl");
        File outputFile = new File(baseClassesDir, "AbstractCursor.java");
        Writer out = new OutputStreamWriter(new FileOutputStream(outputFile));
        template.process(root, out);
        IOUtils.closeQuietly(out);

        // AbstractContentValuesWrapper
        template = getFreeMarkerConfig().getTemplate("abstractcontentvalues.ftl");
        outputFile = new File(baseClassesDir, "AbstractContentValues.java");
        out = new OutputStreamWriter(new FileOutputStream(outputFile));
        template.process(root, out);
        IOUtils.closeQuietly(out);

        // AbstractSelection
        template = getFreeMarkerConfig().getTemplate("abstractselection.ftl");
        outputFile = new File(baseClassesDir, "AbstractSelection.java");
        out = new OutputStreamWriter(new FileOutputStream(outputFile));
        template.process(root, out);
        IOUtils.closeQuietly(out);

        // Entities
        for (Entity entity : Model.get().getEntities()) {
            File entityDir = new File(providerDir, entity.getPackageName());
            entityDir.mkdirs();

            // Cursor wrapper
            outputFile = new File(entityDir, entity.getNameCamelCase() + "Cursor.java");
            out = new OutputStreamWriter(new FileOutputStream(outputFile));
            root.put("entity", entity);
            template = getFreeMarkerConfig().getTemplate("cursor.ftl");
            template.process(root, out);
            IOUtils.closeQuietly(out);

            // ContentValues wrapper
            outputFile = new File(entityDir, entity.getNameCamelCase() + "ContentValues.java");
            out = new OutputStreamWriter(new FileOutputStream(outputFile));
            root.put("entity", entity);
            template = getFreeMarkerConfig().getTemplate("contentvalues.ftl");
            template.process(root, out);
            IOUtils.closeQuietly(out);

            // Selection builder
            outputFile = new File(entityDir, entity.getNameCamelCase() + "Selection.java");
            out = new OutputStreamWriter(new FileOutputStream(outputFile));
            root.put("entity", entity);
            template = getFreeMarkerConfig().getTemplate("selection.ftl");
            template.process(root, out);
            IOUtils.closeQuietly(out);

            // Enums (if any)
            for (Field field : entity.getFields()) {
                if (field.isEnum()) {
                    outputFile = new File(entityDir, field.getEnumName() + ".java");
                    out = new OutputStreamWriter(new FileOutputStream(outputFile));
                    root.put("entity", entity);
                    root.put("field", field);
                    template = getFreeMarkerConfig().getTemplate("enum.ftl");
                    template.process(root, out);
                    IOUtils.closeQuietly(out);
                }
            }
        }
    }

    private void generateContentProvider(Arguments arguments) throws IOException, JSONException, TemplateException {
        Template template = getFreeMarkerConfig().getTemplate("contentprovider.ftl");
        JSONObject config = getConfig(arguments.inputDir);
        String providerJavaPackage = config.getString(Json.PROVIDER_JAVA_PACKAGE);
        File providerDir = new File(arguments.outputDir, providerJavaPackage.replace('.', '/'));
        providerDir.mkdirs();
        File outputFile = new File(providerDir, config.getString(Json.PROVIDER_CLASS_NAME) + ".java");
        Writer out = new OutputStreamWriter(new FileOutputStream(outputFile));

        Map<String, Object> root = new HashMap<>();
        root.put("config", config);
        root.put("model", Model.get());
        root.put("header", Model.get().getHeader());

        template.process(root, out);
    }

    private void generateSqliteOpenHelper(Arguments arguments) throws IOException, JSONException, TemplateException {
        Template template = getFreeMarkerConfig().getTemplate("sqliteopenhelper.ftl");
        JSONObject config = getConfig(arguments.inputDir);
        String providerJavaPackage = config.getString(Json.PROVIDER_JAVA_PACKAGE);
        File providerDir = new File(arguments.outputDir, providerJavaPackage.replace('.', '/'));
        providerDir.mkdirs();
        File outputFile = new File(providerDir, config.getString(Json.SQLITE_OPEN_HELPER_CLASS_NAME) + ".java");
        Writer out = new OutputStreamWriter(new FileOutputStream(outputFile));

        Map<String, Object> root = new HashMap<>();
        root.put("config", config);
        root.put("model", Model.get());
        root.put("header", Model.get().getHeader());

        template.process(root, out);
    }

    private void generateSqliteOpenHelperCallbacks(Arguments arguments) throws IOException, JSONException, TemplateException {
        Template template = getFreeMarkerConfig().getTemplate("sqliteopenhelpercallbacks.ftl");
        JSONObject config = getConfig(arguments.inputDir);
        String providerJavaPackage = config.getString(Json.PROVIDER_JAVA_PACKAGE);
        File providerDir = new File(arguments.outputDir, providerJavaPackage.replace('.', '/'));
        providerDir.mkdirs();
        File outputFile = new File(providerDir, config.getString(Json.SQLITE_OPEN_HELPER_CALLBACKS_CLASS_NAME) + ".java");
        if (outputFile.exists()) {
            if (Config.LOGD) Log.d(TAG, "generateSqliteOpenHelperCallbacks Open helper callbacks class already exists: skip");
            return;
        }
        Writer out = new OutputStreamWriter(new FileOutputStream(outputFile));

        Map<String, Object> root = new HashMap<>();
        root.put("config", config);
        root.put("model", Model.get());
        root.put("header", Model.get().getHeader());

        template.process(root, out);
    }

    private void printManifest(Arguments arguments) throws IOException, JSONException, TemplateException {
        Template template = getFreeMarkerConfig().getTemplate("manifest.ftl");
        JSONObject config = getConfig(arguments.inputDir);
        Writer out = new OutputStreamWriter(System.out);

        Map<String, Object> root = new HashMap<>();
        root.put("config", config);
        root.put("model", Model.get());
        root.put("header", Model.get().getHeader());

        System.out.println("\nProvider declaration to paste in the AndroidManifest.xml file: ");
        template.process(root, out);
    }

    private void go(String[] args) throws IOException, JSONException, TemplateException {
        Arguments arguments = new Arguments();
        JCommander jCommander = new JCommander(arguments, args);
        jCommander.setProgramName("GenerateAndroidProvider");

        if (arguments.help) {
            jCommander.usage();
            return;
        }

        getConfig(arguments.inputDir);

        loadModel(arguments.inputDir);
        generateColumns(arguments);
        generateWrappers(arguments);
        generateContentProvider(arguments);
        generateSqliteOpenHelper(arguments);
        generateSqliteOpenHelperCallbacks(arguments);

        printManifest(arguments);
    }

    public static void main(String[] args) throws Exception {
        new Main().go(args);
    }
}
