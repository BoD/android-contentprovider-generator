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
import org.jraf.androidcontentprovidergenerator.sample.provider.base.BaseModel;

import java.util.Date;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * A human being which is part of a team.
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public interface PersonModel extends BaseModel {

    /**
     * Primary key.
     */
    long getId();

    /**
     * First name of this person. For instance, John.
     * Cannot be {@code null}.
     */
    @NonNull
    String getFirstName();

    /**
     * Last name (a.k.a. Given name) of this person. For instance, Smith.
     * Cannot be {@code null}.
     */
    @NonNull
    String getLastName();

    /**
     * Get the {@code age} value.
     */
    int getAge();

    /**
     * Get the {@code birth_date} value.
     * Can be {@code null}.
     */
    @Nullable
    Date getBirthDate();

    /**
     * If {@code true}, this person has blue eyes. Otherwise, this person doesn't have blue eyes.
     */
    boolean getHasBlueEyes();

    /**
     * Get the {@code height} value.
     * Can be {@code null}.
     */
    @Nullable
    Float getHeight();

    /**
     * Get the {@code gender} value.
     * Cannot be {@code null}.
     */
    @NonNull
    Gender getGender();

    /**
     * Get the {@code country_code} value.
     * Cannot be {@code null}.
     */
    @NonNull
    String getCountryCode();
}
