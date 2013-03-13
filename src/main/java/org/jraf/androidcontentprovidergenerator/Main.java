/*
 * This source is part of the
 *      _____  ___   ____
 *  __ / / _ \/ _ | / __/___  _______ _
 * / // / , _/ __ |/ _/_/ _ \/ __/ _ `/
 * \___/_/|_/_/ |_/_/ (_)___/_/  \_, /
 *                              /___/
 * repository.
 * 
 * Copyright 2012 Benoit 'BoD' Lubek (BoD@JRAF.org).  All Rights Reserved.
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
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.jraf.androidcontentprovidergenerator.model.Constraint;
import org.jraf.androidcontentprovidergenerator.model.Entity;
import org.jraf.androidcontentprovidergenerator.model.Field;
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

            // Fields
            JSONArray fieldsJson = entityJson.getJSONArray("fields");
            int len = fieldsJson.length();
            for (int i = 0; i < len; i++) {
                JSONObject fieldJson = fieldsJson.getJSONObject(i);
                if (Config.LOGD) Log.d(TAG, "fieldJson=" + fieldJson);
                String name = fieldJson.getString(Field.NAME);
                String type = fieldJson.getString(Field.TYPE);
                Field field = new Field(name, type);
                entity.addField(field);
            }

            // Constraints (optional)
            JSONArray constraintsJson = entityJson.optJSONArray("constraints");
            if (constraintsJson != null) {
                len = constraintsJson.length();
                for (int i = 0; i < len; i++) {
                    JSONObject constraintJson = constraintsJson.getJSONObject(i);
                    if (Config.LOGD) Log.d(TAG, "constraintJson=" + constraintJson);
                    String name = constraintJson.getString(Constraint.NAME);
                    String definition = constraintJson.getString(Constraint.DEFINITION);
                    Constraint constraint = new Constraint(name, definition);
                    entity.addConstraint(constraint);
                }
            }

            Model.get().addEntity(entity);
        }
        if (Config.LOGD) Log.d(TAG, Model.get().toString());
    }

    private JSONObject getConfig(File inputDir) throws IOException, JSONException {
        if (mConfig == null) {
            File configFile = new File(inputDir, FILE_CONFIG);
            String fileContents = FileUtils.readFileToString(configFile);
            mConfig = new JSONObject(fileContents);
        }
        return mConfig;
    }

    private void generateColumnsFiles(Arguments arguments) throws IOException, JSONException, TemplateException {
        Template columnsTemplate = getFreeMarkerConfig().getTemplate("columns.ftl");
        JSONObject config = getConfig(arguments.inputDir);
        String providerPackageName = config.getString("providerPackage");

        for (Entity entity : Model.get().getEntities()) {
            File providerPackageDir = new File(arguments.outputDir, providerPackageName.replace('.', '/'));
            providerPackageDir.mkdirs();
            File outputFile = new File(providerPackageDir, entity.getNameCamelCase() + "Columns.java");
            Writer out = new OutputStreamWriter(new FileOutputStream(outputFile));

            Map<String, Object> root = new HashMap<String, Object>();
            root.put("config", getConfig(arguments.inputDir));
            root.put("entity", entity);

            columnsTemplate.process(root, out);
            IOUtils.closeQuietly(out);
        }
    }

    private void generateContentProvider(Arguments arguments) throws IOException, JSONException, TemplateException {
        Template columnsTemplate = getFreeMarkerConfig().getTemplate("contentprovider.ftl");
        JSONObject config = getConfig(arguments.inputDir);
        String providerPackageName = config.getString("providerPackage");
        File providerPackageDir = new File(arguments.outputDir, providerPackageName.replace('.', '/'));
        providerPackageDir.mkdirs();
        File outputFile = new File(providerPackageDir, config.getString("providerClassName") + ".java");
        Writer out = new OutputStreamWriter(new FileOutputStream(outputFile));

        Map<String, Object> root = new HashMap<String, Object>();
        root.put("config", config);
        root.put("model", Model.get());

        columnsTemplate.process(root, out);
    }

    private void generateSqliteHelper(Arguments arguments) throws IOException, JSONException, TemplateException {
        Template columnsTemplate = getFreeMarkerConfig().getTemplate("sqlitehelper.ftl");
        JSONObject config = getConfig(arguments.inputDir);
        String providerPackageName = config.getString("providerPackage");
        File providerPackageDir = new File(arguments.outputDir, providerPackageName.replace('.', '/'));
        providerPackageDir.mkdirs();
        File outputFile = new File(providerPackageDir, config.getString("sqliteHelperClassName") + ".java");
        Writer out = new OutputStreamWriter(new FileOutputStream(outputFile));

        Map<String, Object> root = new HashMap<String, Object>();
        root.put("config", config);
        root.put("model", Model.get());

        columnsTemplate.process(root, out);
    }

    private void go(String[] args) throws IOException, JSONException, TemplateException {
        Arguments arguments = new Arguments();
        JCommander jCommander = new JCommander(arguments, args);
        jCommander.setProgramName("GenerateAndroidProvider");

        if (arguments.help) {
            jCommander.usage();
            return;
        }

        loadModel(arguments.inputDir);
        generateColumnsFiles(arguments);
        generateContentProvider(arguments);
        generateSqliteHelper(arguments);
    }

    public static void main(String[] args) throws Exception {
        new Main().go(args);
    }
}
