/*
 * This source is part of the
 *      _____  ___   ____
 *  __ / / _ \/ _ | / __/___  _______ _
 * / // / , _/ __ |/ _/_/ _ \/ __/ _ `/
 * \___/_/|_/_/ |_/_/ (_)___/_/  \_, /
 *                              /___/
 * repository.
 *
 * Copyright (C) 2012-2017 Benoit 'BoD' Lubek (BoD@JRAF.org)
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

// @formatter:off
import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.CursorLoader;

import org.jraf.androidcontentprovidergenerator.sample.provider.base.AbstractSelection;

/**
 * Selection for the {@code serial_number} table.
 */
@SuppressWarnings({"unused", "WeakerAccess", "Recycle"})
public class SerialNumberSelection extends AbstractSelection<SerialNumberSelection> {
    @Override
    protected Uri baseUri() {
        return SerialNumberColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code SerialNumberCursor} object, which is positioned before the first entry, or null.
     */
    public SerialNumberCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new SerialNumberCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, null)}.
     */
    public SerialNumberCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code SerialNumberCursor} object, which is positioned before the first entry, or null.
     */
    public SerialNumberCursor query(Context context, String[] projection) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new SerialNumberCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, null)}.
     */
    public SerialNumberCursor query(Context context) {
        return query(context, null);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public CursorLoader getCursorLoader(Context context, String[] projection) {
        return new CursorLoader(context, uri(), projection, sel(), args(), order()) {
            @Override
            public Cursor loadInBackground() {
                return new SerialNumberCursor(super.loadInBackground());
            }
        };
    }


    public SerialNumberSelection id(long... value) {
        addEquals("serial_number." + SerialNumberColumns._ID, toObjectArray(value));
        return this;
    }

    public SerialNumberSelection idNot(long... value) {
        addNotEquals("serial_number." + SerialNumberColumns._ID, toObjectArray(value));
        return this;
    }

    public SerialNumberSelection orderById(boolean desc) {
        orderBy("serial_number." + SerialNumberColumns._ID, desc);
        return this;
    }

    public SerialNumberSelection orderById() {
        return orderById(false);
    }

    public SerialNumberSelection part0(String... value) {
        addEquals(SerialNumberColumns.PART0, value);
        return this;
    }

    public SerialNumberSelection part0Not(String... value) {
        addNotEquals(SerialNumberColumns.PART0, value);
        return this;
    }

    public SerialNumberSelection part0Like(String... value) {
        addLike(SerialNumberColumns.PART0, value);
        return this;
    }

    public SerialNumberSelection part0Contains(String... value) {
        addContains(SerialNumberColumns.PART0, value);
        return this;
    }

    public SerialNumberSelection part0StartsWith(String... value) {
        addStartsWith(SerialNumberColumns.PART0, value);
        return this;
    }

    public SerialNumberSelection part0EndsWith(String... value) {
        addEndsWith(SerialNumberColumns.PART0, value);
        return this;
    }

    public SerialNumberSelection orderByPart0(boolean desc) {
        orderBy(SerialNumberColumns.PART0, desc);
        return this;
    }

    public SerialNumberSelection orderByPart0() {
        orderBy(SerialNumberColumns.PART0, false);
        return this;
    }

    public SerialNumberSelection part1(String... value) {
        addEquals(SerialNumberColumns.PART1, value);
        return this;
    }

    public SerialNumberSelection part1Not(String... value) {
        addNotEquals(SerialNumberColumns.PART1, value);
        return this;
    }

    public SerialNumberSelection part1Like(String... value) {
        addLike(SerialNumberColumns.PART1, value);
        return this;
    }

    public SerialNumberSelection part1Contains(String... value) {
        addContains(SerialNumberColumns.PART1, value);
        return this;
    }

    public SerialNumberSelection part1StartsWith(String... value) {
        addStartsWith(SerialNumberColumns.PART1, value);
        return this;
    }

    public SerialNumberSelection part1EndsWith(String... value) {
        addEndsWith(SerialNumberColumns.PART1, value);
        return this;
    }

    public SerialNumberSelection orderByPart1(boolean desc) {
        orderBy(SerialNumberColumns.PART1, desc);
        return this;
    }

    public SerialNumberSelection orderByPart1() {
        orderBy(SerialNumberColumns.PART1, false);
        return this;
    }
}
