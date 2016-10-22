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
package org.jraf.androidcontentprovidergenerator.sample.provider.personteam;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import org.jraf.androidcontentprovidergenerator.sample.provider.base.AbstractSelection;
import org.jraf.androidcontentprovidergenerator.sample.provider.person.*;
import org.jraf.androidcontentprovidergenerator.sample.provider.team.*;
import org.jraf.androidcontentprovidergenerator.sample.provider.company.*;
import org.jraf.androidcontentprovidergenerator.sample.provider.serialnumber.*;
import org.jraf.androidcontentprovidergenerator.sample.provider.serialnumber.*;

/**
 * Selection for the {@code person_team} table.
 */
public class PersonTeamSelection extends AbstractSelection<PersonTeamSelection> {
    @Override
    protected Uri baseUri() {
        return PersonTeamColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code PersonTeamCursor} object, which is positioned before the first entry, or null.
     */
    public PersonTeamCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new PersonTeamCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, null)}.
     */
    public PersonTeamCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code PersonTeamCursor} object, which is positioned before the first entry, or null.
     */
    public PersonTeamCursor query(Context context, String[] projection) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new PersonTeamCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, null)}.
     */
    public PersonTeamCursor query(Context context) {
        return query(context, null);
    }


    public PersonTeamSelection id(long... value) {
        addEquals("person_team." + PersonTeamColumns._ID, toObjectArray(value));
        return this;
    }

    public PersonTeamSelection idNot(long... value) {
        addNotEquals("person_team." + PersonTeamColumns._ID, toObjectArray(value));
        return this;
    }

    public PersonTeamSelection orderById(boolean desc) {
        orderBy("person_team." + PersonTeamColumns._ID, desc);
        return this;
    }

    public PersonTeamSelection orderById() {
        return orderById(false);
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

    public PersonTeamSelection orderByPersonId(boolean desc) {
        orderBy(PersonTeamColumns.PERSON_ID, desc);
        return this;
    }

    public PersonTeamSelection orderByPersonId() {
        orderBy(PersonTeamColumns.PERSON_ID, false);
        return this;
    }

    public PersonTeamSelection personFirstName(String... value) {
        addEquals(PersonColumns.FIRST_NAME, value);
        return this;
    }

    public PersonTeamSelection personFirstNameNot(String... value) {
        addNotEquals(PersonColumns.FIRST_NAME, value);
        return this;
    }

    public PersonTeamSelection personFirstNameLike(String... value) {
        addLike(PersonColumns.FIRST_NAME, value);
        return this;
    }

    public PersonTeamSelection personFirstNameContains(String... value) {
        addContains(PersonColumns.FIRST_NAME, value);
        return this;
    }

    public PersonTeamSelection personFirstNameStartsWith(String... value) {
        addStartsWith(PersonColumns.FIRST_NAME, value);
        return this;
    }

    public PersonTeamSelection personFirstNameEndsWith(String... value) {
        addEndsWith(PersonColumns.FIRST_NAME, value);
        return this;
    }

    public PersonTeamSelection orderByPersonFirstName(boolean desc) {
        orderBy(PersonColumns.FIRST_NAME, desc);
        return this;
    }

    public PersonTeamSelection orderByPersonFirstName() {
        orderBy(PersonColumns.FIRST_NAME, false);
        return this;
    }

    public PersonTeamSelection personLastName(String... value) {
        addEquals(PersonColumns.LAST_NAME, value);
        return this;
    }

    public PersonTeamSelection personLastNameNot(String... value) {
        addNotEquals(PersonColumns.LAST_NAME, value);
        return this;
    }

    public PersonTeamSelection personLastNameLike(String... value) {
        addLike(PersonColumns.LAST_NAME, value);
        return this;
    }

    public PersonTeamSelection personLastNameContains(String... value) {
        addContains(PersonColumns.LAST_NAME, value);
        return this;
    }

    public PersonTeamSelection personLastNameStartsWith(String... value) {
        addStartsWith(PersonColumns.LAST_NAME, value);
        return this;
    }

    public PersonTeamSelection personLastNameEndsWith(String... value) {
        addEndsWith(PersonColumns.LAST_NAME, value);
        return this;
    }

    public PersonTeamSelection orderByPersonLastName(boolean desc) {
        orderBy(PersonColumns.LAST_NAME, desc);
        return this;
    }

    public PersonTeamSelection orderByPersonLastName() {
        orderBy(PersonColumns.LAST_NAME, false);
        return this;
    }

    public PersonTeamSelection personAge(int... value) {
        addEquals(PersonColumns.AGE, toObjectArray(value));
        return this;
    }

    public PersonTeamSelection personAgeNot(int... value) {
        addNotEquals(PersonColumns.AGE, toObjectArray(value));
        return this;
    }

    public PersonTeamSelection personAgeGt(int value) {
        addGreaterThan(PersonColumns.AGE, value);
        return this;
    }

    public PersonTeamSelection personAgeGtEq(int value) {
        addGreaterThanOrEquals(PersonColumns.AGE, value);
        return this;
    }

    public PersonTeamSelection personAgeLt(int value) {
        addLessThan(PersonColumns.AGE, value);
        return this;
    }

    public PersonTeamSelection personAgeLtEq(int value) {
        addLessThanOrEquals(PersonColumns.AGE, value);
        return this;
    }

    public PersonTeamSelection orderByPersonAge(boolean desc) {
        orderBy(PersonColumns.AGE, desc);
        return this;
    }

    public PersonTeamSelection orderByPersonAge() {
        orderBy(PersonColumns.AGE, false);
        return this;
    }

    public PersonTeamSelection personBirthDate(Date... value) {
        addEquals(PersonColumns.BIRTH_DATE, value);
        return this;
    }

    public PersonTeamSelection personBirthDateNot(Date... value) {
        addNotEquals(PersonColumns.BIRTH_DATE, value);
        return this;
    }

    public PersonTeamSelection personBirthDate(Long... value) {
        addEquals(PersonColumns.BIRTH_DATE, value);
        return this;
    }

    public PersonTeamSelection personBirthDateAfter(Date value) {
        addGreaterThan(PersonColumns.BIRTH_DATE, value);
        return this;
    }

    public PersonTeamSelection personBirthDateAfterEq(Date value) {
        addGreaterThanOrEquals(PersonColumns.BIRTH_DATE, value);
        return this;
    }

    public PersonTeamSelection personBirthDateBefore(Date value) {
        addLessThan(PersonColumns.BIRTH_DATE, value);
        return this;
    }

    public PersonTeamSelection personBirthDateBeforeEq(Date value) {
        addLessThanOrEquals(PersonColumns.BIRTH_DATE, value);
        return this;
    }

    public PersonTeamSelection orderByPersonBirthDate(boolean desc) {
        orderBy(PersonColumns.BIRTH_DATE, desc);
        return this;
    }

    public PersonTeamSelection orderByPersonBirthDate() {
        orderBy(PersonColumns.BIRTH_DATE, false);
        return this;
    }

    public PersonTeamSelection personHasBlueEyes(boolean value) {
        addEquals(PersonColumns.HAS_BLUE_EYES, toObjectArray(value));
        return this;
    }

    public PersonTeamSelection orderByPersonHasBlueEyes(boolean desc) {
        orderBy(PersonColumns.HAS_BLUE_EYES, desc);
        return this;
    }

    public PersonTeamSelection orderByPersonHasBlueEyes() {
        orderBy(PersonColumns.HAS_BLUE_EYES, false);
        return this;
    }

    public PersonTeamSelection personHeight(Float... value) {
        addEquals(PersonColumns.HEIGHT, value);
        return this;
    }

    public PersonTeamSelection personHeightNot(Float... value) {
        addNotEquals(PersonColumns.HEIGHT, value);
        return this;
    }

    public PersonTeamSelection personHeightGt(float value) {
        addGreaterThan(PersonColumns.HEIGHT, value);
        return this;
    }

    public PersonTeamSelection personHeightGtEq(float value) {
        addGreaterThanOrEquals(PersonColumns.HEIGHT, value);
        return this;
    }

    public PersonTeamSelection personHeightLt(float value) {
        addLessThan(PersonColumns.HEIGHT, value);
        return this;
    }

    public PersonTeamSelection personHeightLtEq(float value) {
        addLessThanOrEquals(PersonColumns.HEIGHT, value);
        return this;
    }

    public PersonTeamSelection orderByPersonHeight(boolean desc) {
        orderBy(PersonColumns.HEIGHT, desc);
        return this;
    }

    public PersonTeamSelection orderByPersonHeight() {
        orderBy(PersonColumns.HEIGHT, false);
        return this;
    }

    public PersonTeamSelection personGender(Gender... value) {
        addEquals(PersonColumns.GENDER, value);
        return this;
    }

    public PersonTeamSelection personGenderNot(Gender... value) {
        addNotEquals(PersonColumns.GENDER, value);
        return this;
    }


    public PersonTeamSelection orderByPersonGender(boolean desc) {
        orderBy(PersonColumns.GENDER, desc);
        return this;
    }

    public PersonTeamSelection orderByPersonGender() {
        orderBy(PersonColumns.GENDER, false);
        return this;
    }

    public PersonTeamSelection personCountryCode(String... value) {
        addEquals(PersonColumns.COUNTRY_CODE, value);
        return this;
    }

    public PersonTeamSelection personCountryCodeNot(String... value) {
        addNotEquals(PersonColumns.COUNTRY_CODE, value);
        return this;
    }

    public PersonTeamSelection personCountryCodeLike(String... value) {
        addLike(PersonColumns.COUNTRY_CODE, value);
        return this;
    }

    public PersonTeamSelection personCountryCodeContains(String... value) {
        addContains(PersonColumns.COUNTRY_CODE, value);
        return this;
    }

    public PersonTeamSelection personCountryCodeStartsWith(String... value) {
        addStartsWith(PersonColumns.COUNTRY_CODE, value);
        return this;
    }

    public PersonTeamSelection personCountryCodeEndsWith(String... value) {
        addEndsWith(PersonColumns.COUNTRY_CODE, value);
        return this;
    }

    public PersonTeamSelection orderByPersonCountryCode(boolean desc) {
        orderBy(PersonColumns.COUNTRY_CODE, desc);
        return this;
    }

    public PersonTeamSelection orderByPersonCountryCode() {
        orderBy(PersonColumns.COUNTRY_CODE, false);
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

    public PersonTeamSelection orderByTeamId(boolean desc) {
        orderBy(PersonTeamColumns.TEAM_ID, desc);
        return this;
    }

    public PersonTeamSelection orderByTeamId() {
        orderBy(PersonTeamColumns.TEAM_ID, false);
        return this;
    }

    public PersonTeamSelection teamCompanyId(long... value) {
        addEquals(TeamColumns.COMPANY_ID, toObjectArray(value));
        return this;
    }

    public PersonTeamSelection teamCompanyIdNot(long... value) {
        addNotEquals(TeamColumns.COMPANY_ID, toObjectArray(value));
        return this;
    }

    public PersonTeamSelection teamCompanyIdGt(long value) {
        addGreaterThan(TeamColumns.COMPANY_ID, value);
        return this;
    }

    public PersonTeamSelection teamCompanyIdGtEq(long value) {
        addGreaterThanOrEquals(TeamColumns.COMPANY_ID, value);
        return this;
    }

    public PersonTeamSelection teamCompanyIdLt(long value) {
        addLessThan(TeamColumns.COMPANY_ID, value);
        return this;
    }

    public PersonTeamSelection teamCompanyIdLtEq(long value) {
        addLessThanOrEquals(TeamColumns.COMPANY_ID, value);
        return this;
    }

    public PersonTeamSelection orderByTeamCompanyId(boolean desc) {
        orderBy(TeamColumns.COMPANY_ID, desc);
        return this;
    }

    public PersonTeamSelection orderByTeamCompanyId() {
        orderBy(TeamColumns.COMPANY_ID, false);
        return this;
    }

    public PersonTeamSelection teamCompanyName(String... value) {
        addEquals(CompanyColumns.NAME, value);
        return this;
    }

    public PersonTeamSelection teamCompanyNameNot(String... value) {
        addNotEquals(CompanyColumns.NAME, value);
        return this;
    }

    public PersonTeamSelection teamCompanyNameLike(String... value) {
        addLike(CompanyColumns.NAME, value);
        return this;
    }

    public PersonTeamSelection teamCompanyNameContains(String... value) {
        addContains(CompanyColumns.NAME, value);
        return this;
    }

    public PersonTeamSelection teamCompanyNameStartsWith(String... value) {
        addStartsWith(CompanyColumns.NAME, value);
        return this;
    }

    public PersonTeamSelection teamCompanyNameEndsWith(String... value) {
        addEndsWith(CompanyColumns.NAME, value);
        return this;
    }

    public PersonTeamSelection orderByTeamCompanyName(boolean desc) {
        orderBy(CompanyColumns.NAME, desc);
        return this;
    }

    public PersonTeamSelection orderByTeamCompanyName() {
        orderBy(CompanyColumns.NAME, false);
        return this;
    }

    public PersonTeamSelection teamCompanyAddress(String... value) {
        addEquals(CompanyColumns.ADDRESS, value);
        return this;
    }

    public PersonTeamSelection teamCompanyAddressNot(String... value) {
        addNotEquals(CompanyColumns.ADDRESS, value);
        return this;
    }

    public PersonTeamSelection teamCompanyAddressLike(String... value) {
        addLike(CompanyColumns.ADDRESS, value);
        return this;
    }

    public PersonTeamSelection teamCompanyAddressContains(String... value) {
        addContains(CompanyColumns.ADDRESS, value);
        return this;
    }

    public PersonTeamSelection teamCompanyAddressStartsWith(String... value) {
        addStartsWith(CompanyColumns.ADDRESS, value);
        return this;
    }

    public PersonTeamSelection teamCompanyAddressEndsWith(String... value) {
        addEndsWith(CompanyColumns.ADDRESS, value);
        return this;
    }

    public PersonTeamSelection orderByTeamCompanyAddress(boolean desc) {
        orderBy(CompanyColumns.ADDRESS, desc);
        return this;
    }

    public PersonTeamSelection orderByTeamCompanyAddress() {
        orderBy(CompanyColumns.ADDRESS, false);
        return this;
    }

    public PersonTeamSelection teamCompanySerialNumberId(long... value) {
        addEquals(CompanyColumns.SERIAL_NUMBER_ID, toObjectArray(value));
        return this;
    }

    public PersonTeamSelection teamCompanySerialNumberIdNot(long... value) {
        addNotEquals(CompanyColumns.SERIAL_NUMBER_ID, toObjectArray(value));
        return this;
    }

    public PersonTeamSelection teamCompanySerialNumberIdGt(long value) {
        addGreaterThan(CompanyColumns.SERIAL_NUMBER_ID, value);
        return this;
    }

    public PersonTeamSelection teamCompanySerialNumberIdGtEq(long value) {
        addGreaterThanOrEquals(CompanyColumns.SERIAL_NUMBER_ID, value);
        return this;
    }

    public PersonTeamSelection teamCompanySerialNumberIdLt(long value) {
        addLessThan(CompanyColumns.SERIAL_NUMBER_ID, value);
        return this;
    }

    public PersonTeamSelection teamCompanySerialNumberIdLtEq(long value) {
        addLessThanOrEquals(CompanyColumns.SERIAL_NUMBER_ID, value);
        return this;
    }

    public PersonTeamSelection orderByTeamCompanySerialNumberId(boolean desc) {
        orderBy(CompanyColumns.SERIAL_NUMBER_ID, desc);
        return this;
    }

    public PersonTeamSelection orderByTeamCompanySerialNumberId() {
        orderBy(CompanyColumns.SERIAL_NUMBER_ID, false);
        return this;
    }

    public PersonTeamSelection teamCompanySerialNumberPart0(String... value) {
        addEquals(SerialNumberColumns.PART0, value);
        return this;
    }

    public PersonTeamSelection teamCompanySerialNumberPart0Not(String... value) {
        addNotEquals(SerialNumberColumns.PART0, value);
        return this;
    }

    public PersonTeamSelection teamCompanySerialNumberPart0Like(String... value) {
        addLike(SerialNumberColumns.PART0, value);
        return this;
    }

    public PersonTeamSelection teamCompanySerialNumberPart0Contains(String... value) {
        addContains(SerialNumberColumns.PART0, value);
        return this;
    }

    public PersonTeamSelection teamCompanySerialNumberPart0StartsWith(String... value) {
        addStartsWith(SerialNumberColumns.PART0, value);
        return this;
    }

    public PersonTeamSelection teamCompanySerialNumberPart0EndsWith(String... value) {
        addEndsWith(SerialNumberColumns.PART0, value);
        return this;
    }

    public PersonTeamSelection orderByTeamCompanySerialNumberPart0(boolean desc) {
        orderBy(SerialNumberColumns.PART0, desc);
        return this;
    }

    public PersonTeamSelection orderByTeamCompanySerialNumberPart0() {
        orderBy(SerialNumberColumns.PART0, false);
        return this;
    }

    public PersonTeamSelection teamCompanySerialNumberPart1(String... value) {
        addEquals(SerialNumberColumns.PART1, value);
        return this;
    }

    public PersonTeamSelection teamCompanySerialNumberPart1Not(String... value) {
        addNotEquals(SerialNumberColumns.PART1, value);
        return this;
    }

    public PersonTeamSelection teamCompanySerialNumberPart1Like(String... value) {
        addLike(SerialNumberColumns.PART1, value);
        return this;
    }

    public PersonTeamSelection teamCompanySerialNumberPart1Contains(String... value) {
        addContains(SerialNumberColumns.PART1, value);
        return this;
    }

    public PersonTeamSelection teamCompanySerialNumberPart1StartsWith(String... value) {
        addStartsWith(SerialNumberColumns.PART1, value);
        return this;
    }

    public PersonTeamSelection teamCompanySerialNumberPart1EndsWith(String... value) {
        addEndsWith(SerialNumberColumns.PART1, value);
        return this;
    }

    public PersonTeamSelection orderByTeamCompanySerialNumberPart1(boolean desc) {
        orderBy(SerialNumberColumns.PART1, desc);
        return this;
    }

    public PersonTeamSelection orderByTeamCompanySerialNumberPart1() {
        orderBy(SerialNumberColumns.PART1, false);
        return this;
    }

    public PersonTeamSelection teamName(String... value) {
        addEquals(TeamColumns.NAME, value);
        return this;
    }

    public PersonTeamSelection teamNameNot(String... value) {
        addNotEquals(TeamColumns.NAME, value);
        return this;
    }

    public PersonTeamSelection teamNameLike(String... value) {
        addLike(TeamColumns.NAME, value);
        return this;
    }

    public PersonTeamSelection teamNameContains(String... value) {
        addContains(TeamColumns.NAME, value);
        return this;
    }

    public PersonTeamSelection teamNameStartsWith(String... value) {
        addStartsWith(TeamColumns.NAME, value);
        return this;
    }

    public PersonTeamSelection teamNameEndsWith(String... value) {
        addEndsWith(TeamColumns.NAME, value);
        return this;
    }

    public PersonTeamSelection orderByTeamName(boolean desc) {
        orderBy(TeamColumns.NAME, desc);
        return this;
    }

    public PersonTeamSelection orderByTeamName() {
        orderBy(TeamColumns.NAME, false);
        return this;
    }

    public PersonTeamSelection teamCountryCode(String... value) {
        addEquals(TeamColumns.COUNTRY_CODE, value);
        return this;
    }

    public PersonTeamSelection teamCountryCodeNot(String... value) {
        addNotEquals(TeamColumns.COUNTRY_CODE, value);
        return this;
    }

    public PersonTeamSelection teamCountryCodeLike(String... value) {
        addLike(TeamColumns.COUNTRY_CODE, value);
        return this;
    }

    public PersonTeamSelection teamCountryCodeContains(String... value) {
        addContains(TeamColumns.COUNTRY_CODE, value);
        return this;
    }

    public PersonTeamSelection teamCountryCodeStartsWith(String... value) {
        addStartsWith(TeamColumns.COUNTRY_CODE, value);
        return this;
    }

    public PersonTeamSelection teamCountryCodeEndsWith(String... value) {
        addEndsWith(TeamColumns.COUNTRY_CODE, value);
        return this;
    }

    public PersonTeamSelection orderByTeamCountryCode(boolean desc) {
        orderBy(TeamColumns.COUNTRY_CODE, desc);
        return this;
    }

    public PersonTeamSelection orderByTeamCountryCode() {
        orderBy(TeamColumns.COUNTRY_CODE, false);
        return this;
    }

    public PersonTeamSelection teamSerialNumberId(long... value) {
        addEquals(TeamColumns.SERIAL_NUMBER_ID, toObjectArray(value));
        return this;
    }

    public PersonTeamSelection teamSerialNumberIdNot(long... value) {
        addNotEquals(TeamColumns.SERIAL_NUMBER_ID, toObjectArray(value));
        return this;
    }

    public PersonTeamSelection teamSerialNumberIdGt(long value) {
        addGreaterThan(TeamColumns.SERIAL_NUMBER_ID, value);
        return this;
    }

    public PersonTeamSelection teamSerialNumberIdGtEq(long value) {
        addGreaterThanOrEquals(TeamColumns.SERIAL_NUMBER_ID, value);
        return this;
    }

    public PersonTeamSelection teamSerialNumberIdLt(long value) {
        addLessThan(TeamColumns.SERIAL_NUMBER_ID, value);
        return this;
    }

    public PersonTeamSelection teamSerialNumberIdLtEq(long value) {
        addLessThanOrEquals(TeamColumns.SERIAL_NUMBER_ID, value);
        return this;
    }

    public PersonTeamSelection orderByTeamSerialNumberId(boolean desc) {
        orderBy(TeamColumns.SERIAL_NUMBER_ID, desc);
        return this;
    }

    public PersonTeamSelection orderByTeamSerialNumberId() {
        orderBy(TeamColumns.SERIAL_NUMBER_ID, false);
        return this;
    }

    public PersonTeamSelection teamSerialNumberPart0(String... value) {
        addEquals(SerialNumberColumns.PART0, value);
        return this;
    }

    public PersonTeamSelection teamSerialNumberPart0Not(String... value) {
        addNotEquals(SerialNumberColumns.PART0, value);
        return this;
    }

    public PersonTeamSelection teamSerialNumberPart0Like(String... value) {
        addLike(SerialNumberColumns.PART0, value);
        return this;
    }

    public PersonTeamSelection teamSerialNumberPart0Contains(String... value) {
        addContains(SerialNumberColumns.PART0, value);
        return this;
    }

    public PersonTeamSelection teamSerialNumberPart0StartsWith(String... value) {
        addStartsWith(SerialNumberColumns.PART0, value);
        return this;
    }

    public PersonTeamSelection teamSerialNumberPart0EndsWith(String... value) {
        addEndsWith(SerialNumberColumns.PART0, value);
        return this;
    }

    public PersonTeamSelection orderByTeamSerialNumberPart0(boolean desc) {
        orderBy(SerialNumberColumns.PART0, desc);
        return this;
    }

    public PersonTeamSelection orderByTeamSerialNumberPart0() {
        orderBy(SerialNumberColumns.PART0, false);
        return this;
    }

    public PersonTeamSelection teamSerialNumberPart1(String... value) {
        addEquals(SerialNumberColumns.PART1, value);
        return this;
    }

    public PersonTeamSelection teamSerialNumberPart1Not(String... value) {
        addNotEquals(SerialNumberColumns.PART1, value);
        return this;
    }

    public PersonTeamSelection teamSerialNumberPart1Like(String... value) {
        addLike(SerialNumberColumns.PART1, value);
        return this;
    }

    public PersonTeamSelection teamSerialNumberPart1Contains(String... value) {
        addContains(SerialNumberColumns.PART1, value);
        return this;
    }

    public PersonTeamSelection teamSerialNumberPart1StartsWith(String... value) {
        addStartsWith(SerialNumberColumns.PART1, value);
        return this;
    }

    public PersonTeamSelection teamSerialNumberPart1EndsWith(String... value) {
        addEndsWith(SerialNumberColumns.PART1, value);
        return this;
    }

    public PersonTeamSelection orderByTeamSerialNumberPart1(boolean desc) {
        orderBy(SerialNumberColumns.PART1, desc);
        return this;
    }

    public PersonTeamSelection orderByTeamSerialNumberPart1() {
        orderBy(SerialNumberColumns.PART1, false);
        return this;
    }
}
