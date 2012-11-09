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
package org.jraf.generateandroidprovider;

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
import org.jraf.generateandroidprovider.model.Entity;
import org.jraf.generateandroidprovider.model.Field;
import org.jraf.generateandroidprovider.model.Model;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.beust.jcommander.JCommander;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class Main {
    private static final String TAG = Constants.TAG + Main.class.getSimpleName();

    private static final String FILE_CONFIG = "_config.json";


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
        final File[] jsonFiles = inputDir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return !pathname.getName().startsWith("_") && pathname.getName().endsWith(".json");
            }
        });
        for (final File jsonFile : jsonFiles) {
            if (Config.LOGD) Log.d(TAG, jsonFile.getCanonicalPath());
            final String entityName = FilenameUtils.getBaseName(jsonFile.getCanonicalPath());
            if (Config.LOGD) Log.d(TAG, "entityName=" + entityName);
            final Entity entity = new Entity(entityName);
            final String fileContents = FileUtils.readFileToString(jsonFile);
            final JSONArray jsonArray = new JSONArray(fileContents);
            final int len = jsonArray.length();
            for (int i = 0; i < len; i++) {
                final JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (Config.LOGD) Log.d(TAG, "object=" + jsonObject);
                final String fieldName = jsonObject.getString(Field.NAME);
                final String fieldType = jsonObject.getString(Field.TYPE);
                final Field field = new Field(fieldName, fieldType);
                entity.addField(field);
            }
            Model.get().addEntity(entity);
        }
        if (Config.LOGD) Log.d(TAG, Model.get().toString());
    }

    private JSONObject getConfig(File inputDir) throws IOException, JSONException {
        if (mConfig == null) {
            final File configFile = new File(inputDir, FILE_CONFIG);
            final String fileContents = FileUtils.readFileToString(configFile);
            mConfig = new JSONObject(fileContents);
        }
        return mConfig;
    }

    private void generateColumnsFiles(Arguments arguments) throws IOException, JSONException, TemplateException {
        final Template columnsTemplate = getFreeMarkerConfig().getTemplate("columns.ftl");
        final JSONObject config = getConfig(arguments.inputDir);
        final String packageName = config.getString("package");

        for (final Entity entity : Model.get().getEntities()) {
            final File packageDir = new File(arguments.outputDir, packageName.replace('.', '/'));
            packageDir.mkdirs();
            final File outputFile = new File(packageDir, entity.getNameCamelCase() + "Columns.java");
            final Writer out = new OutputStreamWriter(new FileOutputStream(outputFile));

            final Map<String, Object> root = new HashMap<String, Object>();
            root.put("config", getConfig(arguments.inputDir));
            root.put("entity", entity);

            columnsTemplate.process(root, out);
            IOUtils.closeQuietly(out);
        }
    }

    private void generateContentProvider(Arguments arguments) throws IOException, JSONException, TemplateException {
        final Template columnsTemplate = getFreeMarkerConfig().getTemplate("contentprovider.ftl");
        final JSONObject config = getConfig(arguments.inputDir);
        final String packageName = config.getString("package");
        final File packageDir = new File(arguments.outputDir, packageName.replace('.', '/'));
        packageDir.mkdirs();
        final File outputFile = new File(packageDir, config.getString("providerClassName") + ".java");
        final Writer out = new OutputStreamWriter(new FileOutputStream(outputFile));

        final Map<String, Object> root = new HashMap<String, Object>();
        root.put("config", config);
        root.put("model", Model.get());

        columnsTemplate.process(root, out);
    }

    private void generateSqliteHelper(Arguments arguments) throws IOException, JSONException, TemplateException {
        final Template columnsTemplate = getFreeMarkerConfig().getTemplate("sqlitehelper.ftl");
        final JSONObject config = getConfig(arguments.inputDir);
        final String packageName = config.getString("package");
        final File packageDir = new File(arguments.outputDir, packageName.replace('.', '/'));
        packageDir.mkdirs();
        final File outputFile = new File(packageDir, config.getString("sqliteHelperClassName") + ".java");
        final Writer out = new OutputStreamWriter(new FileOutputStream(outputFile));

        final Map<String, Object> root = new HashMap<String, Object>();
        root.put("config", config);
        root.put("model", Model.get());

        columnsTemplate.process(root, out);
    }

    private void go(String[] args) throws IOException, JSONException, TemplateException {
        final Arguments arguments = new Arguments();
        final JCommander jCommander = new JCommander(arguments, args);
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
