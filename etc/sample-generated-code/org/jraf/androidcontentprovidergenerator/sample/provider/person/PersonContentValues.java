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
package org.jraf.androidcontentprovidergenerator.sample.provider.person;

// @formatter:off
import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.jraf.androidcontentprovidergenerator.sample.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code person} table.
 */
@SuppressWarnings({"ConstantConditions", "unused"})
public class PersonContentValues extends AbstractContentValues<PersonContentValues> {
    @Override
    protected Uri baseUri() {
        return PersonColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable PersonSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param context The context to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(Context context, @Nullable PersonSelection where) {
        return context.getContentResolver().update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * First name of this person. For instance, John.
     */
    public PersonContentValues putFirstName(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("firstName must not be null");
        mContentValues.put(PersonColumns.FIRST_NAME, value);
        return this;
    }


    /**
     * Last name (a.k.a. Given name) of this person. For instance, Smith.
     */
    public PersonContentValues putLastName(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("lastName must not be null");
        mContentValues.put(PersonColumns.LAST_NAME, value);
        return this;
    }


    public PersonContentValues putAge(int value) {
        mContentValues.put(PersonColumns.AGE, value);
        return this;
    }


    public PersonContentValues putBirthDate(@Nullable Date value) {
        mContentValues.put(PersonColumns.BIRTH_DATE, value == null ? null : value.getTime());
        return this;
    }

    public PersonContentValues putBirthDateNull() {
        mContentValues.putNull(PersonColumns.BIRTH_DATE);
        return this;
    }

    public PersonContentValues putBirthDate(@Nullable Long value) {
        mContentValues.put(PersonColumns.BIRTH_DATE, value);
        return this;
    }

    /**
     * If {@code true}, this person has blue eyes. Otherwise, this person doesn't have blue eyes.
     */
    public PersonContentValues putHasBlueEyes(boolean value) {
        mContentValues.put(PersonColumns.HAS_BLUE_EYES, value);
        return this;
    }


    public PersonContentValues putHeight(@Nullable Float value) {
        mContentValues.put(PersonColumns.HEIGHT, value);
        return this;
    }

    public PersonContentValues putHeightNull() {
        mContentValues.putNull(PersonColumns.HEIGHT);
        return this;
    }

    public PersonContentValues putGender(@NonNull Gender value) {
        if (value == null) throw new IllegalArgumentException("gender must not be null");
        mContentValues.put(PersonColumns.GENDER, value.ordinal());
        return this;
    }


    public PersonContentValues putCountryCode(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("countryCode must not be null");
        mContentValues.put(PersonColumns.COUNTRY_CODE, value);
        return this;
    }

}
