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
package org.jraf.androidcontentprovidergenerator.sample.provider;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.DefaultDatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import org.jraf.androidcontentprovidergenerator.sample.BuildConfig;
import org.jraf.androidcontentprovidergenerator.sample.provider.company.CompanyColumns;
import org.jraf.androidcontentprovidergenerator.sample.provider.manual.ManualColumns;
import org.jraf.androidcontentprovidergenerator.sample.provider.person.PersonColumns;
import org.jraf.androidcontentprovidergenerator.sample.provider.personteam.PersonTeamColumns;
import org.jraf.androidcontentprovidergenerator.sample.provider.product.ProductColumns;
import org.jraf.androidcontentprovidergenerator.sample.provider.serialnumber.SerialNumberColumns;
import org.jraf.androidcontentprovidergenerator.sample.provider.team.TeamColumns;

public class SampleSQLiteOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = SampleSQLiteOpenHelper.class.getSimpleName();

    public static final String DATABASE_FILE_NAME = "sample.db";
    private static final int DATABASE_VERSION = 1;
    private static SampleSQLiteOpenHelper sInstance;
    private final Context mContext;
    private final SampleSQLiteOpenHelperCallbacks mOpenHelperCallbacks;

    // @formatter:off
    public static final String SQL_CREATE_TABLE_COMPANY = "CREATE TABLE IF NOT EXISTS "
            + CompanyColumns.TABLE_NAME + " ( "
            + CompanyColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CompanyColumns.NAME + " TEXT NOT NULL, "
            + CompanyColumns.ADDRESS + " TEXT, "
            + CompanyColumns.SERIAL_NUMBER_ID + " INTEGER NOT NULL "
            + ", CONSTRAINT fk_serial_number_id FOREIGN KEY (" + CompanyColumns.SERIAL_NUMBER_ID + ") REFERENCES serial_number (_id) ON DELETE CASCADE"
            + " );";

    public static final String SQL_CREATE_INDEX_COMPANY_NAME = "CREATE INDEX IDX_COMPANY_NAME "
            + " ON " + CompanyColumns.TABLE_NAME + " ( " + CompanyColumns.NAME + " );";

    public static final String SQL_CREATE_TABLE_MANUAL = "CREATE TABLE IF NOT EXISTS "
            + ManualColumns.TABLE_NAME + " ( "
            + ManualColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ManualColumns.TITLE + " TEXT NOT NULL, "
            + ManualColumns.ISBN + " TEXT NOT NULL "
            + " );";

    public static final String SQL_CREATE_TABLE_PERSON = "CREATE TABLE IF NOT EXISTS "
            + PersonColumns.TABLE_NAME + " ( "
            + PersonColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + PersonColumns.FIRST_NAME + " TEXT NOT NULL, "
            + PersonColumns.LAST_NAME + " TEXT NOT NULL, "
            + PersonColumns.AGE + " INTEGER NOT NULL, "
            + PersonColumns.BIRTH_DATE + " INTEGER, "
            + PersonColumns.HAS_BLUE_EYES + " INTEGER NOT NULL DEFAULT 0, "
            + PersonColumns.HEIGHT + " REAL, "
            + PersonColumns.GENDER + " INTEGER NOT NULL, "
            + PersonColumns.COUNTRY_CODE + " TEXT NOT NULL "
            + ", CONSTRAINT unique_name UNIQUE (first_name, last_name) ON CONFLICT REPLACE"
            + " );";

    public static final String SQL_CREATE_INDEX_PERSON_LAST_NAME = "CREATE INDEX IDX_PERSON_LAST_NAME "
            + " ON " + PersonColumns.TABLE_NAME + " ( " + PersonColumns.LAST_NAME + " );";

    public static final String SQL_CREATE_TABLE_PERSON_TEAM = "CREATE TABLE IF NOT EXISTS "
            + PersonTeamColumns.TABLE_NAME + " ( "
            + PersonTeamColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + PersonTeamColumns.PERSON_ID + " INTEGER NOT NULL, "
            + PersonTeamColumns.TEAM_ID + " INTEGER NOT NULL "
            + ", CONSTRAINT fk_person_id FOREIGN KEY (" + PersonTeamColumns.PERSON_ID + ") REFERENCES person (_id) ON DELETE RESTRICT"
            + ", CONSTRAINT fk_team_id FOREIGN KEY (" + PersonTeamColumns.TEAM_ID + ") REFERENCES team (_id) ON DELETE RESTRICT"
            + ", CONSTRAINT unique_person_team UNIQUE (person_id, team_id) ON CONFLICT REPLACE"
            + " );";

    public static final String SQL_CREATE_TABLE_PRODUCT = "CREATE TABLE IF NOT EXISTS "
            + ProductColumns.TABLE_NAME + " ( "
            + ProductColumns._ID + " INTEGER PRIMARY KEY, "
            + ProductColumns.NAME + " TEXT NOT NULL, "
            + ProductColumns.MANUAL_ID + " INTEGER "
            + ", CONSTRAINT fk_manual_id FOREIGN KEY (" + ProductColumns.MANUAL_ID + ") REFERENCES manual (_id) ON DELETE SET NULL"
            + " );";

    public static final String SQL_CREATE_INDEX_PRODUCT_PRODUCT_ID = "CREATE INDEX IDX_PRODUCT_PRODUCT_ID "
            + " ON " + ProductColumns.TABLE_NAME + " ( " + ProductColumns.PRODUCT_ID + " );";

    public static final String SQL_CREATE_TABLE_SERIAL_NUMBER = "CREATE TABLE IF NOT EXISTS "
            + SerialNumberColumns.TABLE_NAME + " ( "
            + SerialNumberColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + SerialNumberColumns.PART0 + " TEXT NOT NULL, "
            + SerialNumberColumns.PART1 + " TEXT NOT NULL "
            + " );";

    public static final String SQL_CREATE_TABLE_TEAM = "CREATE TABLE IF NOT EXISTS "
            + TeamColumns.TABLE_NAME + " ( "
            + TeamColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TeamColumns.COMPANY_ID + " INTEGER NOT NULL, "
            + TeamColumns.NAME + " TEXT NOT NULL, "
            + TeamColumns.COUNTRY_CODE + " TEXT NOT NULL, "
            + TeamColumns.SERIAL_NUMBER_ID + " INTEGER NOT NULL "
            + ", CONSTRAINT fk_company_id FOREIGN KEY (" + TeamColumns.COMPANY_ID + ") REFERENCES company (_id) ON DELETE CASCADE"
            + ", CONSTRAINT fk_serial_number_id FOREIGN KEY (" + TeamColumns.SERIAL_NUMBER_ID + ") REFERENCES serial_number (_id) ON DELETE CASCADE"
            + ", CONSTRAINT unique_name UNIQUE (team__name) ON CONFLICT REPLACE"
            + " );";

    // @formatter:on

    public static SampleSQLiteOpenHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = newInstance(context.getApplicationContext());
        }
        return sInstance;
    }

    private static SampleSQLiteOpenHelper newInstance(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            return newInstancePreHoneycomb(context);
        }
        return newInstancePostHoneycomb(context);
    }


    /*
     * Pre Honeycomb.
     */
    private static SampleSQLiteOpenHelper newInstancePreHoneycomb(Context context) {
        return new SampleSQLiteOpenHelper(context);
    }

    private SampleSQLiteOpenHelper(Context context) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION);
        mContext = context;
        mOpenHelperCallbacks = new SampleSQLiteOpenHelperCallbacks();
    }


    /*
     * Post Honeycomb.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static SampleSQLiteOpenHelper newInstancePostHoneycomb(Context context) {
        return new SampleSQLiteOpenHelper(context, new DefaultDatabaseErrorHandler());
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private SampleSQLiteOpenHelper(Context context, DatabaseErrorHandler errorHandler) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION, errorHandler);
        mContext = context;
        mOpenHelperCallbacks = new SampleSQLiteOpenHelperCallbacks();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        if (BuildConfig.DEBUG) Log.d(TAG, "onCreate");
        mOpenHelperCallbacks.onPreCreate(mContext, db);
        db.execSQL(SQL_CREATE_TABLE_COMPANY);
        db.execSQL(SQL_CREATE_INDEX_COMPANY_NAME);
        db.execSQL(SQL_CREATE_TABLE_MANUAL);
        db.execSQL(SQL_CREATE_TABLE_PERSON);
        db.execSQL(SQL_CREATE_INDEX_PERSON_LAST_NAME);
        db.execSQL(SQL_CREATE_TABLE_PERSON_TEAM);
        db.execSQL(SQL_CREATE_TABLE_PRODUCT);
        db.execSQL(SQL_CREATE_INDEX_PRODUCT_PRODUCT_ID);
        db.execSQL(SQL_CREATE_TABLE_SERIAL_NUMBER);
        db.execSQL(SQL_CREATE_TABLE_TEAM);
        mOpenHelperCallbacks.onPostCreate(mContext, db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            setForeignKeyConstraintsEnabled(db);
        }
        mOpenHelperCallbacks.onOpen(mContext, db);
    }

    private void setForeignKeyConstraintsEnabled(SQLiteDatabase db) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            setForeignKeyConstraintsEnabledPreJellyBean(db);
        } else {
            setForeignKeyConstraintsEnabledPostJellyBean(db);
        }
    }

    private void setForeignKeyConstraintsEnabledPreJellyBean(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys=ON;");
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setForeignKeyConstraintsEnabledPostJellyBean(SQLiteDatabase db) {
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        mOpenHelperCallbacks.onUpgrade(mContext, db, oldVersion, newVersion);
    }
}
