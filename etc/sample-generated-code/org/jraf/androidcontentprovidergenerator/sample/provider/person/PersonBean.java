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
@SuppressWarnings({"WeakerAccess", "unused", "ConstantConditions"})
public class PersonBean implements PersonModel {
    private long mId;
    private String mFirstName;
    private String mLastName;
    private int mAge;
    private Date mBirthDate;
    private boolean mHasBlueEyes;
    private Float mHeight;
    private Gender mGender;
    private String mCountryCode;

    /**
     * Primary key.
     */
    @Override
    public long getId() {
        return mId;
    }

    /**
     * Primary key.
     */
    public void setId(long id) {
        mId = id;
    }

    /**
     * First name of this person. For instance, John.
     * Cannot be {@code null}.
     */
    @NonNull
    @Override
    public String getFirstName() {
        return mFirstName;
    }

    /**
     * First name of this person. For instance, John.
     * Must not be {@code null}.
     */
    public void setFirstName(@NonNull String firstName) {
        if (firstName == null) throw new IllegalArgumentException("firstName must not be null");
        mFirstName = firstName;
    }

    /**
     * Last name (a.k.a. Given name) of this person. For instance, Smith.
     * Cannot be {@code null}.
     */
    @NonNull
    @Override
    public String getLastName() {
        return mLastName;
    }

    /**
     * Last name (a.k.a. Given name) of this person. For instance, Smith.
     * Must not be {@code null}.
     */
    public void setLastName(@NonNull String lastName) {
        if (lastName == null) throw new IllegalArgumentException("lastName must not be null");
        mLastName = lastName;
    }

    /**
     * Get the {@code age} value.
     */
    @Override
    public int getAge() {
        return mAge;
    }

    /**
     * Set the {@code age} value.
     */
    public void setAge(int age) {
        mAge = age;
    }

    /**
     * Get the {@code birth_date} value.
     * Can be {@code null}.
     */
    @Nullable
    @Override
    public Date getBirthDate() {
        return mBirthDate;
    }

    /**
     * Set the {@code birth_date} value.
     * Can be {@code null}.
     */
    public void setBirthDate(@Nullable Date birthDate) {
        mBirthDate = birthDate;
    }

    /**
     * If {@code true}, this person has blue eyes. Otherwise, this person doesn't have blue eyes.
     */
    @Override
    public boolean getHasBlueEyes() {
        return mHasBlueEyes;
    }

    /**
     * If {@code true}, this person has blue eyes. Otherwise, this person doesn't have blue eyes.
     */
    public void setHasBlueEyes(boolean hasBlueEyes) {
        mHasBlueEyes = hasBlueEyes;
    }

    /**
     * Get the {@code height} value.
     * Can be {@code null}.
     */
    @Nullable
    @Override
    public Float getHeight() {
        return mHeight;
    }

    /**
     * Set the {@code height} value.
     * Can be {@code null}.
     */
    public void setHeight(@Nullable Float height) {
        mHeight = height;
    }

    /**
     * Get the {@code gender} value.
     * Cannot be {@code null}.
     */
    @NonNull
    @Override
    public Gender getGender() {
        return mGender;
    }

    /**
     * Set the {@code gender} value.
     * Must not be {@code null}.
     */
    public void setGender(@NonNull Gender gender) {
        if (gender == null) throw new IllegalArgumentException("gender must not be null");
        mGender = gender;
    }

    /**
     * Get the {@code country_code} value.
     * Cannot be {@code null}.
     */
    @NonNull
    @Override
    public String getCountryCode() {
        return mCountryCode;
    }

    /**
     * Set the {@code country_code} value.
     * Must not be {@code null}.
     */
    public void setCountryCode(@NonNull String countryCode) {
        if (countryCode == null) throw new IllegalArgumentException("countryCode must not be null");
        mCountryCode = countryCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonBean bean = (PersonBean) o;
        return mId == bean.mId;
    }

    @Override
    public int hashCode() {
        return (int) (mId ^ (mId >>> 32));
    }

