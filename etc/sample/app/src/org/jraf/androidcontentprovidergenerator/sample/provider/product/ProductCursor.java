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

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.jraf.androidcontentprovidergenerator.sample.provider.base.AbstractCursor;
import org.jraf.androidcontentprovidergenerator.sample.provider.manual.*;

/**
 * Cursor wrapper for the {@code product} table.
 */
public class ProductCursor extends AbstractCursor implements ProductModel {
    public ProductCursor(Cursor cursor) {
        super(cursor);
    }

    @Override
    public long getId() {
        return getLongOrNull(ProductColumns._ID);
    }

    /**
     * Get the {@code product_id} value.
     */
    public long getProductId() {
        Long res = getLongOrNull(ProductColumns.PRODUCT_ID);
        if (res == null)
            throw new NullPointerException("The value of 'product_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code name} value.
     * Cannot be {@code null}.
     */
    @NonNull
    public String getName() {
        String res = getStringOrNull(ProductColumns.NAME);
        if (res == null)
            throw new NullPointerException("The value of 'name' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Optional manual id.
     * Can be {@code null}.
     */
    @Nullable
    public Long getManualId() {
        Long res = getLongOrNull(ProductColumns.MANUAL_ID);
        return res;
    }

    /**
     * Get the {@code title} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getManualTitle() {
        String res = getStringOrNull(ManualColumns.TITLE);
        return res;
    }

    /**
     * Get the {@code isbn} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getManualIsbn() {
        String res = getStringOrNull(ManualColumns.ISBN);
        return res;
    }
}
