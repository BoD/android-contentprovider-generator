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
package org.jraf.androidcontentprovidergenerator.sample.provider.personteam;

import java.util.Date;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import org.jraf.androidcontentprovidergenerator.sample.provider.base.AbstractSelection;

/**
 * Selection for the {@code person_team} table.
 */
public class PersonTeamSelection extends AbstractSelection<PersonTeamSelection> {
    @Override
    public Uri uri() {
        return PersonTeamColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @param sortOrder How to order the rows, formatted as an SQL ORDER BY clause (excluding the ORDER BY itself). Passing null will use the default sort
     *            order, which may be unordered.
     * @return A {@code PersonTeamCursor} object, which is positioned before the first entry, or null.
     */
    public PersonTeamCursor query(ContentResolver contentResolver, String[] projection, String sortOrder) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), sortOrder);
        if (cursor == null) return null;
        return new PersonTeamCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null}.
     */
    public PersonTeamCursor query(ContentResolver contentResolver, String[] projection) {
        return query(contentResolver, projection, null);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null, null}.
     */
    public PersonTeamCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null, null);
    }


    public PersonTeamSelection id(long... value) {
        addEquals("person_team." + PersonTeamColumns._ID, toObjectArray(value));
        return this;
    }


    public PersonTeamSelection personId(long... value) {
        addEquals(PersonTeamColumns.PERSON_ID, toObjectArray(value));
        return this;
    }

    public PersonTeamSelection personIdNot(long... value) {
        addNotEquals(PersonTeamColumns.PERSON_ID, toObjectArray(value));
        return this;
    }

    public PersonTeamSelection personIdGt(long value) {
        addGreaterThan(PersonTeamColumns.PERSON_ID, value);
        return this;
    }

    public PersonTeamSelection personIdGtEq(long value) {
        addGreaterThanOrEquals(PersonTeamColumns.PERSON_ID, value);
        return this;
    }

    public PersonTeamSelection personIdLt(long value) {
        addLessThan(PersonTeamColumns.PERSON_ID, value);
        return this;
    }

    public PersonTeamSelection personIdLtEq(long value) {
        addLessThanOrEquals(PersonTeamColumns.PERSON_ID, value);
        return this;
    }

    public PersonTeamSelection teamId(long... value) {
        addEquals(PersonTeamColumns.TEAM_ID, toObjectArray(value));
        return this;
    }

    public PersonTeamSelection teamIdNot(long... value) {
        addNotEquals(PersonTeamColumns.TEAM_ID, toObjectArray(value));
        return this;
    }

    public PersonTeamSelection teamIdGt(long value) {
        addGreaterThan(PersonTeamColumns.TEAM_ID, value);
        return this;
    }

    public PersonTeamSelection teamIdGtEq(long value) {
        addGreaterThanOrEquals(PersonTeamColumns.TEAM_ID, value);
        return this;
    }

    public PersonTeamSelection teamIdLt(long value) {
        addLessThan(PersonTeamColumns.TEAM_ID, value);
        return this;
    }

    public PersonTeamSelection teamIdLtEq(long value) {
        addLessThanOrEquals(PersonTeamColumns.TEAM_ID, value);
        return this;
    }
}
