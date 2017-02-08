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
package org.jraf.androidcontentprovidergenerator.sample.provider.serialnumber;

// @formatter:off
import org.jraf.androidcontentprovidergenerator.sample.provider.base.BaseModel;

import java.util.Date;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * A serial number.
 */
@SuppressWarnings({"WeakerAccess", "unused", "ConstantConditions"})
public class SerialNumberBean implements SerialNumberModel {
    private long mId;
    private String mPart0;
    private String mPart1;

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
     * Unique id, first part.
     * Cannot be {@code null}.
     */
    @NonNull
    @Override
    public String getPart0() {
        return mPart0;
    }

    /**
     * Unique id, first part.
     * Must not be {@code null}.
     */
    public void setPart0(@NonNull String part0) {
        if (part0 == null) throw new IllegalArgumentException("part0 must not be null");
        mPart0 = part0;
    }

    /**
     * Unique id, second part.
     * Cannot be {@code null}.
     */
    @NonNull
    @Override
    public String getPart1() {
        return mPart1;
    }

    /**
     * Unique id, second part.
     * Must not be {@code null}.
     */
    public void setPart1(@NonNull String part1) {
        if (part1 == null) throw new IllegalArgumentException("part1 must not be null");
        mPart1 = part1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SerialNumberBean bean = (SerialNumberBean) o;
        return mId == bean.mId;
    }

    @Override
    public int hashCode() {
        return (int) (mId ^ (mId >>> 32));
    }

    /**
     * Instantiate a new SerialNumberBean with specified values.
     */
    @NonNull
    public static SerialNumberBean newInstance(long id, @NonNull String part0, @NonNull String part1) {
        if (part0 == null) throw new IllegalArgumentException("part0 must not be null");
        if (part1 == null) throw new IllegalArgumentException("part1 must not be null");
        SerialNumberBean res = new SerialNumberBean();
        res.mId = id;
        res.mPart0 = part0;
        res.mPart1 = part1;
        return res;
    }

    /**
     * Instantiate a new SerialNumberBean with all the values copied from the given model.
     */
    @NonNull
    public static SerialNumberBean copy(@NonNull SerialNumberModel from) {
        SerialNumberBean res = new SerialNumberBean();
        res.mId = from.getId();
        res.mPart0 = from.getPart0();
        res.mPart1 = from.getPart1();
        return res;
    }

    public static class Builder {
        private SerialNumberBean mRes = new SerialNumberBean();

        /**
         * Primary key.
         */
        public Builder id(long id) {
            mRes.mId = id;
            return this;
        }

        /**
         * Unique id, first part.
         * Must not be {@code null}.
         */
        public Builder part0(@NonNull String part0) {
            if (part0 == null) throw new IllegalArgumentException("part0 must not be null");
            mRes.mPart0 = part0;
            return this;
        }

        /**
         * Unique id, second part.
         * Must not be {@code null}.
         */
        public Builder part1(@NonNull String part1) {
            if (part1 == null) throw new IllegalArgumentException("part1 must not be null");
            mRes.mPart1 = part1;
            return this;
        }

        /**
         * Get a new SerialNumberBean built with the given values.
         */
        public SerialNumberBean build() {
            if (mRes.mPart0 == null) throw new IllegalArgumentException("part0 must not be null");
            if (mRes.mPart1 == null) throw new IllegalArgumentException("part1 must not be null");
            return mRes;
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }
}
