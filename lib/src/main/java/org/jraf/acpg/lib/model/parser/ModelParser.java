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
package org.jraf.acpg.lib.model.parser;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.jraf.acpg.lib.GeneratorException;
import org.jraf.acpg.lib.model.Constraint;
import org.jraf.acpg.lib.model.Entity;
import org.jraf.acpg.lib.model.EnumValue;
import org.jraf.acpg.lib.model.Field;
import org.jraf.acpg.lib.model.ForeignKey;
import org.jraf.acpg.lib.model.Model;

public class ModelParser {
    private static final Logger LOG = LogManager.getLogger(ModelParser.class);

    public Model parseModel(File entitiesDir) throws GeneratorException {
        if (!entitiesDir.exists()) throw new GeneratorException("Could not find the directory at this location: '" + entitiesDir.getAbsolutePath() + "'.");

        Model model = new Model();
        File[] entityFiles = entitiesDir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return !pathname.getName().startsWith("_") && pathname.getName().endsWith(".json");
            }
        });

        // Sort the entity files (lexicographically) so they are always processed in the same order
        Arrays.sort(entityFiles);

        ObjectMapper objectMapper = new ObjectMapper();
        for (File entityFile : entityFiles) {
            LOG.debug(entityFile.getAbsolutePath());
            String entityName = FilenameUtils.getBaseName(entityFile.getAbsolutePath());
            JsonEntity jsonEntity;
            try {
                jsonEntity = objectMapper.readValue(entityFile, JsonEntity.class);
            } catch (Exception e) {
                throw new GeneratorException("Error while parsing file " + entityFile.getAbsolutePath(), e);
            }
            Entity entity = new Entity(entityName, jsonEntity.documentation);

            // Fields
            for (JsonField jsonField : jsonEntity.fields) {
                List<EnumValue> enumValues = new ArrayList<>();
                if (jsonField.enumValues != null) {
                    for (JsonEnumValue jsonEnumValue : jsonField.enumValues) {
                        enumValues.add(new EnumValue(jsonEnumValue.name, jsonEnumValue.documentation));
                    }
                }
                ForeignKey foreignKey = jsonField.foreignKey == null ? null :
                        new ForeignKey(jsonField.foreignKey.table, Field.OnDeleteAction.fromJsonName(jsonField.foreignKey.onDelete));
                boolean isIndex = jsonField.index == null ? false : jsonField.index;
                boolean isNullable = jsonField.nullable == null ? true : jsonField.nullable;
                Field field = new Field(
                        entity,
                        jsonField.name,
                        jsonField.documentation,
                        jsonField.type,
                        false,
                        isIndex,
                        isNullable,
                        false,
                        jsonField.defaultValue,
                        jsonField.enumName,
                        enumValues,
                        foreignKey
                );
                entity.addField(field);
            }

            // ID Field
            String idFieldName;
            if (jsonEntity.idField == null) {
                // Implicit id field
                idFieldName = "_id";
            } else {
                if (jsonEntity.idField.size() != 1) {
                    throw new GeneratorException(
                            "Invalid number of idField '" + jsonEntity.idField + "' value in " + entityFile.getAbsolutePath() + ".");
                }
                idFieldName = jsonEntity.idField.get(0);
            }
            Field idField;
            if ("_id".equals(idFieldName)) {
                // Implicit id field: create a Field named "_id"
                idField = new Field(entity, "_id", "Primary key.", "Long", true, false, false, true, null, null, null, null);
                entity.addField(0, idField);
            } else {
                // Explicit id field (reference)
                idField = entity.getFieldByName(idFieldName);
                if (idField == null) {
                    // Referenced field not found
                    throw new GeneratorException("Invalid idField: '" + idFieldName + "' not found " + entityFile.getAbsolutePath() + ".");
                }
                if (idField.getType() != Field.Type.INTEGER && idField.getType() != Field.Type.LONG && idField.getType() != Field.Type.DATE
                        && idField.getType() != Field.Type.ENUM) {
                    // Invalid type
                    throw new GeneratorException("Invalid idField type " + idField.getType() + " in " + entityFile.getAbsolutePath() + "."
                            + "  It must be Integer, Long, Date or Enum.");
                }
                if (idField.getIsNullable()) {
                    // Referenced field is nullable
                    throw new GeneratorException("Invalid idField: '" + idFieldName + "' must not be nullable in " + entityFile.getAbsolutePath() + ".");
                }
                if (!idField.getIsIndex()) {
                    // Referenced field is not an index
                    throw new GeneratorException("Invalid idField: '" + idFieldName + "' must be an index in " + entityFile.getAbsolutePath() + ".");
                }
                idField.setIsId(true);
            }

            // Constraints (optional)
            if (jsonEntity.constraints != null) {
                for (JsonConstraint jsonConstraint : jsonEntity.constraints) {
                    entity.addConstraint(new Constraint(jsonConstraint.name, jsonConstraint.definition));
                }
            }

            // Default order (optional)
            if (jsonEntity.defaultOrder != null) {
                String[] defaultOrderParts = jsonEntity.defaultOrder.split(",");
                for (String defaultOrderPart : defaultOrderParts) {
                    defaultOrderPart = defaultOrderPart.trim();
                    String[] defaultOrderPartsParts = defaultOrderPart.split(" ");
                    if (defaultOrderPartsParts.length != 1 && defaultOrderPartsParts.length != 2) {
                        throw new GeneratorException(
                                "Invalid syntax in defaultOrder!  Valid syntax is 'columnName [ASC|DESC] [,columnName [ASC|DESC]]*'");
                    }
                    if (defaultOrderPartsParts.length == 2) {
                        String ascDesc = defaultOrderPartsParts[1].trim().toLowerCase(Locale.US);
                        if (!Entity.DEFAULT_ORDER_ASC.equals(ascDesc) && !Entity.DEFAULT_ORDER_DESC.equals(ascDesc))
                            throw new GeneratorException(
                                    "Invalid syntax in defaultOrder, near '" + ascDesc +
                                            "'!  Valid syntax is 'columnName [ASC|DESC] [,columnName [ASC|DESC]]*'");

                        String columnName = defaultOrderPartsParts[0].trim();
                        Field field = entity.getFieldByName(columnName);
                        if (field == null) {
                            throw new GeneratorException("Invalid defaultOrder!  Could not find column '" + columnName + "'");
                        }

                        entity.addSortOrder(field, ascDesc);
                    } else {
                        String columnName = defaultOrderPartsParts[0].trim();
                        Field field = entity.getFieldByName(columnName);
                        if (field == null) {
                            throw new GeneratorException("Invalid defaultOrder!  Could not find column '" + columnName + "'");
                        }

                        entity.addSortOrder(field, "asc");
                    }
                }
            }

            model.addEntity(entity);
        }
        // Header (optional)
        File headerFile = new File(entitiesDir, "header.txt");
        if (headerFile.exists()) {
            String header;
            try {
                header = FileUtils.readFileToString(headerFile).trim();
            } catch (IOException e) {
                throw new GeneratorException("Could not read from header.txt file", e);
            }
            model.setHeader(header);
        }

        return model;
    }
}
