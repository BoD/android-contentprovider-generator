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
package org.jraf.androidcontentprovidergenerator.sample.provider;

// @formatter:off
import java.util.Arrays;

import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import org.jraf.androidcontentprovidergenerator.sample.BuildConfig;
import org.jraf.androidcontentprovidergenerator.sample.provider.base.BaseContentProvider;
import org.jraf.androidcontentprovidergenerator.sample.provider.company.CompanyColumns;
import org.jraf.androidcontentprovidergenerator.sample.provider.manual.ManualColumns;
import org.jraf.androidcontentprovidergenerator.sample.provider.person.PersonColumns;
import org.jraf.androidcontentprovidergenerator.sample.provider.personteam.PersonTeamColumns;
import org.jraf.androidcontentprovidergenerator.sample.provider.product.ProductColumns;
import org.jraf.androidcontentprovidergenerator.sample.provider.serialnumber.SerialNumberColumns;
import org.jraf.androidcontentprovidergenerator.sample.provider.team.TeamColumns;

public class SampleProvider extends BaseContentProvider {
    private static final String TAG = SampleProvider.class.getSimpleName();

    private static final boolean DEBUG = BuildConfig.LOG_DEBUG_PROVIDER;

    private static final String TYPE_CURSOR_ITEM = "vnd.android.cursor.item/";
    private static final String TYPE_CURSOR_DIR = "vnd.android.cursor.dir/";

    public static final String AUTHORITY = "org.jraf.androidcontentprovidergenerator.sample.provider";
    public static final String CONTENT_URI_BASE = "content://" + AUTHORITY;

    private static final int URI_TYPE_COMPANY = 0;
    private static final int URI_TYPE_COMPANY_ID = 1;

    private static final int URI_TYPE_MANUAL = 2;
    private static final int URI_TYPE_MANUAL_ID = 3;

    private static final int URI_TYPE_PERSON = 4;
    private static final int URI_TYPE_PERSON_ID = 5;

    private static final int URI_TYPE_PERSON_TEAM = 6;
    private static final int URI_TYPE_PERSON_TEAM_ID = 7;

    private static final int URI_TYPE_PRODUCT = 8;
    private static final int URI_TYPE_PRODUCT_ID = 9;

    private static final int URI_TYPE_SERIAL_NUMBER = 10;
    private static final int URI_TYPE_SERIAL_NUMBER_ID = 11;

