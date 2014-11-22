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

import android.database.Cursor;

import org.jraf.androidcontentprovidergenerator.sample.provider.base.AbstractCursor;
import org.jraf.androidcontentprovidergenerator.sample.provider.person.*;
import org.jraf.androidcontentprovidergenerator.sample.provider.team.*;
import org.jraf.androidcontentprovidergenerator.sample.provider.company.*;
import org.jraf.androidcontentprovidergenerator.sample.provider.serialnumber.*;
import org.jraf.androidcontentprovidergenerator.sample.provider.serialnumber.*;

/**
 * Cursor wrapper for the {@code person_team} table.
 */
public class PersonTeamCursor extends AbstractCursor {
    public PersonTeamCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Get the {@code person_id} value.
     */
    public long getPersonId() {
        return getLongOrNull(PersonTeamColumns.PERSON_ID);
    }

    /**
     * First name of this person. For instance, John.
     * Cannot be {@code null}.
     */
    public String getPersonFirstName() {
        Integer index = getCachedColumnIndexOrThrow(PersonColumns.FIRST_NAME);
        return getString(index);
    }

    /**
     * Last name (a.k.a. Given name) of this person. For instance, Smith.
     * Cannot be {@code null}.
     */
    public String getPersonLastName() {
        Integer index = getCachedColumnIndexOrThrow(PersonColumns.LAST_NAME);
        return getString(index);
    }

    /**
     * Get the {@code age} value.
     */
    public int getPersonAge() {
        return getIntegerOrNull(PersonColumns.AGE);
    }

    /**
     * Get the {@code birth_date} value.
     * Can be {@code null}.
     */
    public Date getPersonBirthDate() {
        return getDate(PersonColumns.BIRTH_DATE);
    }

    /**
     * If {@code true}, this person has blue eyes. Otherwise, this person doesn't have blue eyes.
     */
    public boolean getPersonHasBlueEyes() {
        return getBoolean(PersonColumns.HAS_BLUE_EYES);
    }

    /**
     * Get the {@code height} value.
     * Can be {@code null}.
     */
    public Float getPersonHeight() {
        return getFloatOrNull(PersonColumns.HEIGHT);
    }

    /**
     * Get the {@code gender} value.
     * Cannot be {@code null}.
     */
    public Gender getPersonGender() {
        Integer intValue = getIntegerOrNull(PersonColumns.GENDER);
        if (intValue == null) return null;
        return Gender.values()[intValue];
    }

    /**
     * Get the {@code country_code} value.
     * Cannot be {@code null}.
     */
    public String getPersonCountryCode() {
        Integer index = getCachedColumnIndexOrThrow(PersonColumns.COUNTRY_CODE);
        return getString(index);
    }

    /**
     * Get the {@code team_id} value.
     */
    public long getTeamId() {
        return getLongOrNull(PersonTeamColumns.TEAM_ID);
    }

    /**
     * Get the {@code company_id} value.
     */
    public long getTeamCompanyId() {
        return getLongOrNull(TeamColumns.COMPANY_ID);
    }

    /**
     * The commercial name of this company.
     * Cannot be {@code null}.
     */
    public String getTeamCompanyName() {
        Integer index = getCachedColumnIndexOrThrow(CompanyColumns.NAME);
        return getString(index);
    }

    /**
     * The full address of this company.
     * Can be {@code null}.
     */
    public String getTeamCompanyAddress() {
        Integer index = getCachedColumnIndexOrThrow(CompanyColumns.ADDRESS);
        return getString(index);
    }

    /**
     * The serial number of this company.
     */
    public long getTeamCompanySerialNumberId() {
        return getLongOrNull(CompanyColumns.SERIAL_NUMBER_ID);
    }

    /**
     * Unique id, first part.
     * Cannot be {@code null}.
     */
    public String getTeamCompanySerialNumberPart0() {
        Integer index = getCachedColumnIndexOrThrow(SerialNumberColumns.PART0);
        return getString(index);
    }

    /**
     * Unique id, second part.
     * Cannot be {@code null}.
     */
    public String getTeamCompanySerialNumberPart1() {
        Integer index = getCachedColumnIndexOrThrow(SerialNumberColumns.PART1);
        return getString(index);
    }

    /**
     * Get the {@code name} value.
     * Cannot be {@code null}.
     */
    public String getTeamName() {
        Integer index = getCachedColumnIndexOrThrow(TeamColumns.NAME);
        return getString(index);
    }

    /**
     * 2 letter country code where this team operates.
     * Cannot be {@code null}.
     */
    public String getTeamCountryCode() {
        Integer index = getCachedColumnIndexOrThrow(TeamColumns.COUNTRY_CODE);
        return getString(index);
    }

    /**
     * The serial number of this team.
     */
    public long getTeamSerialNumberId() {
        return getLongOrNull(TeamColumns.SERIAL_NUMBER_ID);
    }

    /**
     * Unique id, first part.
     * Cannot be {@code null}.
     */
    public String getTeamSerialNumberPart0() {
        Integer index = getCachedColumnIndexOrThrow(SerialNumberColumns.PART0);
        return getString(index);
    }

    /**
     * Unique id, second part.
     * Cannot be {@code null}.
     */
    public String getTeamSerialNumberPart1() {
        Integer index = getCachedColumnIndexOrThrow(SerialNumberColumns.PART1);
        return getString(index);
    }
}
