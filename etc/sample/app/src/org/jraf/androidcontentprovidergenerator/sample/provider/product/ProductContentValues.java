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
package org.jraf.androidcontentprovidergenerator.sample.provider.product;

import java.util.Date;

import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.jraf.androidcontentprovidergenerator.sample.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code product} table.
 */
public class ProductContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return ProductColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable ProductSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public ProductContentValues putProductId(long value) {
        mContentValues.put(ProductColumns.PRODUCT_ID, value);
        return this;
    }


    public ProductContentValues putName(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("name must not be null");
        mContentValues.put(ProductColumns.NAME, value);
        return this;
    }


    /**
     * Optional manual id.
     */
    public ProductContentValues putManualId(@Nullable Long value) {
        mContentValues.put(ProductColumns.MANUAL_ID, value);
        return this;
    }

    public ProductContentValues putManualIdNull() {
        mContentValues.putNull(ProductColumns.MANUAL_ID);
        return this;
    }
}
