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
package org.jraf.androidcontentprovidergenerator.sample.provider.team;

// @formatter:off
import org.jraf.androidcontentprovidergenerator.sample.provider.base.BaseModel;

import java.util.Date;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * A group of people who work together.
 */
@SuppressWarnings({"WeakerAccess", "unused", "ConstantConditions"})
public class TeamBean implements TeamModel {
    private long mId;
    private long mCompanyId;
    private String mName;
    private String mCountryCode;
    private long mSerialNumberId;

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
     * Get the {@code company_id} value.
     */
    @Override
    public long getCompanyId() {
        return mCompanyId;
    }

    /**
     * Set the {@code company_id} value.
     */
    public void setCompanyId(long companyId) {
        mCompanyId = companyId;
    }

    /**
     * Get the {@code name} value.
     * Cannot be {@code null}.
     */
    @NonNull
    @Override
    public String getName() {
        return mName;
    }

    /**
     * Set the {@code name} value.
     * Must not be {@code null}.
     */
    public void setName(@NonNull String name) {
        if (name == null) throw new IllegalArgumentException("name must not be null");
        mName = name;
    }

    /**
     * 2 letter country code where this team operates.
     * Cannot be {@code null}.
     */
    @NonNull
    @Override
    public String getCountryCode() {
        return mCountryCode;
    }

    /**
     * 2 letter country code where this team operates.
     * Must not be {@code null}.
     */
    public void setCountryCode(@NonNull String countryCode) {
        if (countryCode == null) throw new IllegalArgumentException("countryCode must not be null");
        mCountryCode = countryCode;
    }

    /**
     * The serial number of this team.
     */
    @Override
    public long getSerialNumberId() {
        return mSerialNumberId;
    }

    /**
     * The serial number of this team.
     */
    public void setSerialNumberId(long serialNumberId) {
        mSerialNumberId = serialNumberId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeamBean bean = (TeamBean) o;
        return mId == bean.mId;
    }

    @Override
    public int hashCode() {
        return (int) (mId ^ (mId >>> 32));
    }

    /**
     * Instantiate a new TeamBean with specified values.
     */
    @NonNull
    public static TeamBean newInstance(long id, long companyId, @NonNull String name, @NonNull String countryCode, long serialNumberId) {
        if (name == null) throw new IllegalArgumentException("name must not be null");
        if (countryCode == null) throw new IllegalArgumentException("countryCode must not be null");
        TeamBean res = new TeamBean();
        res.mId = id;
        res.mCompanyId = companyId;
        res.mName = name;
        res.mCountryCode = countryCode;
        res.mSerialNumberId = serialNumberId;
        return res;
    }

    /**
     * Instantiate a new TeamBean with all the values copied from the given model.
     */
    @NonNull
    public static TeamBean copy(@NonNull TeamModel from) {
        TeamBean res = new TeamBean();
        res.mId = from.getId();
        res.mCompanyId = from.getCompanyId();
        res.mName = from.getName();
        res.mCountryCode = from.getCountryCode();
        res.mSerialNumberId = from.getSerialNumberId();
        return res;
    }

    public static class Builder {
        private TeamBean mRes = new TeamBean();

        /**
         * Primary key.
         */
        public Builder id(long id) {
            mRes.mId = id;
            return this;
        }

        /**
         * Set the {@code company_id} value.
         */
        public Builder companyId(long companyId) {
            mRes.mCompanyId = companyId;
            return this;
        }

        /**
         * Set the {@code name} value.
         * Must not be {@code null}.
         */
        public Builder name(@NonNull String name) {
            if (name == null) throw new IllegalArgumentException("name must not be null");
            mRes.mName = name;
            return this;
        }

        /**
         * 2 letter country code where this team operates.
         * Must not be {@code null}.
         */
        public Builder countryCode(@NonNull String countryCode) {
            if (countryCode == null) throw new IllegalArgumentException("countryCode must not be null");
            mRes.mCountryCode = countryCode;
            return this;
        }

        /**
         * The serial number of this team.
         */
        public Builder serialNumberId(long serialNumberId) {
            mRes.mSerialNumberId = serialNumberId;
            return this;
        }

        /**
         * Get a new TeamBean built with the given values.
         */
        public TeamBean build() {
            if (mRes.mName == null) throw new IllegalArgumentException("name must not be null");
            if (mRes.mCountryCode == null) throw new IllegalArgumentException("countryCode must not be null");
            return mRes;
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }
}
