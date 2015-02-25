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
package org.jraf.androidcontentprovidergenerator.sample.provider.team;

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
 * A group of people who work together.
 */
public class TeamColumns implements BaseColumns {
    public static final String TABLE_NAME = "team";
    public static final Uri CONTENT_URI = Uri.parse(SampleProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    public static final String COMPANY_ID = "company_id";

    public static final String NAME = "team__name";

    /**
     * 2 letter country code where this team operates.
     */
    public static final String COUNTRY_CODE = "team__country_code";

    /**
     * The serial number of this team.
     */
    public static final String SERIAL_NUMBER_ID = "team__serial_number_id";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            COMPANY_ID,
            NAME,
            COUNTRY_CODE,
            SERIAL_NUMBER_ID
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c == COMPANY_ID || c.contains("." + COMPANY_ID)) return true;
            if (c == NAME || c.contains("." + NAME)) return true;
            if (c == COUNTRY_CODE || c.contains("." + COUNTRY_CODE)) return true;
            if (c == SERIAL_NUMBER_ID || c.contains("." + SERIAL_NUMBER_ID)) return true;
        }
        return false;
    }

    public static final String PREFIX_COMPANY = TABLE_NAME + "__" + CompanyColumns.TABLE_NAME;
    public static final String PREFIX_SERIAL_NUMBER = TABLE_NAME + "__" + SerialNumberColumns.TABLE_NAME;
}
