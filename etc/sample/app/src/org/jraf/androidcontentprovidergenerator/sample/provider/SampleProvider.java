/*
 * This source is part of the
 *      _____  ___   ____
 *  __ / / _ \/ _ | / __/___  _______ _
 * / // / , _/ __ |/ _/_/ _ \/ __/ _ `/
 * \___/_/|_/_/ |_/_/ (_)___/_/  \_, /
 *                              /___/
 * repository.
 * 
 * Copyright (C) 2012-2014 Benoit 'BoD' Lubek (BoD@JRAF.org)
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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

import org.jraf.androidcontentprovidergenerator.sample.BuildConfig;
import org.jraf.androidcontentprovidergenerator.sample.provider.company.CompanyColumns;
import org.jraf.androidcontentprovidergenerator.sample.provider.person.PersonColumns;
import org.jraf.androidcontentprovidergenerator.sample.provider.team.TeamColumns;

public class SampleProvider extends ContentProvider {
    private static final String TAG = SampleProvider.class.getSimpleName();

    private static final boolean DEBUG = BuildConfig.DEBUG;

    private static final String TYPE_CURSOR_ITEM = "vnd.android.cursor.item/";
    private static final String TYPE_CURSOR_DIR = "vnd.android.cursor.dir/";

    public static final String AUTHORITY = "org.jraf.androidcontentprovidergenerator.sample.provider";
    public static final String CONTENT_URI_BASE = "content://" + AUTHORITY;

    public static final String QUERY_NOTIFY = "QUERY_NOTIFY";
    public static final String QUERY_GROUP_BY = "QUERY_GROUP_BY";

    private static final int URI_TYPE_COMPANY = 0;
    private static final int URI_TYPE_COMPANY_ID = 1;

    private static final int URI_TYPE_PERSON = 2;
    private static final int URI_TYPE_PERSON_ID = 3;

    private static final int URI_TYPE_TEAM = 4;
    private static final int URI_TYPE_TEAM_ID = 5;



    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        URI_MATCHER.addURI(AUTHORITY, CompanyColumns.TABLE_NAME, URI_TYPE_COMPANY);
        URI_MATCHER.addURI(AUTHORITY, CompanyColumns.TABLE_NAME + "/#", URI_TYPE_COMPANY_ID);
        URI_MATCHER.addURI(AUTHORITY, PersonColumns.TABLE_NAME, URI_TYPE_PERSON);
        URI_MATCHER.addURI(AUTHORITY, PersonColumns.TABLE_NAME + "/#", URI_TYPE_PERSON_ID);
        URI_MATCHER.addURI(AUTHORITY, TeamColumns.TABLE_NAME, URI_TYPE_TEAM);
        URI_MATCHER.addURI(AUTHORITY, TeamColumns.TABLE_NAME + "/#", URI_TYPE_TEAM_ID);
    }

    protected SampleSQLiteOpenHelper mSampleSQLiteOpenHelper;

    @Override
    public boolean onCreate() {
        if (DEBUG) {
            // Enable logging of SQL statements as they are executed.
            try {
                Class<?> sqliteDebugClass = Class.forName("android.database.sqlite.SQLiteDebug");
                Field field = sqliteDebugClass.getDeclaredField("DEBUG_SQL_STATEMENTS");
                field.setAccessible(true);
                field.set(null, true);

                // Uncomment the following block if you also want logging of execution time (more verbose)
                // field = sqliteDebugClass.getDeclaredField("DEBUG_SQL_TIME");
                // field.setAccessible(true);
                // field.set(null, true);
            } catch (Throwable t) {
                if (DEBUG) Log.w(TAG, "Could not enable SQLiteDebug logging", t);
            }
        }

        mSampleSQLiteOpenHelper = SampleSQLiteOpenHelper.getInstance(getContext());
        return true;
    }

    @Override
    public String getType(Uri uri) {
        int match = URI_MATCHER.match(uri);
        switch (match) {
            case URI_TYPE_COMPANY:
                return TYPE_CURSOR_DIR + CompanyColumns.TABLE_NAME;
            case URI_TYPE_COMPANY_ID:
                return TYPE_CURSOR_ITEM + CompanyColumns.TABLE_NAME;

            case URI_TYPE_PERSON:
                return TYPE_CURSOR_DIR + PersonColumns.TABLE_NAME;
            case URI_TYPE_PERSON_ID:
                return TYPE_CURSOR_ITEM + PersonColumns.TABLE_NAME;

            case URI_TYPE_TEAM:
                return TYPE_CURSOR_DIR + TeamColumns.TABLE_NAME;
            case URI_TYPE_TEAM_ID:
                return TYPE_CURSOR_ITEM + TeamColumns.TABLE_NAME;

        }
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        if (DEBUG) Log.d(TAG, "insert uri=" + uri + " values=" + values);
        String table = uri.getLastPathSegment();
        long rowId = mSampleSQLiteOpenHelper.getWritableDatabase().insertOrThrow(table, null, values);
        if (rowId == -1) return null;
        String notify;
        if (rowId != -1 && ((notify = uri.getQueryParameter(QUERY_NOTIFY)) == null || "true".equals(notify))) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return uri.buildUpon().appendEncodedPath(String.valueOf(rowId)).build();
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        if (DEBUG) Log.d(TAG, "bulkInsert uri=" + uri + " values.length=" + values.length);
        String table = uri.getLastPathSegment();
        SQLiteDatabase db = mSampleSQLiteOpenHelper.getWritableDatabase();
        int res = 0;
        db.beginTransaction();
        try {
            for (ContentValues v : values) {
                long id = db.insert(table, null, v);
                db.yieldIfContendedSafely();
                if (id != -1) {
                    res++;
                }
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        String notify;
        if (res != 0 && ((notify = uri.getQueryParameter(QUERY_NOTIFY)) == null || "true".equals(notify))) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return res;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if (DEBUG) Log.d(TAG, "update uri=" + uri + " values=" + values + " selection=" + selection + " selectionArgs=" + Arrays.toString(selectionArgs));
        QueryParams queryParams = getQueryParams(uri, selection, null);
        int res = mSampleSQLiteOpenHelper.getWritableDatabase().update(queryParams.table, values, queryParams.selection, selectionArgs);
        String notify;
        if (res != 0 && ((notify = uri.getQueryParameter(QUERY_NOTIFY)) == null || "true".equals(notify))) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return res;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        if (DEBUG) Log.d(TAG, "delete uri=" + uri + " selection=" + selection + " selectionArgs=" + Arrays.toString(selectionArgs));
        QueryParams queryParams = getQueryParams(uri, selection, null);
        int res = mSampleSQLiteOpenHelper.getWritableDatabase().delete(queryParams.table, queryParams.selection, selectionArgs);
        String notify;
        if (res != 0 && ((notify = uri.getQueryParameter(QUERY_NOTIFY)) == null || "true".equals(notify))) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return res;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        String groupBy = uri.getQueryParameter(QUERY_GROUP_BY);
        if (DEBUG)
            Log.d(TAG, "query uri=" + uri + " selection=" + selection + " selectionArgs=" + Arrays.toString(selectionArgs) + " sortOrder=" + sortOrder
                    + " groupBy=" + groupBy);
        QueryParams queryParams = getQueryParams(uri, selection, projection);
        ensureIdIsFullyQualified(projection, queryParams.table);
        Cursor res = mSampleSQLiteOpenHelper.getReadableDatabase().query(queryParams.tablesWithJoins, projection, queryParams.selection, selectionArgs, groupBy,
                null, sortOrder == null ? queryParams.orderBy : sortOrder);
        res.setNotificationUri(getContext().getContentResolver(), uri);
        return res;
    }

    private void ensureIdIsFullyQualified(String[] projection, String tableName) {
        if (projection != null) {
            for (int i = 0; i < projection.length; ++i) {
                if (projection[i].equals(BaseColumns._ID)) {
                    projection[i] = tableName + "." + BaseColumns._ID + " AS " + BaseColumns._ID;
                }
            }
        }
    }

    @Override
    public ContentProviderResult[] applyBatch(ArrayList<ContentProviderOperation> operations) throws OperationApplicationException {
        HashSet<Uri> urisToNotify = new HashSet<Uri>(operations.size());
        for (ContentProviderOperation operation : operations) {
            urisToNotify.add(operation.getUri());
        }
        SQLiteDatabase db = mSampleSQLiteOpenHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            int numOperations = operations.size();
            ContentProviderResult[] results = new ContentProviderResult[numOperations];
            int i = 0;
            for (ContentProviderOperation operation : operations) {
                results[i] = operation.apply(this, results, i);
                if (operation.isYieldAllowed()) {
                    db.yieldIfContendedSafely();
                }
                i++;
            }
            db.setTransactionSuccessful();
            for (Uri uri : urisToNotify) {
                getContext().getContentResolver().notifyChange(uri, null);
            }
            return results;
        } finally {
            db.endTransaction();
        }
    }

    private static class QueryParams {
        public String table;
        public String tablesWithJoins;
        public String selection;
        public String orderBy;
    }

    private QueryParams getQueryParams(Uri uri, String selection, String[] projection) {
        QueryParams res = new QueryParams();
        String id = null;
        int matchedId = URI_MATCHER.match(uri);
        switch (matchedId) {
            case URI_TYPE_COMPANY:
            case URI_TYPE_COMPANY_ID:
                res.table = CompanyColumns.TABLE_NAME;
                res.tablesWithJoins = CompanyColumns.TABLE_NAME;
                res.orderBy = CompanyColumns.DEFAULT_ORDER;
                break;

            case URI_TYPE_PERSON:
            case URI_TYPE_PERSON_ID:
                res.table = PersonColumns.TABLE_NAME;
                res.tablesWithJoins = PersonColumns.TABLE_NAME;
                if (TeamColumns.hasColumns(projection) || CompanyColumns.hasColumns(projection)) {
                    res.tablesWithJoins += " LEFT OUTER JOIN " + TeamColumns.TABLE_NAME + " ON " + PersonColumns.TABLE_NAME + "." + PersonColumns.MAIN_TEAM_ID + "=" + TeamColumns.TABLE_NAME + "." + TeamColumns._ID;
                }
                if (CompanyColumns.hasColumns(projection)) {
                    res.tablesWithJoins += " LEFT OUTER JOIN " + CompanyColumns.TABLE_NAME + " ON " + TeamColumns.TABLE_NAME + "." + TeamColumns.COMPANY_ID + "=" + CompanyColumns.TABLE_NAME + "." + CompanyColumns._ID;
                }
                res.orderBy = PersonColumns.DEFAULT_ORDER;
                break;

            case URI_TYPE_TEAM:
            case URI_TYPE_TEAM_ID:
                res.table = TeamColumns.TABLE_NAME;
                res.tablesWithJoins = TeamColumns.TABLE_NAME;
                if (CompanyColumns.hasColumns(projection)) {
                    res.tablesWithJoins += " LEFT OUTER JOIN " + CompanyColumns.TABLE_NAME + " ON " + TeamColumns.TABLE_NAME + "." + TeamColumns.COMPANY_ID + "=" + CompanyColumns.TABLE_NAME + "." + CompanyColumns._ID;
                }
                res.orderBy = TeamColumns.DEFAULT_ORDER;
                break;

            default:
                throw new IllegalArgumentException("The uri '" + uri + "' is not supported by this ContentProvider");
        }

        switch (matchedId) {
            case URI_TYPE_COMPANY_ID:
            case URI_TYPE_PERSON_ID:
            case URI_TYPE_TEAM_ID:
                id = uri.getLastPathSegment();
        }
        if (id != null) {
            if (selection != null) {
                res.selection = res.table + "." + BaseColumns._ID + "=" + id + " and (" + selection + ")";
            } else {
                res.selection = res.table + "." + BaseColumns._ID + "=" + id;
            }
        } else {
            res.selection = selection;
        }
        return res;
    }

    public static Uri notify(Uri uri, boolean notify) {
        return uri.buildUpon().appendQueryParameter(QUERY_NOTIFY, String.valueOf(notify)).build();
    }

    public static Uri groupBy(Uri uri, String groupBy) {
        return uri.buildUpon().appendQueryParameter(QUERY_GROUP_BY, groupBy).build();
    }
}
