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
package org.jraf.androidcontentprovidergenerator.sample.provider.serialnumber;

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
 * A serial number.
 */
public class SerialNumberColumns implements BaseColumns {
    public static final String TABLE_NAME = "serial_number";
    public static final Uri CONTENT_URI = Uri.parse(SampleProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    /**
     * Unique id, first part.
     */
    public static final String PART0 = "serial_number__part0";

    /**
     * Unique id, second part.
     */
    public static final String PART1 = "serial_number__part1";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            PART0,
            PART1
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(PART0) || c.contains("." + PART0)) return true;
            if (c.equals(PART1) || c.contains("." + PART1)) return true;
        }
        return false;
    }

}
