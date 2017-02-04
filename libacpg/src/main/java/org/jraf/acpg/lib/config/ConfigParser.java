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
import java.io.FileReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import org.jraf.acpg.lib.GeneratorException;

public class ConfigParser {
    private static final Logger LOG = LogManager.getLogger(ConfigParser.class);
    private static final int SYNTAX_VERSION = 3;

    public Config parseConfig(Gson gson, File configFile) throws GeneratorException {
        if (!configFile.exists()) throw new IllegalArgumentException("Could not find a config file at this location: '" + configFile.getAbsolutePath() + "'.");
        Config config;
        try (JsonReader jsonReader = new JsonReader(new FileReader(configFile))) {
            config = gson.fromJson(jsonReader, Config.class);
        } catch (Exception e) {
            throw new GeneratorException("Error while parsing _config.json file", e);
        }
        validateConfig(config);
        return config;
    }

    private void validateConfig(Config config) throws GeneratorException {
        // Ensure the input files are compatible with this version of the tool
        if (config.syntaxVersion != SYNTAX_VERSION) {
            throw new GeneratorException(
                    "Invalid 'syntaxVersion' value in _config.json: found '" + config.syntaxVersion + "' but expected '" + SYNTAX_VERSION + "'.");
        }

        // Ensure mandatory fields are present
        ensureNotNull(config.projectPackageId, "projectPackageId");
        ensureNotNull(config.providerJavaPackage, "providerJavaPackage");
        ensureNotNull(config.providerClassName, "providerClassName");
        ensureNotNull(config.sqliteOpenHelperClassName, "sqliteOpenHelperClassName");
        ensureNotNull(config.sqliteOpenHelperCallbacksClassName, "sqliteOpenHelperCallbacksClassName");
        ensureNotNull(config.authority, "authority");
        ensureNotNull(config.databaseFileName, "databaseFileName");
        ensureNotNull(config.databaseVersion, "databaseVersion");
        ensureNotNull(config.enableForeignKeys, "enableForeignKeys");
        ensureNotNull(config.useAnnotations, "useAnnotations");

        // Default values
        if (config.useSupportLibrary == null) {
            LOG.warn("Could not find 'useSupportLibrary' field in _config.json: assuming false.");
            config.useSupportLibrary = false;
        }
        if (config.generateBeans == null) {
            LOG.warn("Could not find 'generateBeans' field in _config.json: assuming true.");
            config.generateBeans = true;
        }
    }

    private void ensureNotNull(Object value, String fieldName) throws GeneratorException {
        if (value == null) throw new GeneratorException("Could not find mandatory '" + fieldName + "' field in _config.json.");
    }
}
