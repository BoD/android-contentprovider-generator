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

import java.util.Date;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.jraf.androidcontentprovidergenerator.sample.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code serial_number} table.
 */
public class SerialNumberCursor extends AbstractCursor implements SerialNumberModel {
    public SerialNumberCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        Long res = getLongOrNull(SerialNumberColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Unique id, first part.
     * Cannot be {@code null}.
     */
    @NonNull
    public String getPart0() {
        String res = getStringOrNull(SerialNumberColumns.PART0);
        if (res == null)
            throw new NullPointerException("The value of 'part0' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Unique id, second part.
     * Cannot be {@code null}.
     */
    @NonNull
    public String getPart1() {
        String res = getStringOrNull(SerialNumberColumns.PART1);
        if (res == null)
            throw new NullPointerException("The value of 'part1' in the database was null, which is not allowed according to the model definition");
        return res;
    }
}
