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

import java.util.HashSet;
import java.util.Set;

import android.net.Uri;
import android.provider.BaseColumns;

import org.jraf.androidcontentprovidergenerator.sample.provider.SampleProvider;

/**
 * Columns for the {@code person} table.
 */
public class PersonColumns implements BaseColumns {
    public static final String TABLE_NAME = "person";
    public static final Uri CONTENT_URI = Uri.parse(SampleProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    public static final String _ID = BaseColumns._ID;
    public static final String MAIN_TEAM_ID = "main_team_id";
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String AGE = "age";
    public static final String BIRTH_DATE = "birth_date";
    public static final String HAS_BLUE_EYES = "has_blue_eyes";
    public static final String HEIGHT = "height";
    public static final String GENDER = "gender";

    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] FULL_PROJECTION = new String[] {
            TABLE_NAME + "." + _ID + " AS " + BaseColumns._ID,
            TABLE_NAME + "." + MAIN_TEAM_ID,
            TABLE_NAME + "." + FIRST_NAME,
            TABLE_NAME + "." + LAST_NAME,
            TABLE_NAME + "." + AGE,
            TABLE_NAME + "." + BIRTH_DATE,
            TABLE_NAME + "." + HAS_BLUE_EYES,
            TABLE_NAME + "." + HEIGHT,
            TABLE_NAME + "." + GENDER
    };
    // @formatter:on

    private static final Set<String> ALL_COLUMNS = new HashSet<String>();
    static {
        ALL_COLUMNS.add(_ID);
        ALL_COLUMNS.add(MAIN_TEAM_ID);
        ALL_COLUMNS.add(FIRST_NAME);
        ALL_COLUMNS.add(LAST_NAME);
        ALL_COLUMNS.add(AGE);
        ALL_COLUMNS.add(BIRTH_DATE);
        ALL_COLUMNS.add(HAS_BLUE_EYES);
        ALL_COLUMNS.add(HEIGHT);
        ALL_COLUMNS.add(GENDER);
    }

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (ALL_COLUMNS.contains(c)) return true;
        }
        return false;
    }
}
