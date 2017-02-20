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
package org.jraf.androidcontentprovidergenerator.sample.provider.manual;

// @formatter:off
import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.CursorLoader;

import org.jraf.androidcontentprovidergenerator.sample.provider.base.AbstractSelection;

/**
 * Selection for the {@code manual} table.
 */
@SuppressWarnings({"unused", "WeakerAccess", "Recycle"})
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
     * @return A {@code ManualCursor} object, which is positioned before the first entry, or null.
     */
    public ManualCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new ManualCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, null)}.
     */
    public ManualCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code ManualCursor} object, which is positioned before the first entry, or null.
     */
    public ManualCursor query(Context context, String[] projection) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new ManualCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, null)}.
     */
    public ManualCursor query(Context context) {
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
                return new ManualCursor(super.loadInBackground());
            }
        };
    }


    public ManualSelection id(long... value) {
        addEquals("manual." + ManualColumns._ID, toObjectArray(value));
        return this;
    }

    public ManualSelection idNot(long... value) {
        addNotEquals("manual." + ManualColumns._ID, toObjectArray(value));
        return this;
    }

    public ManualSelection orderById(boolean desc) {
        orderBy("manual." + ManualColumns._ID, desc);
        return this;
    }

    public ManualSelection orderById() {
        return orderById(false);
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

    public ManualSelection orderByTitle(boolean desc) {
        orderBy(ManualColumns.TITLE, desc);
        return this;
    }

    public ManualSelection orderByTitle() {
        orderBy(ManualColumns.TITLE, false);
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

    public ManualSelection orderByIsbn(boolean desc) {
        orderBy(ManualColumns.ISBN, desc);
        return this;
    }

    public ManualSelection orderByIsbn() {
        orderBy(ManualColumns.ISBN, false);
        return this;
    }
}
