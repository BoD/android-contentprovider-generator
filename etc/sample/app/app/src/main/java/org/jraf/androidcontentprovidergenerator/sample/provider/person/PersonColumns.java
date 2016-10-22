/*
 * This source is part of the
 *      _____  ___   ____
 *  __ / / _ \/ _ | / __/___  _______ _
 * / // / , _/ __ |/ _/_/ _ \/ __/ _ `/
 * \___/_/|_/_/ |_/_/ (_)___/_/  \_, /
 *                              /___/
 * repository.
 *
 * Copyright (C) 2012-2015 Benoit 'BoD' Lubek (BoD@JRAF.org)
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
import org.jraf.androidcontentprovidergenerator.sample.provider.company.CompanyColumns;
import org.jraf.androidcontentprovidergenerator.sample.provider.manual.ManualColumns;
import org.jraf.androidcontentprovidergenerator.sample.provider.person.PersonColumns;
import org.jraf.androidcontentprovidergenerator.sample.provider.personteam.PersonTeamColumns;
import org.jraf.androidcontentprovidergenerator.sample.provider.product.ProductColumns;
import org.jraf.androidcontentprovidergenerator.sample.provider.serialnumber.SerialNumberColumns;
import org.jraf.androidcontentprovidergenerator.sample.provider.team.TeamColumns;

/**
 * A human being which is part of a team.
 */
public class PersonColumns implements BaseColumns {
    public static final String TABLE_NAME = "person";
    public static final Uri CONTENT_URI = Uri.parse(SampleProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    /**
     * First name of this person. For instance, John.
     */
    public static final String FIRST_NAME = "first_name";

    /**
     * Last name (a.k.a. Given name) of this person. For instance, Smith.
     */
    public static final String LAST_NAME = "last_name";

    public static final String AGE = "Age";

    public static final String BIRTH_DATE = "birth_date";

    /**
     * If {@code true}, this person has blue eyes. Otherwise, this person doesn't have blue eyes.
     */
    public static final String HAS_BLUE_EYES = "has_blue_eyes";

    public static final String HEIGHT = "height";

    public static final String GENDER = "gender";

    public static final String COUNTRY_CODE = "person__country_code";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            FIRST_NAME,
            LAST_NAME,
            AGE,
            BIRTH_DATE,
            HAS_BLUE_EYES,
            HEIGHT,
            GENDER,
            COUNTRY_CODE
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(FIRST_NAME) || c.contains("." + FIRST_NAME)) return true;
            if (c.equals(LAST_NAME) || c.contains("." + LAST_NAME)) return true;
            if (c.equals(AGE) || c.contains("." + AGE)) return true;
            if (c.equals(BIRTH_DATE) || c.contains("." + BIRTH_DATE)) return true;
            if (c.equals(HAS_BLUE_EYES) || c.contains("." + HAS_BLUE_EYES)) return true;
            if (c.equals(HEIGHT) || c.contains("." + HEIGHT)) return true;
            if (c.equals(GENDER) || c.contains("." + GENDER)) return true;
            if (c.equals(COUNTRY_CODE) || c.contains("." + COUNTRY_CODE)) return true;
        }
        return false;
    }

}
