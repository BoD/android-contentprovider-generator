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
package org.jraf.androidcontentprovidergenerator.sample.provider.manual;

import java.util.Date;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import org.jraf.androidcontentprovidergenerator.sample.provider.base.AbstractSelection;

/**
 * Selection for the {@code manual} table.
 */
public class ManualSelection extends AbstractSelection<ManualSelection> {
    @Override
    protected Uri baseUri() {
        return ManualColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @param sortOrder How to order the rows, formatted as an SQL ORDER BY clause (excluding the ORDER BY itself). Passing null will use the default sort
     *            order, which may be unordered.
     * @return A {@code ManualCursor} object, which is positioned before the first entry, or null.
     */
    public ManualCursor query(ContentResolver contentResolver, String[] projection, String sortOrder) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), sortOrder);
        if (cursor == null) return null;
        return new ManualCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null)}.
     */
    public ManualCursor query(ContentResolver contentResolver, String[] projection) {
        return query(contentResolver, projection, null);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null, null)}.
     */
    public ManualCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null, null);
    }


    public ManualSelection id(long... value) {
        addEquals("manual." + ManualColumns._ID, toObjectArray(value));
        return this;
    }

    public ManualSelection title(String... value) {
        addEquals(ManualColumns.TITLE, value);
        return this;
    }

    public ManualSelection titleNot(String... value) {
        addNotEquals(ManualColumns.TITLE, value);
        return this;
    }

    public ManualSelection titleLike(String... value) {
        addLike(ManualColumns.TITLE, value);
        return this;
    }

    public ManualSelection titleContains(String... value) {
        addContains(ManualColumns.TITLE, value);
        return this;
    }

    public ManualSelection titleStartsWith(String... value) {
        addStartsWith(ManualColumns.TITLE, value);
        return this;
    }

    public ManualSelection titleEndsWith(String... value) {
        addEndsWith(ManualColumns.TITLE, value);
        return this;
    }

    public ManualSelection isbn(String... value) {
        addEquals(ManualColumns.ISBN, value);
        return this;
    }

    public ManualSelection isbnNot(String... value) {
        addNotEquals(ManualColumns.ISBN, value);
        return this;
    }

    public ManualSelection isbnLike(String... value) {
        addLike(ManualColumns.ISBN, value);
        return this;
    }

    public ManualSelection isbnContains(String... value) {
        addContains(ManualColumns.ISBN, value);
        return this;
    }

    public ManualSelection isbnStartsWith(String... value) {
        addStartsWith(ManualColumns.ISBN, value);
        return this;
    }

    public ManualSelection isbnEndsWith(String... value) {
        addEndsWith(ManualColumns.ISBN, value);
        return this;
    }
}
