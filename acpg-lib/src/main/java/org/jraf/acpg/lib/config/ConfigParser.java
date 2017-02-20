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
package org.jraf.acpg.lib.config;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.jraf.acpg.lib.GeneratorException;

public class ConfigParser {
    private static final Logger LOG = LogManager.getLogger(ConfigParser.class);
    private static final int SYNTAX_VERSION = 4;

    public Config parseConfig(File configFile) throws GeneratorException {
        if (!configFile.exists()) throw new GeneratorException("Could not find a config file at this location: '" + configFile.getAbsolutePath() + "'.");
        ObjectMapper objectMapper = new ObjectMapper();
        Config config;
        try {
            config = objectMapper.readValue(configFile, Config.class);
        } catch (Exception e) {
            throw new GeneratorException("Error while parsing _config.json file", e);
        }
        return config;
    }

    public void validateConfig(Config config) throws GeneratorException {
        // Ensure the input files are compatible with this version of the tool
        if (config.syntaxVersion == null || config.syntaxVersion != SYNTAX_VERSION) {
            throw new GeneratorException(
                    "Invalid 'syntaxVersion' value in configuration: found '" + config.syntaxVersion + "' but expected '" + SYNTAX_VERSION + "'.");
        }

        // Ensure mandatory fields are present
        ensureNotNull(config.packageName, "packageName");
        ensureNotNull(config.providerJavaPackage, "providerJavaPackage");
        ensureNotNull(config.providerClassName, "providerClassName");
        ensureNotNull(config.databaseFileName, "databaseFileName");
        ensureNotNull(config.databaseVersion, "databaseVersion");

        // Default values
        if (config.authority == null) {
            String defaultValue = config.packageName;
            LOG.info("'authority' not set in configuration: assuming '" + defaultValue + "'.");
            config.authority = defaultValue;
        }
        if (config.sqliteOpenHelperClassName == null) {
            String defaultValue = config.providerClassName + "SQLiteOpenHelper";
            LOG.info("'sqliteOpenHelperClassName' not set in configuration: assuming '" + defaultValue + "'.");
            config.sqliteOpenHelperClassName = defaultValue;
        }
        if (config.sqliteOpenHelperCallbacksClassName == null) {
            LOG.info("'sqliteOpenHelperCallbacksClassName' not set in configuration: will use default BaseSQLiteOpenHelperCallbacks.");
        }
        if (config.enableForeignKeys == null) {
            LOG.info("'enableForeignKeys' not set in configuration: assuming false.");
            config.enableForeignKeys = true;
        }
        if (config.useAnnotations == null) {
            LOG.info("'useAnnotations' not set in configuration: assuming false.");
            config.useAnnotations = false;
        }
        if (config.useSupportLibrary == null) {
            LOG.info("'useSupportLibrary' not set in configuration: assuming false.");
            config.useSupportLibrary = false;
        }
        if (config.generateBeans == null) {
            LOG.info("'generateBeans' not set in configuration: assuming true.");
            config.generateBeans = true;
        }
        if (config.debugLogsFieldName == null) {
            LOG.info("'debugLogsFieldName' not set in configuration: assuming 'DEBUG'.");
            config.debugLogsFieldName = "DEBUG";
        }
    }

    private void ensureNotNull(Object value, String fieldName) throws GeneratorException {
        if (value == null) throw new GeneratorException("Mandatory property '" + fieldName + "' not set in configuration.");
    }
}
