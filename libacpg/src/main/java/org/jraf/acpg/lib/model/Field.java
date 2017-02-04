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
package org.jraf.acpg.lib.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.WordUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.jraf.acpg.lib.GeneratorException;

public class Field {
    private static final Logger LOG = LogManager.getLogger(Field.class);

    private static final String TYPE_STRING = "String";
    private static final String TYPE_INTEGER = "Integer";
    private static final String TYPE_LONG = "Long";
    private static final String TYPE_FLOAT = "Float";
    private static final String TYPE_DOUBLE = "Double";
    private static final String TYPE_BOOLEAN = "Boolean";
    private static final String TYPE_DATE = "Date";
    private static final String TYPE_BYTE_ARRAY = "byte[]";
    private static final String TYPE_ENUM = "enum";

    private static final String ON_DELETE_ACTION_NO_ACTION = "NO ACTION";
    private static final String ON_DELETE_ACTION_RESTRICT = "RESTRICT";
    private static final String ON_DELETE_ACTION_SET_NULL = "SET NULL";
    private static final String ON_DELETE_ACTION_SET_DEFAULT = "SET DEFAULT";
    private static final String ON_DELETE_ACTION_CASCADE = "CASCADE";

    public enum Type {
        // @formatter:off
        STRING(TYPE_STRING, "TEXT", String.class, String.class),
        INTEGER(TYPE_INTEGER, "INTEGER", Integer.class, int.class),
        LONG(TYPE_LONG, "INTEGER", Long.class, long.class),
        FLOAT(TYPE_FLOAT, "REAL", Float.class, float.class),
        DOUBLE(TYPE_DOUBLE, "REAL", Double.class, double.class),
        BOOLEAN(TYPE_BOOLEAN, "INTEGER", Boolean.class, boolean.class),
        DATE(TYPE_DATE, "INTEGER", Date.class, Date.class),
        BYTE_ARRAY(TYPE_BYTE_ARRAY, "BLOB", byte[].class, byte[].class),
        ENUM(TYPE_ENUM, "INTEGER", null, null),
        // @formatter:on
        ;

        private String mJsonName;
        private String mSqlType;
        private Class<?> mNullableJavaType;
        private Class<?> mNotNullableJavaType;

        private Type(String jsonName, String sqlType, Class<?> nullableJavaType, Class<?> notNullableJavaType) {
            mJsonName = jsonName;
            mSqlType = sqlType;
            mNullableJavaType = nullableJavaType;
            mNotNullableJavaType = notNullableJavaType;
            sTypeJsonNames.put(jsonName, this);
        }

        public static Type fromJsonName(String jsonName) throws GeneratorException {
            Type res = sTypeJsonNames.get(jsonName);
            if (res == null) throw new GeneratorException("The type '" + jsonName + "' is unknown");
            return res;
        }

        public String getSqlType() {
            return mSqlType;
        }

        public Class<?> getNullableJavaType() {
            return mNullableJavaType;
        }

        public Class<?> getNotNullableJavaType() {
            return mNotNullableJavaType;
        }

        public boolean hasNotNullableJavaType() {
            if (this == ENUM) return false;
            return !mNullableJavaType.equals(mNotNullableJavaType);
        }
    }

    public enum OnDeleteAction {
        // @formatter:off
        NO_ACTION(ON_DELETE_ACTION_NO_ACTION),
        RESTRICT(ON_DELETE_ACTION_RESTRICT),
        SET_NULL(ON_DELETE_ACTION_SET_NULL),
        SET_DEFAULT(ON_DELETE_ACTION_SET_DEFAULT),
        CASCADE(ON_DELETE_ACTION_CASCADE),
        // @formatter:on
        ;

        private OnDeleteAction(String jsonName) {
            sOnDeleteActionJsonNames.put(jsonName, this);
        }

        public String toSql() {
            return name().replace('_', ' ');
        }

        public static OnDeleteAction fromJsonName(String jsonName) throws GeneratorException {
            OnDeleteAction res = sOnDeleteActionJsonNames.get(jsonName);
            if (res == null) throw new GeneratorException("The onDelete value '" + jsonName + "' is unknown");
            return res;
        }
    }

    private static HashMap<String, Type> sTypeJsonNames = new HashMap<>();
    private static HashMap<String, OnDeleteAction> sOnDeleteActionJsonNames = new HashMap<>();

    private final Entity mEntity;
    private final String mName;
    private final String mDocumentation;
    private final Type mType;
    private boolean mIsId;
    private final boolean mIsIndex;
    private final boolean mIsNullable;
    private final boolean mIsAutoIncrement;
    private final String mDefaultValue;
    private final String mEnumName;
    private final List<EnumValue> mEnumValues = new ArrayList<>();
    private final ForeignKey mForeignKey;
    private boolean mIsForeign;
    private boolean mIsAmbiguous;
    private Field mOriginalField;
    private String mPath;

