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
package org.jraf.androidcontentprovidergenerator.sample.provider.product;

// @formatter:off
import org.jraf.androidcontentprovidergenerator.sample.provider.base.BaseModel;

import java.util.Date;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * A product that the company sells.
 */
@SuppressWarnings({"WeakerAccess", "unused", "ConstantConditions"})
public class ProductBean implements ProductModel {
    private long mProductId;
    private String mName;
    private Long mManualId;

    /**
     * Get the {@code product_id} value.
     */
    @Override
    public long getProductId() {
        return mProductId;
    }

    /**
     * Set the {@code product_id} value.
     */
    public void setProductId(long productId) {
        mProductId = productId;
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
     * Optional manual id.
     * Can be {@code null}.
     */
    @Nullable
    @Override
    public Long getManualId() {
        return mManualId;
    }

    /**
     * Optional manual id.
     * Can be {@code null}.
     */
    public void setManualId(@Nullable Long manualId) {
        mManualId = manualId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductBean bean = (ProductBean) o;
        return mProductId == bean.mProductId;
    }

    @Override
    public int hashCode() {
        return (int) (mProductId ^ (mProductId >>> 32));
    }

    /**
     * Instantiate a new ProductBean with specified values.
     */
    @NonNull
    public static ProductBean newInstance(long productId, @NonNull String name, @Nullable Long manualId) {
        if (name == null) throw new IllegalArgumentException("name must not be null");
        ProductBean res = new ProductBean();
        res.mProductId = productId;
        res.mName = name;
        res.mManualId = manualId;
        return res;
    }

    /**
     * Instantiate a new ProductBean with all the values copied from the given model.
     */
    @NonNull
    public static ProductBean copy(@NonNull ProductModel from) {
        ProductBean res = new ProductBean();
        res.mProductId = from.getProductId();
        res.mName = from.getName();
        res.mManualId = from.getManualId();
        return res;
    }

    public static class Builder {
        private ProductBean mRes = new ProductBean();

        /**
         * Set the {@code product_id} value.
         */
        public Builder productId(long productId) {
            mRes.mProductId = productId;
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
         * Optional manual id.
         * Can be {@code null}.
         */
        public Builder manualId(@Nullable Long manualId) {
            mRes.mManualId = manualId;
            return this;
        }

        /**
         * Get a new ProductBean built with the given values.
         */
        public ProductBean build() {
            if (mRes.mName == null) throw new IllegalArgumentException("name must not be null");
            return mRes;
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }
}
