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
package org.jraf.androidcontentprovidergenerator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.WordUtils;

public class Entity {
    private static final String CONCAT = "res.tablesWithJoins += ";
    private static final String HAS_COLUMNS = ".hasColumns(projection)";
    private static final String OPEN_BRACE = ") {\n";
    private static final String IF = "if (";
    private static final String OR = " || ";
    private static final String INDENT1 = "                ";
    private static final String INDENT2 = "                    ";
    private static final String PLUS = " + ";
    private static final String COLUMNS = "Columns";
    private static final String TABLE_NAME = ".TABLE_NAME";
    private static final String LEFT_OUTER_JOIN = "\" LEFT OUTER JOIN \"";
    private static final String ON = "\" ON \"";
    private static final String EQUALS = "\"=\"";
    private static final String DOT = "\".\"";


    private static final Map<String, Entity> ALL_ENTITIES = new HashMap<>();

    private final String mName;
    private final List<Field> mFields = new ArrayList<>();
    private final List<Constraint> mConstraints = new ArrayList<>();

    public Entity(String name) {
        mName = name;
        ALL_ENTITIES.put(name, this);
    }

    public void addField(Field field) {
        mFields.add(field);
    }

    public List<Field> getFields() {
        return Collections.unmodifiableList(mFields);
    }

    public List<Field> getFieldsIncludingJoins(boolean isForeign) {
        List<Field> res = new ArrayList<>();
        for (Field field : mFields) {
			field.setForeign(isForeign);
			res.add(field);
		}

        for (Field field : getFields()) {
            ForeignKey foreignKey = field.getForeignKey();
            if (foreignKey == null) continue;

            // Recurse
            res.addAll(foreignKey.getEntity().getFieldsIncludingJoins(true));
        }

        return res;
    }

    public List<Entity> getJoinedEntities() {
        List<Entity> res = new ArrayList<>();

        for (Field field : getFields()) {
            ForeignKey foreignKey = field.getForeignKey();
            if (foreignKey == null) continue;

            // Recurse
            res.add(foreignKey.getEntity());
            res.addAll(foreignKey.getEntity().getJoinedEntities());
        }

        return res;
    }

    public Field getFieldByName(String fieldName) {
        for (Field field : getFields()) {
            if (fieldName.equals(field.getNameLowerCase())) return field;
        }
        return null;
    }

    public void addConstraint(Constraint constraint) {
        mConstraints.add(constraint);
    }

    public List<Constraint> getConstraints() {
        return Collections.unmodifiableList(mConstraints);
    }

    public String getNameCamelCase() {
        return WordUtils.capitalizeFully(mName, new char[] { '_' }).replaceAll("_", "");
    }

    public String getPackageName() {
        return getNameLowerCase().replace("_", "");
    }

    public String getNameLowerCase() {
        return mName.toLowerCase(Locale.US);
    }

    public String getNameUpperCase() {
        return mName.toUpperCase(Locale.US);
    }

    public String getAllJoinedTableNames() {
        StringBuilder res = new StringBuilder(getNameCamelCase());
        res.append(COLUMNS);
        res.append(TABLE_NAME);
        res.append(";");

        addAllJoinedClauses(this, res);
        return res.toString();
    }

    private static void addAllJoinedClauses(Entity entity, StringBuilder res) {
        List<Field> fields = entity.getFields();
        for (Field field : fields) {
            ForeignKey foreignKey = field.getForeignKey();
            if (foreignKey == null) continue;

            res.append("\n");
            res.append(INDENT1);
            res.append(IF);

            getHasColumnClauses(res, foreignKey.getEntity());

            res.append(OPEN_BRACE);
            res.append(INDENT2);
            res.append(CONCAT);
            res.append(LEFT_OUTER_JOIN);
            res.append(PLUS);
            res.append(foreignKey.getEntity().getNameCamelCase());
            res.append(COLUMNS);
            res.append(TABLE_NAME);
            res.append(PLUS);
            res.append(ON);
            res.append(PLUS);

            res.append(getQualifiedColumnName(entity, field));

            res.append(PLUS);
            res.append(EQUALS);
            res.append(PLUS);

            res.append(getQualifiedColumnName(foreignKey.getEntity(), foreignKey.getField()));
            res.append(";\n");
            res.append(INDENT1);
            res.append("}");

            // Recurse
            addAllJoinedClauses(foreignKey.getEntity(), res);
        }
    }

    private static void getHasColumnClauses(StringBuilder res, Entity entity) {
        res.append(entity.getNameCamelCase());
        res.append(COLUMNS);
        res.append(HAS_COLUMNS);

        for (Field field : entity.getFields()) {
            ForeignKey foreignKey = field.getForeignKey();
            if (foreignKey == null) continue;

            res.append(OR);
            // Recurse
            getHasColumnClauses(res, foreignKey.getEntity());
        }
    }

    private static String getQualifiedColumnName(Entity entity, Field field) {
        StringBuilder res = new StringBuilder();
        res.append(entity.getNameCamelCase());
        res.append(COLUMNS);
        res.append(TABLE_NAME);
        res.append(PLUS);
        res.append(DOT);
        res.append(PLUS);
        res.append(entity.getNameCamelCase());
        res.append(COLUMNS);
        res.append(".");
        res.append(field.getNameUpperCase());
        return res.toString();
    }

    @Override
    public String toString() {
        return "Entity [mName=" + mName + ", mFields=" + mFields + ", mConstraints=" + mConstraints + "]";
    }

    public static Entity getByName(String entityName) {
        return ALL_ENTITIES.get(entityName);
    }
}