    public Field(Entity entity, String name, String documentation, String type, boolean isId, boolean isIndex, boolean isNullable, boolean isAutoIncrement,
                 String defaultValue, String enumName, List<EnumValue> enumValues, ForeignKey foreignKey) throws GeneratorException {
        mEntity = entity;
        mName = name;
        mDocumentation = documentation;
        mType = Type.fromJsonName(type);
        mIsId = isId;
        mIsIndex = isIndex;
        mIsNullable = isNullable;
        mIsAutoIncrement = isAutoIncrement;
        mDefaultValue = defaultValue;
        mEnumName = enumName;
        if (enumValues != null) mEnumValues.addAll(enumValues);
        mForeignKey = foreignKey;
    }

    public Field asForeignField(String path, boolean forceNullable) throws GeneratorException {
        boolean isNullable = forceNullable ? true : mIsNullable;
        Field res = new Field(mEntity, mName, mDocumentation, mType.mJsonName, mIsId, mIsIndex, isNullable, mIsAutoIncrement, mDefaultValue, mEnumName,
                mEnumValues, mForeignKey);
        res.mIsForeign = true;
        res.mOriginalField = this;
        res.mPath = path;
        return res;
    }

    public Entity getEntity() {
        return mEntity;
    }

    public String getName() {
        return mName;
    }

    public String getNameUpperCase() {
        return mName.toUpperCase(Locale.US);
    }

    public String getNameLowerCase() {
        return mName.toLowerCase(Locale.US);
    }

    public String getNameCamelCase() {
        return WordUtils.capitalizeFully(mName, new char[] {'_'}).replaceAll("_", "");
    }

    public String getNameCamelCaseLowerCase() {
        return WordUtils.uncapitalize(getNameCamelCase());
    }

    public String getEnumName() {
        return mEnumName;
    }

    public List<EnumValue> getEnumValues() {
        return mEnumValues;
    }

    public String getPrefixedName() {
        return mEntity.getNameLowerCase() + "__" + getNameLowerCase();
    }

    public String getNameOrPrefixed() {
        if (mIsAmbiguous) return getPrefixedName();
        return mName;
    }

    public Type getType() {
        return mType;
    }

    public boolean getIsId() {
        return mIsId;
    }

    public boolean getIsIndex() {
        return mIsIndex;
    }

    public boolean getIsNullable() {
        return mIsNullable;
    }

    public boolean getIsAutoIncrement() {
        return mIsAutoIncrement;
    }

    public String getDefaultValue() {
        switch (mType) {
            case BOOLEAN:
                if ("true".equals(mDefaultValue)) return "1";
                if ("false".equals(mDefaultValue)) return "0";
                LOG.warn("The default value for field " + mEntity.getNameLowerCase() + "." + getName()
                        + " could not be parsed as a boolean type, which is probably a problem - assuming 'false'.");
                return "0";
            case INTEGER:
            case LONG:
            case DATE:
            case ENUM:
                try {
                    Long.parseLong(mDefaultValue);
                    return mDefaultValue;
                } catch (NumberFormatException e) {
                    LOG.warn("The default value for field " + mEntity.getNameLowerCase() + "." + getName()
                            + " could not be parsed as a numeric type, which is probably a problem - assuming '0'.", e);
                }
                return "0";
            case FLOAT:
            case DOUBLE:
                try {
                    Double.parseDouble(mDefaultValue);
                    return mDefaultValue;
                } catch (NumberFormatException e) {
                    LOG.warn("The default value for field " + mEntity.getNameLowerCase() + "." + getName()
                            + " could not be parsed as a floating point type, which is probably a problem - assuming '0.0'.", e);
                }
                return "0.0";
            default:
                return '\'' + mDefaultValue + '\'';
        }
    }

    public boolean getHasDefaultValue() {
        return mDefaultValue != null;
    }

    public String getJavaTypeSimpleName() {
        if (mType == Type.ENUM) {
            return mEnumName;
        }
        if (mIsNullable) {
            return mType.getNullableJavaType().getSimpleName();
        }
        return mType.getNotNullableJavaType().getSimpleName();
    }

    public boolean getIsConvertionNeeded() {
        return !mIsNullable && mType.hasNotNullableJavaType();
    }

    public boolean isEnum() {
        return mType == Type.ENUM;
    }

    public ForeignKey getForeignKey() {
        return mForeignKey;
    }

    public boolean getIsForeign() {
        return mIsForeign;
    }

    public String getPath() {
        return mPath;
    }

    /* package */void setIsAmbiguous(boolean isAmbiguous) {
        mIsAmbiguous = isAmbiguous;
        if (mOriginalField != null) mOriginalField.mIsAmbiguous = isAmbiguous;
    }

    /* package */boolean getIsAmbiguous() {
        return mIsAmbiguous;
    }

    public String getDocumentation() {
        return mDocumentation;
    }

    public void setIsId(boolean isId) {
        mIsId = isId;
    }

    @Override
    public String toString() {
        return "Field [mName=" + mName + ", mDocumentation=" + mDocumentation + ", mType=" + mType + ", mIsId=" + mIsId + ", mIsIndex=" + mIsIndex
                + ", mIsNullable=" + mIsNullable + ", mIsAutoIncrement=" + mIsAutoIncrement + ", mDefaultValue=" + mDefaultValue + ", mEnumName=" + mEnumName
                + ", mEnumValues=" + mEnumValues + ", mForeignKey=" + mForeignKey + "]";
    }
}
