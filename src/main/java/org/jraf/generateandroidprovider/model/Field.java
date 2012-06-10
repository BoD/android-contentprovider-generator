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
 */
package org.jraf.generateandroidprovider.model;

public class Field {
    public static final String NAME = "name";
    public static final String TYPE = "type";

    public static enum Type {
        TEXT, INTEGER;

        public static Type fromString(String s) {
            return valueOf(s.toUpperCase());
        }
    }

    private final String mName;
    private final Type mType;

    public Field(String name, String type) {
        mName = name.toLowerCase();
        mType = Type.fromString(type);
    }

    public String getNameUpperCase() {
        return mName.toUpperCase();
    }

    public String getNameLowerCase() {
        return mName;
    }

    public Type getType() {
        return mType;
    }

    @Override
    public String toString() {
        return "[" + getNameLowerCase() + ": " + mType + "]";
    }
}
