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
package org.jraf.androidcontentprovidergenerator.sample.provider.manual;

// @formatter:off
import org.jraf.androidcontentprovidergenerator.sample.provider.base.BaseModel;

import java.util.Date;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * A manual related to a product.
 */
@SuppressWarnings({"WeakerAccess", "unused", "ConstantConditions"})
public class ManualBean implements ManualModel {
    private long mId;
    private String mTitle;
    private String mIsbn;

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
     * Get the {@code title} value.
     * Cannot be {@code null}.
     */
    @NonNull
    @Override
    public String getTitle() {
        return mTitle;
    }

    /**
     * Set the {@code title} value.
     * Must not be {@code null}.
     */
    public void setTitle(@NonNull String title) {
        if (title == null) throw new IllegalArgumentException("title must not be null");
        mTitle = title;
    }

    /**
     * Get the {@code isbn} value.
     * Cannot be {@code null}.
     */
    @NonNull
    @Override
    public String getIsbn() {
        return mIsbn;
    }

    /**
     * Set the {@code isbn} value.
     * Must not be {@code null}.
     */
    public void setIsbn(@NonNull String isbn) {
        if (isbn == null) throw new IllegalArgumentException("isbn must not be null");
        mIsbn = isbn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ManualBean bean = (ManualBean) o;
        return mId == bean.mId;
    }

    @Override
    public int hashCode() {
        return (int) (mId ^ (mId >>> 32));
    }

    /**
     * Instantiate a new ManualBean with specified values.
     */
    @NonNull
    public static ManualBean newInstance(long id, @NonNull String title, @NonNull String isbn) {
        if (title == null) throw new IllegalArgumentException("title must not be null");
        if (isbn == null) throw new IllegalArgumentException("isbn must not be null");
        ManualBean res = new ManualBean();
        res.mId = id;
        res.mTitle = title;
        res.mIsbn = isbn;
        return res;
    }

    /**
     * Instantiate a new ManualBean with all the values copied from the given model.
     */
    @NonNull
    public static ManualBean copy(@NonNull ManualModel from) {
        ManualBean res = new ManualBean();
        res.mId = from.getId();
        res.mTitle = from.getTitle();
        res.mIsbn = from.getIsbn();
        return res;
    }

    public static class Builder {
        private ManualBean mRes = new ManualBean();

        /**
         * Primary key.
         */
        public Builder id(long id) {
            mRes.mId = id;
            return this;
        }

        /**
         * Set the {@code title} value.
         * Must not be {@code null}.
         */
        public Builder title(@NonNull String title) {
            if (title == null) throw new IllegalArgumentException("title must not be null");
            mRes.mTitle = title;
            return this;
        }

        /**
         * Set the {@code isbn} value.
         * Must not be {@code null}.
         */
        public Builder isbn(@NonNull String isbn) {
            if (isbn == null) throw new IllegalArgumentException("isbn must not be null");
            mRes.mIsbn = isbn;
            return this;
        }

        /**
         * Get a new ManualBean built with the given values.
         */
        public ManualBean build() {
            if (mRes.mTitle == null) throw new IllegalArgumentException("title must not be null");
            if (mRes.mIsbn == null) throw new IllegalArgumentException("isbn must not be null");
            return mRes;
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }
}
