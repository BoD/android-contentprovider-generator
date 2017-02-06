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
package org.jraf.androidcontentprovidergenerator.sample.provider.company;

// @formatter:off
import org.jraf.androidcontentprovidergenerator.sample.provider.base.BaseModel;

import java.util.Date;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * A commercial business.
 */
@SuppressWarnings({"WeakerAccess", "unused", "ConstantConditions"})
public class CompanyBean implements CompanyModel {
    private long mId;
    private String mName;
    private String mAddress;
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
     * The commercial name of this company.
     * Cannot be {@code null}.
     */
    @NonNull
    @Override
    public String getName() {
        return mName;
    }

    /**
     * The commercial name of this company.
     * Must not be {@code null}.
     */
    public void setName(@NonNull String name) {
        if (name == null) throw new IllegalArgumentException("name must not be null");
        mName = name;
    }

    /**
     * The full address of this company.
     * Can be {@code null}.
     */
    @Nullable
    @Override
    public String getAddress() {
        return mAddress;
    }

    /**
     * The full address of this company.
     * Can be {@code null}.
     */
    public void setAddress(@Nullable String address) {
        mAddress = address;
    }

    /**
     * The serial number of this company.
     */
    @Override
    public long getSerialNumberId() {
        return mSerialNumberId;
    }

    /**
     * The serial number of this company.
     */
    public void setSerialNumberId(long serialNumberId) {
        mSerialNumberId = serialNumberId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompanyBean bean = (CompanyBean) o;
        return mId == bean.mId;
    }

    @Override
    public int hashCode() {
        return (int) (mId ^ (mId >>> 32));
    }

    /**
     * Instantiate a new CompanyBean with specified values.
     */
    @NonNull
    public static CompanyBean newInstance(long id, @NonNull String name, @Nullable String address, long serialNumberId) {
        if (name == null) throw new IllegalArgumentException("name must not be null");
        CompanyBean res = new CompanyBean();
        res.mId = id;
        res.mName = name;
        res.mAddress = address;
        res.mSerialNumberId = serialNumberId;
        return res;
    }

    /**
     * Instantiate a new CompanyBean with all the values copied from the given model.
     */
    @NonNull
    public static CompanyBean copy(@NonNull CompanyModel from) {
        CompanyBean res = new CompanyBean();
        res.mId = from.getId();
        res.mName = from.getName();
        res.mAddress = from.getAddress();
        res.mSerialNumberId = from.getSerialNumberId();
        return res;
    }

    public static class Builder {
        private CompanyBean mRes = new CompanyBean();

        /**
         * Primary key.
         */
        public Builder id(long id) {
            mRes.mId = id;
            return this;
        }

        /**
         * The commercial name of this company.
         * Must not be {@code null}.
         */
        public Builder name(@NonNull String name) {
            if (name == null) throw new IllegalArgumentException("name must not be null");
            mRes.mName = name;
            return this;
        }

        /**
         * The full address of this company.
         * Can be {@code null}.
         */
        public Builder address(@Nullable String address) {
            mRes.mAddress = address;
            return this;
        }

        /**
         * The serial number of this company.
         */
        public Builder serialNumberId(long serialNumberId) {
            mRes.mSerialNumberId = serialNumberId;
            return this;
        }

        /**
         * Get a new CompanyBean built with the given values.
         */
        public CompanyBean build() {
            if (mRes.mName == null) throw new IllegalArgumentException("name must not be null");
            return mRes;
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }
}
