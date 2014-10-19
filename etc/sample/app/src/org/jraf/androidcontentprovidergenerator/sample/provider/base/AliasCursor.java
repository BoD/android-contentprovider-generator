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
package org.jraf.androidcontentprovidergenerator.sample.provider.base;

import android.database.Cursor;
import android.database.CursorWrapper;

import org.jraf.androidcontentprovidergenerator.sample.provider.company.CompanyColumns;
import org.jraf.androidcontentprovidergenerator.sample.provider.person.PersonColumns;
import org.jraf.androidcontentprovidergenerator.sample.provider.team.TeamColumns;

public class AliasCursor extends CursorWrapper {
    public AliasCursor(Cursor cursor) {
        super(cursor);
    }

    @Override
    public int getColumnIndexOrThrow(String columnName) throws IllegalArgumentException {
        int res = getColumnIndex(columnName);
        if (res == -1) throw new IllegalArgumentException("column '" + columnName + "' does not exist");
        return res;
    }

    @Override
    public int getColumnIndex(String columnName) {
        int res = super.getColumnIndex(columnName);
        if (res == -1) {
            // Could not find the column, try with its alias
            String alias = getAlias(columnName);
            if (alias == null) return -1;
            return super.getColumnIndex(alias);
        }
        return res;
    }

    private static String getAlias(String columnName) {
        String res = null;
        // Company
        res = CompanyColumns.getAlias(columnName);
        if (res != null) return res;

        // Person
        res = PersonColumns.getAlias(columnName);
        if (res != null) return res;

        // Team
        res = TeamColumns.getAlias(columnName);
        if (res != null) return res;

        return null;
    }
}