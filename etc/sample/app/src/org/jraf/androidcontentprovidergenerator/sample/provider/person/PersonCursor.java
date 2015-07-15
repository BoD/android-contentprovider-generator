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

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.jraf.androidcontentprovidergenerator.sample.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code person} table.
 */
public class PersonCursor extends AbstractCursor implements PersonModel {
    public PersonCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        Long res = getLongOrNull(PersonColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * First name of this person. For instance, John.
     * Cannot be {@code null}.
     */
    @NonNull
    public String getFirstName() {
        String res = getStringOrNull(PersonColumns.FIRST_NAME);
        if (res == null)
            throw new NullPointerException("The value of 'first_name' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Last name (a.k.a. Given name) of this person. For instance, Smith.
     * Cannot be {@code null}.
     */
    @NonNull
    public String getLastName() {
        String res = getStringOrNull(PersonColumns.LAST_NAME);
        if (res == null)
            throw new NullPointerException("The value of 'last_name' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code age} value.
     */
    public int getAge() {
        Integer res = getIntegerOrNull(PersonColumns.AGE);
        if (res == null)
            throw new NullPointerException("The value of 'age' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code birth_date} value.
     * Can be {@code null}.
     */
    @Nullable
    public Date getBirthDate() {
        Date res = getDateOrNull(PersonColumns.BIRTH_DATE);
        return res;
    }

    /**
     * If {@code true}, this person has blue eyes. Otherwise, this person doesn't have blue eyes.
     */
    public boolean getHasBlueEyes() {
        Boolean res = getBooleanOrNull(PersonColumns.HAS_BLUE_EYES);
        if (res == null)
            throw new NullPointerException("The value of 'has_blue_eyes' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code height} value.
     * Can be {@code null}.
     */
    @Nullable
    public Float getHeight() {
        Float res = getFloatOrNull(PersonColumns.HEIGHT);
        return res;
    }

    /**
     * Get the {@code gender} value.
     * Cannot be {@code null}.
     */
    @NonNull
    public Gender getGender() {
        Integer intValue = getIntegerOrNull(PersonColumns.GENDER);
        if (intValue == null)
            throw new NullPointerException("The value of 'gender' in the database was null, which is not allowed according to the model definition");
        return Gender.values()[intValue];
    }

    /**
     * Get the {@code country_code} value.
     * Cannot be {@code null}.
     */
    @NonNull
    public String getCountryCode() {
        String res = getStringOrNull(PersonColumns.COUNTRY_CODE);
        if (res == null)
            throw new NullPointerException("The value of 'country_code' in the database was null, which is not allowed according to the model definition");
        return res;
    }
}
