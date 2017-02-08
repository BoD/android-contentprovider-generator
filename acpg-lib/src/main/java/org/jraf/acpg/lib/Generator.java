/*
 * This source is part of the
 *      _____  ___   ____
 *  __ / / _ \/ _ | / __/___  _______ _
 * / // / , _/ __ |/ _/_/ _ \/ __/ _ `/
 * \___/_/|_/_/ |_/_/ (_)___/_/  \_, /
 *                              /___/
 * repository.
 *
 * Copyright (C) 2012-2017 Benoit 'BoD' Lubek (BoD@JRAF.org)
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
package org.jraf.acpg.lib;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import org.jraf.acpg.lib.config.Config;
import org.jraf.acpg.lib.config.ConfigParser;
import org.jraf.acpg.lib.model.Entity;
import org.jraf.acpg.lib.model.Field;
import org.jraf.acpg.lib.model.Model;
import org.jraf.acpg.lib.model.parser.ModelParser;

public class Generator {
    private static final Logger LOG = LogManager.getLogger(Generator.class);
    private static String FILE_CONFIG = "_config.json";

    private final File mProviderDir;
    private Config mConfig = null;
    private final Model mModel;
    private Configuration mFreemarkerConfig;

    private Configuration getFreeMarkerConfig() {
        if (mFreemarkerConfig == null) {
            mFreemarkerConfig = new Configuration();
            mFreemarkerConfig.setClassForTemplateLoading(getClass(), "");
            DefaultObjectWrapper objectWrapper = new DefaultObjectWrapper();
            objectWrapper.setExposeFields(true);
            mFreemarkerConfig.setObjectWrapper(objectWrapper);
        }
        return mFreemarkerConfig;
    }

    private void generateColumnsModelsBeans() throws IOException, TemplateException {
        Template columnsTemplate = getFreeMarkerConfig().getTemplate("columns.ftl");
        Template modelTemplate = getFreeMarkerConfig().getTemplate("model.ftl");
        Template beanTemplate = getFreeMarkerConfig().getTemplate("bean.ftl");

        Map<String, Object> root = new HashMap<>();
        root.put("config", mConfig);
        root.put("header", mModel.getHeader());
        root.put("model", mModel);

        // Entities
        for (Entity entity : mModel.getEntities()) {
            root.put("entity", entity);
            File outputDir = new File(mProviderDir, entity.getPackageName());
            outputDir.mkdirs();

            // Columns
            File columnsOutputFile = new File(outputDir, entity.getNameCamelCase() + "Columns.java");
            Writer columnsWriter = new OutputStreamWriter(new FileOutputStream(columnsOutputFile));
            columnsTemplate.process(root, columnsWriter);
            IOUtils.closeQuietly(columnsWriter);

            // Models
            File modelOutputFile = new File(outputDir, entity.getNameCamelCase() + "Model.java");
            Writer modelWriter = new OutputStreamWriter(new FileOutputStream(modelOutputFile));
            modelTemplate.process(root, modelWriter);
            IOUtils.closeQuietly(modelWriter);

            // Beans
            if (mConfig.generateBeans) {
                File beanOutputFile = new File(outputDir, entity.getNameCamelCase() + "Bean.java");
                Writer beanWriter = new OutputStreamWriter(new FileOutputStream(beanOutputFile));
                beanTemplate.process(root, beanWriter);
                IOUtils.closeQuietly(beanWriter);
            }
        }
    }

    private void generateWrappers() throws IOException, TemplateException {
        File baseClassesDir = new File(mProviderDir, "base");
        baseClassesDir.mkdirs();

        Map<String, Object> root = new HashMap<>();
        root.put("config", mConfig);
        root.put("header", mModel.getHeader());
        root.put("model", mModel);

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

        // BaseContentProvider
        template = getFreeMarkerConfig().getTemplate("basecontentprovider.ftl");
        outputFile = new File(baseClassesDir, "BaseContentProvider.java");
        out = new OutputStreamWriter(new FileOutputStream(outputFile));
        template.process(root, out);
        IOUtils.closeQuietly(out);

        // BaseModel
        template = getFreeMarkerConfig().getTemplate("basemodel.ftl");
        outputFile = new File(baseClassesDir, "BaseModel.java");
        out = new OutputStreamWriter(new FileOutputStream(outputFile));
        template.process(root, out);
        IOUtils.closeQuietly(out);

        // Entities
        for (Entity entity : mModel.getEntities()) {
            File entityDir = new File(mProviderDir, entity.getPackageName());
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

    private void generateContentProvider() throws IOException, TemplateException {
        Template template = getFreeMarkerConfig().getTemplate("contentprovider.ftl");
        File outputFile = new File(mProviderDir, mConfig.providerClassName + ".java");
        Writer out = new OutputStreamWriter(new FileOutputStream(outputFile));

        Map<String, Object> root = new HashMap<>();
        root.put("config", mConfig);
        root.put("model", mModel);
        root.put("header", mModel.getHeader());

        template.process(root, out);
    }

    private void generateSqliteOpenHelper() throws IOException, TemplateException {
        Template template = getFreeMarkerConfig().getTemplate("sqliteopenhelper.ftl");
        File outputFile = new File(mProviderDir, mConfig.sqliteOpenHelperClassName + ".java");
        Writer out = new OutputStreamWriter(new FileOutputStream(outputFile));

        Map<String, Object> root = new HashMap<>();
        root.put("config", mConfig);
        root.put("model", mModel);
        root.put("header", mModel.getHeader());

        template.process(root, out);
    }

    private void generateSqliteOpenHelperCallbacks() throws IOException, TemplateException {
        File baseClassesDir = new File(mProviderDir, "base");
        baseClassesDir.mkdirs();
        Template template = getFreeMarkerConfig().getTemplate("basesqliteopenhelpercallbacks.ftl");
        File outputFile = new File(baseClassesDir, "BaseSQLiteOpenHelperCallbacks.java");
        Writer out = new OutputStreamWriter(new FileOutputStream(outputFile));

        Map<String, Object> root = new HashMap<>();
        root.put("config", mConfig);
        root.put("model", mModel);
        root.put("header", mModel.getHeader());

        template.process(root, out);
    }

    private void printManifest() throws IOException, TemplateException {
        Template template = getFreeMarkerConfig().getTemplate("manifest.ftl");
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        Writer writer = new OutputStreamWriter(output);

        Map<String, Object> root = new HashMap<>();
        root.put("config", mConfig);
        root.put("model", mModel);
        root.put("header", mModel.getHeader());
        template.process(root, writer);

        LOG.info("\n\n\nProvider declaration to paste in the AndroidManifest.xml file: " + output.toString("utf-8"));
    }

    public void generate() throws GeneratorException {
        mModel.flagAmbiguousFields();
        try {
            generateColumnsModelsBeans();
            generateWrappers();
            generateContentProvider();
            generateSqliteOpenHelper();
            generateSqliteOpenHelperCallbacks();
            printManifest();
        } catch (TemplateException e) {
            throw new GeneratorException("Problem while generating the code.", e);
        } catch (IOException e) {
            throw new GeneratorException("Problem while trying to read a template file.", e);
        }
    }

    public static Config parseConfig(File inputDir) throws GeneratorException {
        File configFile = new File(inputDir, FILE_CONFIG);
        return new ConfigParser().parseConfig(configFile);
    }

    public Generator(Config config, File entitiesDir, File outputDir) throws GeneratorException {
        // We modify the given config, so clone it first
        try {
            mConfig = (Config) config.clone();
        } catch (CloneNotSupportedException ignored) {}
        new ConfigParser().validateConfig(mConfig);
        mProviderDir = new File(outputDir, mConfig.providerJavaPackage.replace('.', '/'));
        mProviderDir.mkdirs();
        mModel = new ModelParser().parseModel(entitiesDir);
    }
}