    /**
     * Instantiate a new PersonBean with specified values.
     */
    @NonNull
    public static PersonBean newInstance(long id, @NonNull String firstName, @NonNull String lastName, int age, @Nullable Date birthDate, boolean hasBlueEyes, @Nullable Float height, @NonNull Gender gender, @NonNull String countryCode) {
        if (firstName == null) throw new IllegalArgumentException("firstName must not be null");
        if (lastName == null) throw new IllegalArgumentException("lastName must not be null");
        if (gender == null) throw new IllegalArgumentException("gender must not be null");
        if (countryCode == null) throw new IllegalArgumentException("countryCode must not be null");
        PersonBean res = new PersonBean();
        res.mId = id;
        res.mFirstName = firstName;
        res.mLastName = lastName;
        res.mAge = age;
        res.mBirthDate = birthDate;
        res.mHasBlueEyes = hasBlueEyes;
        res.mHeight = height;
        res.mGender = gender;
        res.mCountryCode = countryCode;
        return res;
    }

    /**
     * Instantiate a new PersonBean with all the values copied from the given model.
     */
    @NonNull
    public static PersonBean copy(@NonNull PersonModel from) {
        PersonBean res = new PersonBean();
        res.mId = from.getId();
        res.mFirstName = from.getFirstName();
        res.mLastName = from.getLastName();
        res.mAge = from.getAge();
        res.mBirthDate = from.getBirthDate();
        res.mHasBlueEyes = from.getHasBlueEyes();
        res.mHeight = from.getHeight();
        res.mGender = from.getGender();
        res.mCountryCode = from.getCountryCode();
        return res;
    }

    public static class Builder {
        private PersonBean mRes = new PersonBean();

        /**
         * Primary key.
         */
        public Builder id(long id) {
            mRes.mId = id;
            return this;
        }

        /**
         * First name of this person. For instance, John.
         * Must not be {@code null}.
         */
        public Builder firstName(@NonNull String firstName) {
            if (firstName == null) throw new IllegalArgumentException("firstName must not be null");
            mRes.mFirstName = firstName;
            return this;
        }

        /**
         * Last name (a.k.a. Given name) of this person. For instance, Smith.
         * Must not be {@code null}.
         */
        public Builder lastName(@NonNull String lastName) {
            if (lastName == null) throw new IllegalArgumentException("lastName must not be null");
            mRes.mLastName = lastName;
            return this;
        }

        /**
         * Set the {@code age} value.
         */
        public Builder age(int age) {
            mRes.mAge = age;
            return this;
        }

        /**
         * Set the {@code birth_date} value.
         * Can be {@code null}.
         */
        public Builder birthDate(@Nullable Date birthDate) {
            mRes.mBirthDate = birthDate;
            return this;
        }

        /**
         * If {@code true}, this person has blue eyes. Otherwise, this person doesn't have blue eyes.
         */
        public Builder hasBlueEyes(boolean hasBlueEyes) {
            mRes.mHasBlueEyes = hasBlueEyes;
            return this;
        }

        /**
         * Set the {@code height} value.
         * Can be {@code null}.
         */
        public Builder height(@Nullable Float height) {
            mRes.mHeight = height;
            return this;
        }

        /**
         * Set the {@code gender} value.
         * Must not be {@code null}.
         */
        public Builder gender(@NonNull Gender gender) {
            if (gender == null) throw new IllegalArgumentException("gender must not be null");
            mRes.mGender = gender;
            return this;
        }

        /**
         * Set the {@code country_code} value.
         * Must not be {@code null}.
         */
        public Builder countryCode(@NonNull String countryCode) {
            if (countryCode == null) throw new IllegalArgumentException("countryCode must not be null");
            mRes.mCountryCode = countryCode;
            return this;
        }

        /**
         * Get a new PersonBean built with the given values.
         */
        public PersonBean build() {
            if (mRes.mFirstName == null) throw new IllegalArgumentException("firstName must not be null");
            if (mRes.mLastName == null) throw new IllegalArgumentException("lastName must not be null");
            if (mRes.mGender == null) throw new IllegalArgumentException("gender must not be null");
            if (mRes.mCountryCode == null) throw new IllegalArgumentException("countryCode must not be null");
            return mRes;
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }
}
