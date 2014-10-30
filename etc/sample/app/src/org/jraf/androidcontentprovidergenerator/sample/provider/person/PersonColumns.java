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
package org.jraf.androidcontentprovidergenerator.sample.provider.person;

import android.net.Uri;
import android.provider.BaseColumns;

import org.jraf.androidcontentprovidergenerator.sample.provider.SampleProvider;

/**
 * Columns for the {@code person} table.
 */
public class PersonColumns implements BaseColumns {
    public static final String TABLE_NAME = "person";
    public static final Uri CONTENT_URI = Uri.parse(SampleProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    public static final String _ID = new String(BaseColumns._ID);
    public static final String MAIN_TEAM_ID = new String("main_team_id");
    public static final String FIRST_NAME = new String("first_name");
    public static final String LAST_NAME = new String("last_name");
    public static final String AGE = new String("age");
    public static final String BIRTH_DATE = new String("birth_date");
    public static final String HAS_BLUE_EYES = new String("has_blue_eyes");
    public static final String HEIGHT = new String("height");
    public static final String GENDER = new String("gender");

    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;
    
    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            MAIN_TEAM_ID,
            FIRST_NAME,
            LAST_NAME,
            AGE,
            BIRTH_DATE,
            HAS_BLUE_EYES,
            HEIGHT,
            GENDER
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c == _ID) return true;
            if (c == MAIN_TEAM_ID) return true;
            if (c == FIRST_NAME) return true;
            if (c == LAST_NAME) return true;
            if (c == AGE) return true;
            if (c == BIRTH_DATE) return true;
            if (c == HAS_BLUE_EYES) return true;
            if (c == HEIGHT) return true;
            if (c == GENDER) return true;
        }
        return false;
    }

    public static String getQualifiedColumnName(String columnName) {
        if (columnName == _ID) return TABLE_NAME + "." + columnName + " AS " + _ID;
        if (columnName == MAIN_TEAM_ID) return TABLE_NAME + "." + columnName + " AS " + TABLE_NAME + "__" + columnName;
        if (columnName == FIRST_NAME) return TABLE_NAME + "." + columnName + " AS " + TABLE_NAME + "__" + columnName;
        if (columnName == LAST_NAME) return TABLE_NAME + "." + columnName + " AS " + TABLE_NAME + "__" + columnName;
        if (columnName == AGE) return TABLE_NAME + "." + columnName + " AS " + TABLE_NAME + "__" + columnName;
        if (columnName == BIRTH_DATE) return TABLE_NAME + "." + columnName + " AS " + TABLE_NAME + "__" + columnName;
        if (columnName == HAS_BLUE_EYES) return TABLE_NAME + "." + columnName + " AS " + TABLE_NAME + "__" + columnName;
        if (columnName == HEIGHT) return TABLE_NAME + "." + columnName + " AS " + TABLE_NAME + "__" + columnName;
        if (columnName == GENDER) return TABLE_NAME + "." + columnName + " AS " + TABLE_NAME + "__" + columnName;
        return null;
    }

    public static String getAlias(String columnName) {
        if (columnName == MAIN_TEAM_ID) return TABLE_NAME + "__" + columnName;
        if (columnName == FIRST_NAME) return TABLE_NAME + "__" + columnName;
        if (columnName == LAST_NAME) return TABLE_NAME + "__" + columnName;
        if (columnName == AGE) return TABLE_NAME + "__" + columnName;
        if (columnName == BIRTH_DATE) return TABLE_NAME + "__" + columnName;
        if (columnName == HAS_BLUE_EYES) return TABLE_NAME + "__" + columnName;
        if (columnName == HEIGHT) return TABLE_NAME + "__" + columnName;
        if (columnName == GENDER) return TABLE_NAME + "__" + columnName;
        return null;
    }
}
