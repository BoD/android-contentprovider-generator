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
package org.jraf.androidcontentprovidergenerator.sample.provider.person;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import org.jraf.androidcontentprovidergenerator.sample.provider.base.AbstractSelection;

/**
 * Selection for the {@code person} table.
 */
public class PersonSelection extends AbstractSelection<PersonSelection> {
    @Override
    protected Uri baseUri() {
        return PersonColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code PersonCursor} object, which is positioned before the first entry, or null.
     */
    public PersonCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new PersonCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, null)}.
     */
    public PersonCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code PersonCursor} object, which is positioned before the first entry, or null.
     */
    public PersonCursor query(Context context, String[] projection) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new PersonCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, null)}.
     */
    public PersonCursor query(Context context) {
        return query(context, null);
    }


    public PersonSelection id(long... value) {
        addEquals("person." + PersonColumns._ID, toObjectArray(value));
        return this;
    }

    public PersonSelection idNot(long... value) {
        addNotEquals("person." + PersonColumns._ID, toObjectArray(value));
        return this;
    }

    public PersonSelection orderById(boolean desc) {
        orderBy("person." + PersonColumns._ID, desc);
        return this;
    }

    public PersonSelection orderById() {
        return orderById(false);
    }

    public PersonSelection firstName(String... value) {
        addEquals(PersonColumns.FIRST_NAME, value);
        return this;
    }

    public PersonSelection firstNameNot(String... value) {
        addNotEquals(PersonColumns.FIRST_NAME, value);
        return this;
    }

    public PersonSelection firstNameLike(String... value) {
        addLike(PersonColumns.FIRST_NAME, value);
        return this;
    }

    public PersonSelection firstNameContains(String... value) {
        addContains(PersonColumns.FIRST_NAME, value);
        return this;
    }

    public PersonSelection firstNameStartsWith(String... value) {
        addStartsWith(PersonColumns.FIRST_NAME, value);
        return this;
    }

    public PersonSelection firstNameEndsWith(String... value) {
        addEndsWith(PersonColumns.FIRST_NAME, value);
        return this;
    }

    public PersonSelection orderByFirstName(boolean desc) {
        orderBy(PersonColumns.FIRST_NAME, desc);
        return this;
    }

    public PersonSelection orderByFirstName() {
        orderBy(PersonColumns.FIRST_NAME, false);
        return this;
    }

    public PersonSelection lastName(String... value) {
        addEquals(PersonColumns.LAST_NAME, value);
        return this;
    }

    public PersonSelection lastNameNot(String... value) {
        addNotEquals(PersonColumns.LAST_NAME, value);
        return this;
    }

    public PersonSelection lastNameLike(String... value) {
        addLike(PersonColumns.LAST_NAME, value);
        return this;
    }

    public PersonSelection lastNameContains(String... value) {
        addContains(PersonColumns.LAST_NAME, value);
        return this;
    }

    public PersonSelection lastNameStartsWith(String... value) {
        addStartsWith(PersonColumns.LAST_NAME, value);
        return this;
    }

    public PersonSelection lastNameEndsWith(String... value) {
        addEndsWith(PersonColumns.LAST_NAME, value);
        return this;
    }

    public PersonSelection orderByLastName(boolean desc) {
        orderBy(PersonColumns.LAST_NAME, desc);
        return this;
    }

    public PersonSelection orderByLastName() {
        orderBy(PersonColumns.LAST_NAME, false);
        return this;
    }

    public PersonSelection age(int... value) {
        addEquals(PersonColumns.AGE, toObjectArray(value));
        return this;
    }

    public PersonSelection ageNot(int... value) {
        addNotEquals(PersonColumns.AGE, toObjectArray(value));
        return this;
    }

    public PersonSelection ageGt(int value) {
        addGreaterThan(PersonColumns.AGE, value);
        return this;
    }

    public PersonSelection ageGtEq(int value) {
        addGreaterThanOrEquals(PersonColumns.AGE, value);
        return this;
    }

    public PersonSelection ageLt(int value) {
        addLessThan(PersonColumns.AGE, value);
        return this;
    }

    public PersonSelection ageLtEq(int value) {
        addLessThanOrEquals(PersonColumns.AGE, value);
        return this;
    }

    public PersonSelection orderByAge(boolean desc) {
        orderBy(PersonColumns.AGE, desc);
        return this;
    }

    public PersonSelection orderByAge() {
        orderBy(PersonColumns.AGE, false);
        return this;
    }

    public PersonSelection birthDate(Date... value) {
        addEquals(PersonColumns.BIRTH_DATE, value);
        return this;
    }

    public PersonSelection birthDateNot(Date... value) {
        addNotEquals(PersonColumns.BIRTH_DATE, value);
        return this;
    }

    public PersonSelection birthDate(Long... value) {
        addEquals(PersonColumns.BIRTH_DATE, value);
        return this;
    }

    public PersonSelection birthDateAfter(Date value) {
        addGreaterThan(PersonColumns.BIRTH_DATE, value);
        return this;
    }

    public PersonSelection birthDateAfterEq(Date value) {
        addGreaterThanOrEquals(PersonColumns.BIRTH_DATE, value);
        return this;
    }

    public PersonSelection birthDateBefore(Date value) {
        addLessThan(PersonColumns.BIRTH_DATE, value);
        return this;
    }

    public PersonSelection birthDateBeforeEq(Date value) {
        addLessThanOrEquals(PersonColumns.BIRTH_DATE, value);
        return this;
    }

    public PersonSelection orderByBirthDate(boolean desc) {
        orderBy(PersonColumns.BIRTH_DATE, desc);
        return this;
    }

    public PersonSelection orderByBirthDate() {
        orderBy(PersonColumns.BIRTH_DATE, false);
        return this;
    }

    public PersonSelection hasBlueEyes(boolean value) {
        addEquals(PersonColumns.HAS_BLUE_EYES, toObjectArray(value));
        return this;
    }

    public PersonSelection orderByHasBlueEyes(boolean desc) {
        orderBy(PersonColumns.HAS_BLUE_EYES, desc);
        return this;
    }

    public PersonSelection orderByHasBlueEyes() {
        orderBy(PersonColumns.HAS_BLUE_EYES, false);
        return this;
    }

    public PersonSelection height(Float... value) {
        addEquals(PersonColumns.HEIGHT, value);
        return this;
    }

    public PersonSelection heightNot(Float... value) {
        addNotEquals(PersonColumns.HEIGHT, value);
        return this;
    }

    public PersonSelection heightGt(float value) {
        addGreaterThan(PersonColumns.HEIGHT, value);
        return this;
    }

    public PersonSelection heightGtEq(float value) {
        addGreaterThanOrEquals(PersonColumns.HEIGHT, value);
        return this;
    }

    public PersonSelection heightLt(float value) {
        addLessThan(PersonColumns.HEIGHT, value);
        return this;
    }

    public PersonSelection heightLtEq(float value) {
        addLessThanOrEquals(PersonColumns.HEIGHT, value);
        return this;
    }

    public PersonSelection orderByHeight(boolean desc) {
        orderBy(PersonColumns.HEIGHT, desc);
        return this;
    }

    public PersonSelection orderByHeight() {
        orderBy(PersonColumns.HEIGHT, false);
        return this;
    }

    public PersonSelection gender(Gender... value) {
        addEquals(PersonColumns.GENDER, value);
        return this;
    }

    public PersonSelection genderNot(Gender... value) {
        addNotEquals(PersonColumns.GENDER, value);
        return this;
    }


    public PersonSelection orderByGender(boolean desc) {
        orderBy(PersonColumns.GENDER, desc);
        return this;
    }

    public PersonSelection orderByGender() {
        orderBy(PersonColumns.GENDER, false);
        return this;
    }

    public PersonSelection countryCode(String... value) {
        addEquals(PersonColumns.COUNTRY_CODE, value);
        return this;
    }

    public PersonSelection countryCodeNot(String... value) {
        addNotEquals(PersonColumns.COUNTRY_CODE, value);
        return this;
    }

    public PersonSelection countryCodeLike(String... value) {
        addLike(PersonColumns.COUNTRY_CODE, value);
        return this;
    }

    public PersonSelection countryCodeContains(String... value) {
        addContains(PersonColumns.COUNTRY_CODE, value);
        return this;
    }

    public PersonSelection countryCodeStartsWith(String... value) {
        addStartsWith(PersonColumns.COUNTRY_CODE, value);
        return this;
    }

    public PersonSelection countryCodeEndsWith(String... value) {
        addEndsWith(PersonColumns.COUNTRY_CODE, value);
        return this;
    }

    public PersonSelection orderByCountryCode(boolean desc) {
        orderBy(PersonColumns.COUNTRY_CODE, desc);
        return this;
    }

    public PersonSelection orderByCountryCode() {
        orderBy(PersonColumns.COUNTRY_CODE, false);
        return this;
    }
}