    private static final int URI_TYPE_TEAM = 12;
    private static final int URI_TYPE_TEAM_ID = 13;



    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        URI_MATCHER.addURI(AUTHORITY, CompanyColumns.TABLE_NAME, URI_TYPE_COMPANY);
        URI_MATCHER.addURI(AUTHORITY, CompanyColumns.TABLE_NAME + "/#", URI_TYPE_COMPANY_ID);
        URI_MATCHER.addURI(AUTHORITY, ManualColumns.TABLE_NAME, URI_TYPE_MANUAL);
        URI_MATCHER.addURI(AUTHORITY, ManualColumns.TABLE_NAME + "/#", URI_TYPE_MANUAL_ID);
        URI_MATCHER.addURI(AUTHORITY, PersonColumns.TABLE_NAME, URI_TYPE_PERSON);
        URI_MATCHER.addURI(AUTHORITY, PersonColumns.TABLE_NAME + "/#", URI_TYPE_PERSON_ID);
        URI_MATCHER.addURI(AUTHORITY, PersonTeamColumns.TABLE_NAME, URI_TYPE_PERSON_TEAM);
        URI_MATCHER.addURI(AUTHORITY, PersonTeamColumns.TABLE_NAME + "/#", URI_TYPE_PERSON_TEAM_ID);
        URI_MATCHER.addURI(AUTHORITY, ProductColumns.TABLE_NAME, URI_TYPE_PRODUCT);
        URI_MATCHER.addURI(AUTHORITY, ProductColumns.TABLE_NAME + "/#", URI_TYPE_PRODUCT_ID);
        URI_MATCHER.addURI(AUTHORITY, SerialNumberColumns.TABLE_NAME, URI_TYPE_SERIAL_NUMBER);
        URI_MATCHER.addURI(AUTHORITY, SerialNumberColumns.TABLE_NAME + "/#", URI_TYPE_SERIAL_NUMBER_ID);
        URI_MATCHER.addURI(AUTHORITY, TeamColumns.TABLE_NAME, URI_TYPE_TEAM);
        URI_MATCHER.addURI(AUTHORITY, TeamColumns.TABLE_NAME + "/#", URI_TYPE_TEAM_ID);
    }

    @Override
    protected SQLiteOpenHelper createSqLiteOpenHelper() {
        return SampleSQLiteOpenHelper.getInstance(getContext());
    }

    @Override
    protected boolean hasDebug() {
        return DEBUG;
    }

    @Override
    public String getType(Uri uri) {
        int match = URI_MATCHER.match(uri);
        switch (match) {
            case URI_TYPE_COMPANY:
                return TYPE_CURSOR_DIR + CompanyColumns.TABLE_NAME;
            case URI_TYPE_COMPANY_ID:
                return TYPE_CURSOR_ITEM + CompanyColumns.TABLE_NAME;

            case URI_TYPE_MANUAL:
                return TYPE_CURSOR_DIR + ManualColumns.TABLE_NAME;
            case URI_TYPE_MANUAL_ID:
                return TYPE_CURSOR_ITEM + ManualColumns.TABLE_NAME;

            case URI_TYPE_PERSON:
                return TYPE_CURSOR_DIR + PersonColumns.TABLE_NAME;
            case URI_TYPE_PERSON_ID:
                return TYPE_CURSOR_ITEM + PersonColumns.TABLE_NAME;

            case URI_TYPE_PERSON_TEAM:
                return TYPE_CURSOR_DIR + PersonTeamColumns.TABLE_NAME;
            case URI_TYPE_PERSON_TEAM_ID:
                return TYPE_CURSOR_ITEM + PersonTeamColumns.TABLE_NAME;

            case URI_TYPE_PRODUCT:
                return TYPE_CURSOR_DIR + ProductColumns.TABLE_NAME;
            case URI_TYPE_PRODUCT_ID:
                return TYPE_CURSOR_ITEM + ProductColumns.TABLE_NAME;

            case URI_TYPE_SERIAL_NUMBER:
                return TYPE_CURSOR_DIR + SerialNumberColumns.TABLE_NAME;
            case URI_TYPE_SERIAL_NUMBER_ID:
                return TYPE_CURSOR_ITEM + SerialNumberColumns.TABLE_NAME;

            case URI_TYPE_TEAM:
                return TYPE_CURSOR_DIR + TeamColumns.TABLE_NAME;
            case URI_TYPE_TEAM_ID:
                return TYPE_CURSOR_ITEM + TeamColumns.TABLE_NAME;

        }
        return null;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        if (DEBUG) Log.d(TAG, "insert uri=" + uri + " values=" + values);
        return super.insert(uri, values);
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        if (DEBUG) Log.d(TAG, "bulkInsert uri=" + uri + " values.length=" + values.length);
        return super.bulkInsert(uri, values);
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if (DEBUG) Log.d(TAG, "update uri=" + uri + " values=" + values + " selection=" + selection + " selectionArgs=" + Arrays.toString(selectionArgs));
        return super.update(uri, values, selection, selectionArgs);
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        if (DEBUG) Log.d(TAG, "delete uri=" + uri + " selection=" + selection + " selectionArgs=" + Arrays.toString(selectionArgs));
        return super.delete(uri, selection, selectionArgs);
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        if (DEBUG)
            Log.d(TAG, "query uri=" + uri + " selection=" + selection + " selectionArgs=" + Arrays.toString(selectionArgs) + " sortOrder=" + sortOrder
                    + " groupBy=" + uri.getQueryParameter(QUERY_GROUP_BY) + " having=" + uri.getQueryParameter(QUERY_HAVING) + " limit=" + uri.getQueryParameter(QUERY_LIMIT));
        return super.query(uri, projection, selection, selectionArgs, sortOrder);
    }

    @Override
    protected QueryParams getQueryParams(Uri uri, String selection, String[] projection) {
        QueryParams res = new QueryParams();
        String id = null;
        int matchedId = URI_MATCHER.match(uri);
        switch (matchedId) {
            case URI_TYPE_COMPANY:
            case URI_TYPE_COMPANY_ID:
                res.table = CompanyColumns.TABLE_NAME;
                res.idColumn = CompanyColumns._ID;
                res.tablesWithJoins = CompanyColumns.TABLE_NAME;
                if (SerialNumberColumns.hasColumns(projection)) {
                    res.tablesWithJoins += " LEFT OUTER JOIN " + SerialNumberColumns.TABLE_NAME + " AS " + CompanyColumns.PREFIX_SERIAL_NUMBER + " ON " + CompanyColumns.TABLE_NAME + "." + CompanyColumns.SERIAL_NUMBER_ID + "=" + CompanyColumns.PREFIX_SERIAL_NUMBER + "." + SerialNumberColumns._ID;
                }
                res.orderBy = CompanyColumns.DEFAULT_ORDER;
                break;

            case URI_TYPE_MANUAL:
            case URI_TYPE_MANUAL_ID:
                res.table = ManualColumns.TABLE_NAME;
                res.idColumn = ManualColumns._ID;
                res.tablesWithJoins = ManualColumns.TABLE_NAME;
                res.orderBy = ManualColumns.DEFAULT_ORDER;
                break;

            case URI_TYPE_PERSON:
            case URI_TYPE_PERSON_ID:
                res.table = PersonColumns.TABLE_NAME;
                res.idColumn = PersonColumns._ID;
                res.tablesWithJoins = PersonColumns.TABLE_NAME;
                res.orderBy = PersonColumns.DEFAULT_ORDER;
                break;

            case URI_TYPE_PERSON_TEAM:
            case URI_TYPE_PERSON_TEAM_ID:
                res.table = PersonTeamColumns.TABLE_NAME;
                res.idColumn = PersonTeamColumns._ID;
                res.tablesWithJoins = PersonTeamColumns.TABLE_NAME;
                if (PersonColumns.hasColumns(projection)) {
                    res.tablesWithJoins += " LEFT OUTER JOIN " + PersonColumns.TABLE_NAME + " AS " + PersonTeamColumns.PREFIX_PERSON + " ON " + PersonTeamColumns.TABLE_NAME + "." + PersonTeamColumns.PERSON_ID + "=" + PersonTeamColumns.PREFIX_PERSON + "." + PersonColumns._ID;
                }
                if (TeamColumns.hasColumns(projection) || CompanyColumns.hasColumns(projection) || SerialNumberColumns.hasColumns(projection) || SerialNumberColumns.hasColumns(projection)) {
                    res.tablesWithJoins += " LEFT OUTER JOIN " + TeamColumns.TABLE_NAME + " AS " + PersonTeamColumns.PREFIX_TEAM + " ON " + PersonTeamColumns.TABLE_NAME + "." + PersonTeamColumns.TEAM_ID + "=" + PersonTeamColumns.PREFIX_TEAM + "." + TeamColumns._ID;
                }
                if (CompanyColumns.hasColumns(projection) || SerialNumberColumns.hasColumns(projection)) {
                    res.tablesWithJoins += " LEFT OUTER JOIN " + CompanyColumns.TABLE_NAME + " AS " + TeamColumns.PREFIX_COMPANY + " ON " + PersonTeamColumns.PREFIX_TEAM + "." + TeamColumns.COMPANY_ID + "=" + TeamColumns.PREFIX_COMPANY + "." + CompanyColumns._ID;
                }
                if (SerialNumberColumns.hasColumns(projection)) {
                    res.tablesWithJoins += " LEFT OUTER JOIN " + SerialNumberColumns.TABLE_NAME + " AS " + CompanyColumns.PREFIX_SERIAL_NUMBER + " ON " + TeamColumns.PREFIX_COMPANY + "." + CompanyColumns.SERIAL_NUMBER_ID + "=" + CompanyColumns.PREFIX_SERIAL_NUMBER + "." + SerialNumberColumns._ID;
                }
                if (SerialNumberColumns.hasColumns(projection)) {
                    res.tablesWithJoins += " LEFT OUTER JOIN " + SerialNumberColumns.TABLE_NAME + " AS " + TeamColumns.PREFIX_SERIAL_NUMBER + " ON " + PersonTeamColumns.PREFIX_TEAM + "." + TeamColumns.SERIAL_NUMBER_ID + "=" + TeamColumns.PREFIX_SERIAL_NUMBER + "." + SerialNumberColumns._ID;
                }
                res.orderBy = PersonTeamColumns.DEFAULT_ORDER;
                break;

            case URI_TYPE_PRODUCT:
            case URI_TYPE_PRODUCT_ID:
                res.table = ProductColumns.TABLE_NAME;
                res.idColumn = ProductColumns._ID;
                res.tablesWithJoins = ProductColumns.TABLE_NAME;
                if (ManualColumns.hasColumns(projection)) {
                    res.tablesWithJoins += " LEFT OUTER JOIN " + ManualColumns.TABLE_NAME + " AS " + ProductColumns.PREFIX_MANUAL + " ON " + ProductColumns.TABLE_NAME + "." + ProductColumns.MANUAL_ID + "=" + ProductColumns.PREFIX_MANUAL + "." + ManualColumns._ID;
                }
                res.orderBy = ProductColumns.DEFAULT_ORDER;
                break;

            case URI_TYPE_SERIAL_NUMBER:
            case URI_TYPE_SERIAL_NUMBER_ID:
                res.table = SerialNumberColumns.TABLE_NAME;
                res.idColumn = SerialNumberColumns._ID;
                res.tablesWithJoins = SerialNumberColumns.TABLE_NAME;
                res.orderBy = SerialNumberColumns.DEFAULT_ORDER;
                break;

            case URI_TYPE_TEAM:
            case URI_TYPE_TEAM_ID:
                res.table = TeamColumns.TABLE_NAME;
                res.idColumn = TeamColumns._ID;
                res.tablesWithJoins = TeamColumns.TABLE_NAME;
                if (CompanyColumns.hasColumns(projection) || SerialNumberColumns.hasColumns(projection)) {
                    res.tablesWithJoins += " LEFT OUTER JOIN " + CompanyColumns.TABLE_NAME + " AS " + TeamColumns.PREFIX_COMPANY + " ON " + TeamColumns.TABLE_NAME + "." + TeamColumns.COMPANY_ID + "=" + TeamColumns.PREFIX_COMPANY + "." + CompanyColumns._ID;
                }
                if (SerialNumberColumns.hasColumns(projection)) {
                    res.tablesWithJoins += " LEFT OUTER JOIN " + SerialNumberColumns.TABLE_NAME + " AS " + CompanyColumns.PREFIX_SERIAL_NUMBER + " ON " + TeamColumns.PREFIX_COMPANY + "." + CompanyColumns.SERIAL_NUMBER_ID + "=" + CompanyColumns.PREFIX_SERIAL_NUMBER + "." + SerialNumberColumns._ID;
                }
                if (SerialNumberColumns.hasColumns(projection)) {
                    res.tablesWithJoins += " LEFT OUTER JOIN " + SerialNumberColumns.TABLE_NAME + " AS " + TeamColumns.PREFIX_SERIAL_NUMBER + " ON " + TeamColumns.TABLE_NAME + "." + TeamColumns.SERIAL_NUMBER_ID + "=" + TeamColumns.PREFIX_SERIAL_NUMBER + "." + SerialNumberColumns._ID;
                }
                res.orderBy = TeamColumns.DEFAULT_ORDER;
                break;

            default:
                throw new IllegalArgumentException("The uri '" + uri + "' is not supported by this ContentProvider");
        }

        switch (matchedId) {
            case URI_TYPE_COMPANY_ID:
            case URI_TYPE_MANUAL_ID:
            case URI_TYPE_PERSON_ID:
            case URI_TYPE_PERSON_TEAM_ID:
            case URI_TYPE_PRODUCT_ID:
            case URI_TYPE_SERIAL_NUMBER_ID:
            case URI_TYPE_TEAM_ID:
                id = uri.getLastPathSegment();
        }
        if (id != null) {
            if (selection != null) {
                res.selection = res.table + "." + res.idColumn + "=" + id + " and (" + selection + ")";
            } else {
                res.selection = res.table + "." + res.idColumn + "=" + id;
            }
        } else {
            res.selection = selection;
        }
        return res;
    }
}
