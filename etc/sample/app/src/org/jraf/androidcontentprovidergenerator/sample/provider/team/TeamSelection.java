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

import java.util.Date;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import org.jraf.androidcontentprovidergenerator.sample.provider.base.AbstractSelection;
import org.jraf.androidcontentprovidergenerator.sample.provider.company.*;
import org.jraf.androidcontentprovidergenerator.sample.provider.serialnumber.*;
import org.jraf.androidcontentprovidergenerator.sample.provider.serialnumber.*;

/**
 * Selection for the {@code team} table.
 */
public class TeamSelection extends AbstractSelection<TeamSelection> {
    @Override
    protected Uri baseUri() {
        return TeamColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @param sortOrder How to order the rows, formatted as an SQL ORDER BY clause (excluding the ORDER BY itself). Passing null will use the default sort
     *            order, which may be unordered.
     * @return A {@code TeamCursor} object, which is positioned before the first entry, or null.
     */
    public TeamCursor query(ContentResolver contentResolver, String[] projection, String sortOrder) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), sortOrder);
        if (cursor == null) return null;
        return new TeamCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null)}.
     */
    public TeamCursor query(ContentResolver contentResolver, String[] projection) {
        return query(contentResolver, projection, null);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null, null)}.
     */
    public TeamCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null, null);
    }


    public TeamSelection id(long... value) {
        addEquals("team." + TeamColumns._ID, toObjectArray(value));
        return this;
    }

    public TeamSelection companyId(long... value) {
        addEquals(TeamColumns.COMPANY_ID, toObjectArray(value));
        return this;
    }

    public TeamSelection companyIdNot(long... value) {
        addNotEquals(TeamColumns.COMPANY_ID, toObjectArray(value));
        return this;
    }

    public TeamSelection companyIdGt(long value) {
        addGreaterThan(TeamColumns.COMPANY_ID, value);
        return this;
    }

    public TeamSelection companyIdGtEq(long value) {
        addGreaterThanOrEquals(TeamColumns.COMPANY_ID, value);
        return this;
    }

    public TeamSelection companyIdLt(long value) {
        addLessThan(TeamColumns.COMPANY_ID, value);
        return this;
    }

    public TeamSelection companyIdLtEq(long value) {
        addLessThanOrEquals(TeamColumns.COMPANY_ID, value);
        return this;
    }

    public TeamSelection companyName(String... value) {
        addEquals(CompanyColumns.NAME, value);
        return this;
    }

    public TeamSelection companyNameNot(String... value) {
        addNotEquals(CompanyColumns.NAME, value);
        return this;
    }

    public TeamSelection companyNameLike(String... value) {
        addLike(CompanyColumns.NAME, value);
        return this;
    }

    public TeamSelection companyNameContains(String... value) {
        addContains(CompanyColumns.NAME, value);
        return this;
    }

    public TeamSelection companyNameStartsWith(String... value) {
        addStartsWith(CompanyColumns.NAME, value);
        return this;
    }

    public TeamSelection companyNameEndsWith(String... value) {
        addEndsWith(CompanyColumns.NAME, value);
        return this;
    }

    public TeamSelection companyAddress(String... value) {
        addEquals(CompanyColumns.ADDRESS, value);
        return this;
    }

    public TeamSelection companyAddressNot(String... value) {
        addNotEquals(CompanyColumns.ADDRESS, value);
        return this;
    }

    public TeamSelection companyAddressLike(String... value) {
        addLike(CompanyColumns.ADDRESS, value);
        return this;
    }

    public TeamSelection companyAddressContains(String... value) {
        addContains(CompanyColumns.ADDRESS, value);
        return this;
    }

    public TeamSelection companyAddressStartsWith(String... value) {
        addStartsWith(CompanyColumns.ADDRESS, value);
        return this;
    }

    public TeamSelection companyAddressEndsWith(String... value) {
        addEndsWith(CompanyColumns.ADDRESS, value);
        return this;
    }

    public TeamSelection companySerialNumberId(long... value) {
        addEquals(CompanyColumns.SERIAL_NUMBER_ID, toObjectArray(value));
        return this;
    }

    public TeamSelection companySerialNumberIdNot(long... value) {
        addNotEquals(CompanyColumns.SERIAL_NUMBER_ID, toObjectArray(value));
        return this;
    }

    public TeamSelection companySerialNumberIdGt(long value) {
        addGreaterThan(CompanyColumns.SERIAL_NUMBER_ID, value);
        return this;
    }

    public TeamSelection companySerialNumberIdGtEq(long value) {
        addGreaterThanOrEquals(CompanyColumns.SERIAL_NUMBER_ID, value);
        return this;
    }

    public TeamSelection companySerialNumberIdLt(long value) {
        addLessThan(CompanyColumns.SERIAL_NUMBER_ID, value);
        return this;
    }

    public TeamSelection companySerialNumberIdLtEq(long value) {
        addLessThanOrEquals(CompanyColumns.SERIAL_NUMBER_ID, value);
        return this;
    }

    public TeamSelection companySerialNumberPart0(String... value) {
        addEquals(SerialNumberColumns.PART0, value);
        return this;
    }

    public TeamSelection companySerialNumberPart0Not(String... value) {
        addNotEquals(SerialNumberColumns.PART0, value);
        return this;
    }

    public TeamSelection companySerialNumberPart0Like(String... value) {
        addLike(SerialNumberColumns.PART0, value);
        return this;
    }

    public TeamSelection companySerialNumberPart0Contains(String... value) {
        addContains(SerialNumberColumns.PART0, value);
        return this;
    }

    public TeamSelection companySerialNumberPart0StartsWith(String... value) {
        addStartsWith(SerialNumberColumns.PART0, value);
        return this;
    }

    public TeamSelection companySerialNumberPart0EndsWith(String... value) {
        addEndsWith(SerialNumberColumns.PART0, value);
        return this;
    }

    public TeamSelection companySerialNumberPart1(String... value) {
        addEquals(SerialNumberColumns.PART1, value);
        return this;
    }

    public TeamSelection companySerialNumberPart1Not(String... value) {
        addNotEquals(SerialNumberColumns.PART1, value);
        return this;
    }

    public TeamSelection companySerialNumberPart1Like(String... value) {
        addLike(SerialNumberColumns.PART1, value);
        return this;
    }

    public TeamSelection companySerialNumberPart1Contains(String... value) {
        addContains(SerialNumberColumns.PART1, value);
        return this;
    }

    public TeamSelection companySerialNumberPart1StartsWith(String... value) {
        addStartsWith(SerialNumberColumns.PART1, value);
        return this;
    }

    public TeamSelection companySerialNumberPart1EndsWith(String... value) {
        addEndsWith(SerialNumberColumns.PART1, value);
        return this;
    }

    public TeamSelection name(String... value) {
        addEquals(TeamColumns.NAME, value);
        return this;
    }

    public TeamSelection nameNot(String... value) {
        addNotEquals(TeamColumns.NAME, value);
        return this;
    }

    public TeamSelection nameLike(String... value) {
        addLike(TeamColumns.NAME, value);
        return this;
    }

    public TeamSelection nameContains(String... value) {
        addContains(TeamColumns.NAME, value);
        return this;
    }

    public TeamSelection nameStartsWith(String... value) {
        addStartsWith(TeamColumns.NAME, value);
        return this;
    }

    public TeamSelection nameEndsWith(String... value) {
        addEndsWith(TeamColumns.NAME, value);
        return this;
    }

    public TeamSelection countryCode(String... value) {
        addEquals(TeamColumns.COUNTRY_CODE, value);
        return this;
    }

    public TeamSelection countryCodeNot(String... value) {
        addNotEquals(TeamColumns.COUNTRY_CODE, value);
        return this;
    }

    public TeamSelection countryCodeLike(String... value) {
        addLike(TeamColumns.COUNTRY_CODE, value);
        return this;
    }

    public TeamSelection countryCodeContains(String... value) {
        addContains(TeamColumns.COUNTRY_CODE, value);
        return this;
    }

    public TeamSelection countryCodeStartsWith(String... value) {
        addStartsWith(TeamColumns.COUNTRY_CODE, value);
        return this;
    }

    public TeamSelection countryCodeEndsWith(String... value) {
        addEndsWith(TeamColumns.COUNTRY_CODE, value);
        return this;
    }

    public TeamSelection serialNumberId(long... value) {
        addEquals(TeamColumns.SERIAL_NUMBER_ID, toObjectArray(value));
        return this;
    }

    public TeamSelection serialNumberIdNot(long... value) {
        addNotEquals(TeamColumns.SERIAL_NUMBER_ID, toObjectArray(value));
        return this;
    }

    public TeamSelection serialNumberIdGt(long value) {
        addGreaterThan(TeamColumns.SERIAL_NUMBER_ID, value);
        return this;
    }

    public TeamSelection serialNumberIdGtEq(long value) {
        addGreaterThanOrEquals(TeamColumns.SERIAL_NUMBER_ID, value);
        return this;
    }

    public TeamSelection serialNumberIdLt(long value) {
        addLessThan(TeamColumns.SERIAL_NUMBER_ID, value);
        return this;
    }

    public TeamSelection serialNumberIdLtEq(long value) {
        addLessThanOrEquals(TeamColumns.SERIAL_NUMBER_ID, value);
        return this;
    }

    public TeamSelection serialNumberPart0(String... value) {
        addEquals(SerialNumberColumns.PART0, value);
        return this;
    }

    public TeamSelection serialNumberPart0Not(String... value) {
        addNotEquals(SerialNumberColumns.PART0, value);
        return this;
    }

    public TeamSelection serialNumberPart0Like(String... value) {
        addLike(SerialNumberColumns.PART0, value);
        return this;
    }

    public TeamSelection serialNumberPart0Contains(String... value) {
        addContains(SerialNumberColumns.PART0, value);
        return this;
    }

    public TeamSelection serialNumberPart0StartsWith(String... value) {
        addStartsWith(SerialNumberColumns.PART0, value);
        return this;
    }

    public TeamSelection serialNumberPart0EndsWith(String... value) {
        addEndsWith(SerialNumberColumns.PART0, value);
        return this;
    }

    public TeamSelection serialNumberPart1(String... value) {
        addEquals(SerialNumberColumns.PART1, value);
        return this;
    }

    public TeamSelection serialNumberPart1Not(String... value) {
        addNotEquals(SerialNumberColumns.PART1, value);
        return this;
    }

    public TeamSelection serialNumberPart1Like(String... value) {
        addLike(SerialNumberColumns.PART1, value);
        return this;
    }

    public TeamSelection serialNumberPart1Contains(String... value) {
        addContains(SerialNumberColumns.PART1, value);
        return this;
    }

    public TeamSelection serialNumberPart1StartsWith(String... value) {
        addStartsWith(SerialNumberColumns.PART1, value);
        return this;
    }

    public TeamSelection serialNumberPart1EndsWith(String... value) {
        addEndsWith(SerialNumberColumns.PART1, value);
        return this;
    }
}
