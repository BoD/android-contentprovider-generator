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
import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.CursorLoader;

import org.jraf.androidcontentprovidergenerator.sample.provider.base.AbstractSelection;
import org.jraf.androidcontentprovidergenerator.sample.provider.manual.*;

/**
 * Selection for the {@code product} table.
 */
@SuppressWarnings({"unused", "WeakerAccess", "Recycle"})
public class ProductSelection extends AbstractSelection<ProductSelection> {
    @Override
    protected Uri baseUri() {
        return ProductColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code ProductCursor} object, which is positioned before the first entry, or null.
     */
    public ProductCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new ProductCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, null)}.
     */
    public ProductCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code ProductCursor} object, which is positioned before the first entry, or null.
     */
    public ProductCursor query(Context context, String[] projection) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new ProductCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, null)}.
     */
    public ProductCursor query(Context context) {
        return query(context, null);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public CursorLoader getCursorLoader(Context context, String[] projection) {
        return new CursorLoader(context, uri(), projection, sel(), args(), order()) {
            @Override
            public Cursor loadInBackground() {
                return new ProductCursor(super.loadInBackground());
            }
        };
    }


    public ProductSelection id(long... value) {
        addEquals("product." + ProductColumns._ID, toObjectArray(value));
        return this;
    }

    public ProductSelection idNot(long... value) {
        addNotEquals("product." + ProductColumns._ID, toObjectArray(value));
        return this;
    }

    public ProductSelection orderById(boolean desc) {
        orderBy("product." + ProductColumns._ID, desc);
        return this;
    }

    public ProductSelection orderById() {
        return orderById(false);
    }

    public ProductSelection productId(long... value) {
        addEquals(ProductColumns.PRODUCT_ID, toObjectArray(value));
        return this;
    }

    public ProductSelection productIdNot(long... value) {
        addNotEquals(ProductColumns.PRODUCT_ID, toObjectArray(value));
        return this;
    }

    public ProductSelection productIdGt(long value) {
        addGreaterThan(ProductColumns.PRODUCT_ID, value);
        return this;
    }

    public ProductSelection productIdGtEq(long value) {
        addGreaterThanOrEquals(ProductColumns.PRODUCT_ID, value);
        return this;
    }

    public ProductSelection productIdLt(long value) {
        addLessThan(ProductColumns.PRODUCT_ID, value);
        return this;
    }

    public ProductSelection productIdLtEq(long value) {
        addLessThanOrEquals(ProductColumns.PRODUCT_ID, value);
        return this;
    }

    public ProductSelection orderByProductId(boolean desc) {
        orderBy(ProductColumns.PRODUCT_ID, desc);
        return this;
    }

    public ProductSelection orderByProductId() {
        orderBy(ProductColumns.PRODUCT_ID, false);
        return this;
    }

    public ProductSelection name(String... value) {
        addEquals(ProductColumns.NAME, value);
        return this;
    }

    public ProductSelection nameNot(String... value) {
        addNotEquals(ProductColumns.NAME, value);
        return this;
    }

    public ProductSelection nameLike(String... value) {
        addLike(ProductColumns.NAME, value);
        return this;
    }

    public ProductSelection nameContains(String... value) {
        addContains(ProductColumns.NAME, value);
        return this;
    }

    public ProductSelection nameStartsWith(String... value) {
        addStartsWith(ProductColumns.NAME, value);
        return this;
    }

    public ProductSelection nameEndsWith(String... value) {
        addEndsWith(ProductColumns.NAME, value);
        return this;
    }

    public ProductSelection orderByName(boolean desc) {
        orderBy(ProductColumns.NAME, desc);
        return this;
    }

    public ProductSelection orderByName() {
        orderBy(ProductColumns.NAME, false);
        return this;
    }

    public ProductSelection manualId(Long... value) {
        addEquals(ProductColumns.MANUAL_ID, value);
        return this;
    }

    public ProductSelection manualIdNot(Long... value) {
        addNotEquals(ProductColumns.MANUAL_ID, value);
        return this;
    }

    public ProductSelection manualIdGt(long value) {
        addGreaterThan(ProductColumns.MANUAL_ID, value);
        return this;
    }

    public ProductSelection manualIdGtEq(long value) {
        addGreaterThanOrEquals(ProductColumns.MANUAL_ID, value);
        return this;
    }

    public ProductSelection manualIdLt(long value) {
        addLessThan(ProductColumns.MANUAL_ID, value);
        return this;
    }

    public ProductSelection manualIdLtEq(long value) {
        addLessThanOrEquals(ProductColumns.MANUAL_ID, value);
        return this;
    }

    public ProductSelection orderByManualId(boolean desc) {
        orderBy(ProductColumns.MANUAL_ID, desc);
        return this;
    }

    public ProductSelection orderByManualId() {
        orderBy(ProductColumns.MANUAL_ID, false);
        return this;
    }

    public ProductSelection manualTitle(String... value) {
        addEquals(ManualColumns.TITLE, value);
        return this;
    }

    public ProductSelection manualTitleNot(String... value) {
        addNotEquals(ManualColumns.TITLE, value);
        return this;
    }

    public ProductSelection manualTitleLike(String... value) {
        addLike(ManualColumns.TITLE, value);
        return this;
    }

    public ProductSelection manualTitleContains(String... value) {
        addContains(ManualColumns.TITLE, value);
        return this;
    }

    public ProductSelection manualTitleStartsWith(String... value) {
        addStartsWith(ManualColumns.TITLE, value);
        return this;
    }

    public ProductSelection manualTitleEndsWith(String... value) {
        addEndsWith(ManualColumns.TITLE, value);
        return this;
    }

    public ProductSelection orderByManualTitle(boolean desc) {
        orderBy(ManualColumns.TITLE, desc);
        return this;
    }

    public ProductSelection orderByManualTitle() {
        orderBy(ManualColumns.TITLE, false);
        return this;
    }

    public ProductSelection manualIsbn(String... value) {
        addEquals(ManualColumns.ISBN, value);
        return this;
    }

    public ProductSelection manualIsbnNot(String... value) {
        addNotEquals(ManualColumns.ISBN, value);
        return this;
    }

    public ProductSelection manualIsbnLike(String... value) {
        addLike(ManualColumns.ISBN, value);
        return this;
    }

    public ProductSelection manualIsbnContains(String... value) {
        addContains(ManualColumns.ISBN, value);
        return this;
    }

    public ProductSelection manualIsbnStartsWith(String... value) {
        addStartsWith(ManualColumns.ISBN, value);
        return this;
    }

    public ProductSelection manualIsbnEndsWith(String... value) {
        addEndsWith(ManualColumns.ISBN, value);
        return this;
    }

    public ProductSelection orderByManualIsbn(boolean desc) {
        orderBy(ManualColumns.ISBN, desc);
        return this;
    }

    public ProductSelection orderByManualIsbn() {
        orderBy(ManualColumns.ISBN, false);
        return this;
    }
}
