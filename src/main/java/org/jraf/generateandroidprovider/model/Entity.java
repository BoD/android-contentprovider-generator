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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.WordUtils;

public class Entity {
    private final String mName;
    private final List<Field> mFields = new ArrayList<Field>();

    public Entity(String name) {
        mName = name.toLowerCase();
    }

    public void addField(Field field) {
        mFields.add(field);
    }

    public List<Field> getFields() {
        return Collections.unmodifiableList(mFields);
    }

    public String getNameCamelCase() {
        return WordUtils.capitalizeFully(mName, new char[] { '_' }).replaceAll("_", "");
    }

    public String getNameLowerCase() {
        return mName;
    }

    public String getNameUpperCase() {
        return mName.toUpperCase();
    }



    @Override
    public String toString() {
        return "[" + getNameLowerCase() + ": " + mFields.toString() + "]";
    }
}
