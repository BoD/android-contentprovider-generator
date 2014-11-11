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
package org.jraf.androidcontentprovidergenerator.sample.provider.team;

import java.util.Date;

import android.database.Cursor;

import org.jraf.androidcontentprovidergenerator.sample.provider.base.AbstractCursor;
import org.jraf.androidcontentprovidergenerator.sample.provider.company.*;

/**
 * Cursor wrapper for the {@code team} table.
 */
public class TeamCursor extends AbstractCursor {
    public TeamCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Get the {@code company_id} value.
     */
    public long getCompanyId() {
        return getLongOrNull(TeamColumns.COMPANY_ID);
    }

    /**
     * The commercial name of this company.
     * Cannot be {@code null}.
     */
    public String getCompanyName() {
        Integer index = getCachedColumnIndexOrThrow(CompanyColumns.NAME);
        return getString(index);
    }

    /**
     * The full address of this company.
     * Can be {@code null}.
     */
    public String getCompanyAddress() {
        Integer index = getCachedColumnIndexOrThrow(CompanyColumns.ADDRESS);
        return getString(index);
    }

    /**
     * Get the {@code name} value.
     * Cannot be {@code null}.
     */
    public String getName() {
        Integer index = getCachedColumnIndexOrThrow(TeamColumns.NAME);
        return getString(index);
    }

    /**
     * 2 letter country code where this team operates.
     * Cannot be {@code null}.
     */
    public String getCountryCode() {
        Integer index = getCachedColumnIndexOrThrow(TeamColumns.COUNTRY_CODE);
        return getString(index);
    }
}
