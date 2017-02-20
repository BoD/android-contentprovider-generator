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
package org.jraf.androidcontentprovidergenerator.sample.provider.company;

// @formatter:off
import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.CursorLoader;

import org.jraf.androidcontentprovidergenerator.sample.provider.base.AbstractSelection;
import org.jraf.androidcontentprovidergenerator.sample.provider.serialnumber.*;

/**
 * Selection for the {@code company} table.
 */
@SuppressWarnings({"unused", "WeakerAccess", "Recycle"})
public class CompanySelection extends AbstractSelection<CompanySelection> {
    @Override
    protected Uri baseUri() {
        return CompanyColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code CompanyCursor} object, which is positioned before the first entry, or null.
     */
    public CompanyCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new CompanyCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, null)}.
     */
    public CompanyCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code CompanyCursor} object, which is positioned before the first entry, or null.
     */
    public CompanyCursor query(Context context, String[] projection) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new CompanyCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, null)}.
     */
    public CompanyCursor query(Context context) {
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
                return new CompanyCursor(super.loadInBackground());
            }
        };
    }


    public CompanySelection id(long... value) {
        addEquals("company." + CompanyColumns._ID, toObjectArray(value));
        return this;
    }

    public CompanySelection idNot(long... value) {
        addNotEquals("company." + CompanyColumns._ID, toObjectArray(value));
        return this;
    }

    public CompanySelection orderById(boolean desc) {
        orderBy("company." + CompanyColumns._ID, desc);
        return this;
    }

    public CompanySelection orderById() {
        return orderById(false);
    }

    public CompanySelection name(String... value) {
        addEquals(CompanyColumns.NAME, value);
        return this;
    }

    public CompanySelection nameNot(String... value) {
        addNotEquals(CompanyColumns.NAME, value);
        return this;
    }

    public CompanySelection nameLike(String... value) {
        addLike(CompanyColumns.NAME, value);
        return this;
    }

    public CompanySelection nameContains(String... value) {
        addContains(CompanyColumns.NAME, value);
        return this;
    }

    public CompanySelection nameStartsWith(String... value) {
        addStartsWith(CompanyColumns.NAME, value);
        return this;
    }

    public CompanySelection nameEndsWith(String... value) {
        addEndsWith(CompanyColumns.NAME, value);
        return this;
    }

    public CompanySelection orderByName(boolean desc) {
        orderBy(CompanyColumns.NAME, desc);
        return this;
    }

    public CompanySelection orderByName() {
        orderBy(CompanyColumns.NAME, false);
        return this;
    }

    public CompanySelection address(String... value) {
        addEquals(CompanyColumns.ADDRESS, value);
        return this;
    }

    public CompanySelection addressNot(String... value) {
        addNotEquals(CompanyColumns.ADDRESS, value);
        return this;
    }

    public CompanySelection addressLike(String... value) {
        addLike(CompanyColumns.ADDRESS, value);
        return this;
    }

    public CompanySelection addressContains(String... value) {
        addContains(CompanyColumns.ADDRESS, value);
        return this;
    }

    public CompanySelection addressStartsWith(String... value) {
        addStartsWith(CompanyColumns.ADDRESS, value);
        return this;
    }

    public CompanySelection addressEndsWith(String... value) {
        addEndsWith(CompanyColumns.ADDRESS, value);
        return this;
    }

    public CompanySelection orderByAddress(boolean desc) {
        orderBy(CompanyColumns.ADDRESS, desc);
        return this;
    }

    public CompanySelection orderByAddress() {
        orderBy(CompanyColumns.ADDRESS, false);
        return this;
    }

    public CompanySelection serialNumberId(long... value) {
        addEquals(CompanyColumns.SERIAL_NUMBER_ID, toObjectArray(value));
        return this;
    }

    public CompanySelection serialNumberIdNot(long... value) {
        addNotEquals(CompanyColumns.SERIAL_NUMBER_ID, toObjectArray(value));
        return this;
    }

    public CompanySelection serialNumberIdGt(long value) {
        addGreaterThan(CompanyColumns.SERIAL_NUMBER_ID, value);
        return this;
    }

    public CompanySelection serialNumberIdGtEq(long value) {
        addGreaterThanOrEquals(CompanyColumns.SERIAL_NUMBER_ID, value);
        return this;
    }

    public CompanySelection serialNumberIdLt(long value) {
        addLessThan(CompanyColumns.SERIAL_NUMBER_ID, value);
        return this;
    }

    public CompanySelection serialNumberIdLtEq(long value) {
        addLessThanOrEquals(CompanyColumns.SERIAL_NUMBER_ID, value);
        return this;
    }

    public CompanySelection orderBySerialNumberId(boolean desc) {
        orderBy(CompanyColumns.SERIAL_NUMBER_ID, desc);
        return this;
    }

    public CompanySelection orderBySerialNumberId() {
        orderBy(CompanyColumns.SERIAL_NUMBER_ID, false);
        return this;
    }

    public CompanySelection serialNumberPart0(String... value) {
        addEquals(SerialNumberColumns.PART0, value);
        return this;
    }

    public CompanySelection serialNumberPart0Not(String... value) {
        addNotEquals(SerialNumberColumns.PART0, value);
        return this;
    }

    public CompanySelection serialNumberPart0Like(String... value) {
        addLike(SerialNumberColumns.PART0, value);
        return this;
    }

    public CompanySelection serialNumberPart0Contains(String... value) {
        addContains(SerialNumberColumns.PART0, value);
        return this;
    }

    public CompanySelection serialNumberPart0StartsWith(String... value) {
        addStartsWith(SerialNumberColumns.PART0, value);
        return this;
    }

    public CompanySelection serialNumberPart0EndsWith(String... value) {
        addEndsWith(SerialNumberColumns.PART0, value);
        return this;
    }

    public CompanySelection orderBySerialNumberPart0(boolean desc) {
        orderBy(SerialNumberColumns.PART0, desc);
        return this;
    }

    public CompanySelection orderBySerialNumberPart0() {
        orderBy(SerialNumberColumns.PART0, false);
        return this;
    }

    public CompanySelection serialNumberPart1(String... value) {
        addEquals(SerialNumberColumns.PART1, value);
        return this;
    }

    public CompanySelection serialNumberPart1Not(String... value) {
        addNotEquals(SerialNumberColumns.PART1, value);
        return this;
    }

    public CompanySelection serialNumberPart1Like(String... value) {
        addLike(SerialNumberColumns.PART1, value);
        return this;
    }

    public CompanySelection serialNumberPart1Contains(String... value) {
        addContains(SerialNumberColumns.PART1, value);
        return this;
    }

    public CompanySelection serialNumberPart1StartsWith(String... value) {
        addStartsWith(SerialNumberColumns.PART1, value);
        return this;
    }

    public CompanySelection serialNumberPart1EndsWith(String... value) {
        addEndsWith(SerialNumberColumns.PART1, value);
        return this;
    }

    public CompanySelection orderBySerialNumberPart1(boolean desc) {
        orderBy(SerialNumberColumns.PART1, desc);
        return this;
    }

    public CompanySelection orderBySerialNumberPart1() {
        orderBy(SerialNumberColumns.PART1, false);
        return this;
    }
}
