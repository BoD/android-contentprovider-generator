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
package org.jraf.androidcontentprovidergenerator.model;

import org.apache.commons.lang.WordUtils;

import java.util.Date;

public class Field {
    public static final String NAME = "name";
    public static final String TYPE = "type";
    public static final String INDEX = "index";
    public static final String NULLABLE = "nullable";
    public static final String DEFAULT_VALUE = "default_value";

    public static enum Type {
        LONG("INTEGER", Long.class),
        TEXT("TEXT", String.class),
        INTEGER("INTEGER", Integer.class),
        FLOAT("FLOAT", Double.class),
        BLOB("BLOB", byte[].class),
        DATE("INTEGER", Date.class),
        BOOLEAN("INTEGER", Boolean.class);

        private String mSqlType;
        private Class<?> mJavaType;

        private Type(String sqlType, Class<?> javaType) {
            mSqlType = sqlType;
            mJavaType = javaType;
        }

        public static Type fromString(String s) {
            return valueOf(s.toUpperCase());
        }

        public String getSqlType() {
            return mSqlType;
        }

        public Class<?> getJavaType() {
            return mJavaType;
        }
    }

    private final String mName;
    private final Type mType;
    private boolean mIsIndex = false;
    private boolean mIsNullable = true;
    private String mDefaultValue;

    public Field(String name, String type) {
        this(name, type, false, true, null);
    }

    public Field(String name, String type, boolean pIsIndex) {
        this(name, type, pIsIndex, true, null);
    }

    public Field(String name, String type, boolean pIsIndex, boolean pIsNullable) {
        this(name, type, pIsIndex, pIsNullable, null);
    }

    public Field(String name, String type, boolean pIsIndex, boolean pIsNullable, String pDefaultValue) {
        mName = name.toLowerCase();
        mType = Type.fromString(type);
        mIsIndex = pIsIndex;
        mIsNullable = pIsNullable;
        mDefaultValue = pDefaultValue;
    }

    public String getNameUpperCase() {
        return mName.toUpperCase();
    }

    public String getNameLowerCase() {
        return mName;
    }

    public String getNameCamelCase() {
        return /*StringUtils.uncapitalize(*/WordUtils.capitalizeFully(mName, new char[]{'_'}).replaceAll("_", "")/*)*/;
    }

    public Type getType() {
        return mType;
    }

    public boolean getIsIndex() {
        return mIsIndex;
    }

    public boolean getIsNullable() {
        return mIsNullable;
    }

    public String getDefaultValue() {
        return mDefaultValue;
    }

    public boolean getHasDefaultValue() {
        return mDefaultValue != null && mDefaultValue.length() > 0;
    }

    @Override
    public String toString() {
        return "Field [mName=" + mName + ", mType=" + mType + ", mIsIndex=" + mIsIndex + ", mIsNullable=" + mIsNullable + "]";
    }
}
