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
package org.jraf.androidcontentprovidergenerator.sample.provider.person;

import java.util.Date;

import android.database.Cursor;

import org.jraf.androidcontentprovidergenerator.sample.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code person} table.
 */
public class PersonCursor extends AbstractCursor {
    public PersonCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * First name of this person. For instance, John.
     * Cannot be {@code null}.
     */
    public String getFirstName() {
        Integer index = getCachedColumnIndexOrThrow(PersonColumns.FIRST_NAME);
        return getString(index);
    }

    /**
     * Last name (a.k.a. Given name) of this person. For instance, Smith.
     * Cannot be {@code null}.
     */
    public String getLastName() {
        Integer index = getCachedColumnIndexOrThrow(PersonColumns.LAST_NAME);
        return getString(index);
    }

    /**
     * Get the {@code age} value.
     */
    public int getAge() {
        return getIntegerOrNull(PersonColumns.AGE);
    }

    /**
     * Get the {@code birth_date} value.
     * Can be {@code null}.
     */
    public Date getBirthDate() {
        return getDate(PersonColumns.BIRTH_DATE);
    }

    /**
     * If {@code true}, this person has blue eyes. Otherwise, this person doesn't have blue eyes.
     */
    public boolean getHasBlueEyes() {
        return getBoolean(PersonColumns.HAS_BLUE_EYES);
    }

    /**
     * Get the {@code height} value.
     * Can be {@code null}.
     */
    public Float getHeight() {
        return getFloatOrNull(PersonColumns.HEIGHT);
    }

    /**
     * Get the {@code gender} value.
     * Cannot be {@code null}.
     */
    public Gender getGender() {
        Integer intValue = getIntegerOrNull(PersonColumns.GENDER);
        if (intValue == null) return null;
        return Gender.values()[intValue];
    }

    /**
     * Get the {@code country_code} value.
     * Cannot be {@code null}.
     */
    public String getCountryCode() {
        Integer index = getCachedColumnIndexOrThrow(PersonColumns.COUNTRY_CODE);
        return getString(index);
    }
}
