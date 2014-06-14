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

import android.annotation.TargetApi;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.DefaultDatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import org.jraf.androidcontentprovidergenerator.sample.BuildConfig;
import org.jraf.androidcontentprovidergenerator.sample.provider.company.CompanyColumns;
import org.jraf.androidcontentprovidergenerator.sample.provider.person.PersonColumns;
import org.jraf.androidcontentprovidergenerator.sample.provider.team.TeamColumns;

public class SampleSQLiteOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = SampleSQLiteOpenHelper.class.getSimpleName();

    public static final String DATABASE_FILE_NAME = "sample.db";
    private static final int DATABASE_VERSION = 1;
    private final Context mContext;
    private final SampleSQLiteOpenHelperCallbacks mOpenHelperCallbacks;

    // @formatter:off
    private static final String SQL_CREATE_TABLE_COMPANY = "CREATE TABLE IF NOT EXISTS "
            + CompanyColumns.TABLE_NAME + " ( "
            + CompanyColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CompanyColumns.COMPANY_NAME + " TEXT NOT NULL, "
            + CompanyColumns.ADDRESS + " TEXT "
            + " );";

    private static final String SQL_CREATE_INDEX_COMPANY_COMPANY_NAME = "CREATE INDEX IDX_COMPANY_COMPANY_NAME "
            + " ON " + CompanyColumns.TABLE_NAME + " ( " + CompanyColumns.COMPANY_NAME + " );";

    private static final String SQL_CREATE_TABLE_PERSON = "CREATE TABLE IF NOT EXISTS "
            + PersonColumns.TABLE_NAME + " ( "
            + PersonColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + PersonColumns.MAIN_TEAM_ID + " INTEGER NOT NULL, "
            + PersonColumns.FIRST_NAME + " TEXT NOT NULL, "
            + PersonColumns.LAST_NAME + " TEXT NOT NULL, "
            + PersonColumns.AGE + " INTEGER NOT NULL, "
            + PersonColumns.BIRTH_DATE + " INTEGER, "
            + PersonColumns.HAS_BLUE_EYES + " INTEGER NOT NULL DEFAULT '0', "
            + PersonColumns.HEIGHT + " REAL, "
            + PersonColumns.GENDER + " INTEGER NOT NULL "
            + ", CONSTRAINT FK_MAIN_TEAM_ID FOREIGN KEY (MAIN_TEAM_ID) REFERENCES TEAM (_ID) ON DELETE CASCADE"
            + ", CONSTRAINT UNIQUE_NAME UNIQUE (FIRST_NAME, LAST_NAME) ON CONFLICT REPLACE"
            + " );";

    private static final String SQL_CREATE_INDEX_PERSON_LAST_NAME = "CREATE INDEX IDX_PERSON_LAST_NAME "
            + " ON " + PersonColumns.TABLE_NAME + " ( " + PersonColumns.LAST_NAME + " );";

    private static final String SQL_CREATE_TABLE_TEAM = "CREATE TABLE IF NOT EXISTS "
            + TeamColumns.TABLE_NAME + " ( "
            + TeamColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TeamColumns.COMPANY_ID + " INTEGER NOT NULL, "
            + TeamColumns.TEAM_NAME + " TEXT NOT NULL "
            + ", CONSTRAINT FK_COMPANY_ID FOREIGN KEY (COMPANY_ID) REFERENCES COMPANY (_ID) ON DELETE CASCADE"
            + ", CONSTRAINT UNIQUE_NAME UNIQUE (TEAM_NAME) ON CONFLICT REPLACE"
            + " );";

    // @formatter:on

    public static SampleSQLiteOpenHelper newInstance(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            return newInstancePreHoneycomb(context);
        }
        return newInstancePostHoneycomb(context);
    }


    /*
     * Pre Honeycomb.
     */

    private static SampleSQLiteOpenHelper newInstancePreHoneycomb(Context context) {
        return new SampleSQLiteOpenHelper(context, DATABASE_FILE_NAME, null, DATABASE_VERSION);
    }

    private SampleSQLiteOpenHelper(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
        mOpenHelperCallbacks = new SampleSQLiteOpenHelperCallbacks();
    }


    /*
     * Post Honeycomb.
     */

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static SampleSQLiteOpenHelper newInstancePostHoneycomb(Context context) {
        return new SampleSQLiteOpenHelper(context, DATABASE_FILE_NAME, null, DATABASE_VERSION, new DefaultDatabaseErrorHandler());
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private SampleSQLiteOpenHelper(Context context, String name, CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
        mContext = context;
        mOpenHelperCallbacks = new SampleSQLiteOpenHelperCallbacks();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        if (BuildConfig.DEBUG) Log.d(TAG, "onCreate");
        mOpenHelperCallbacks.onPreCreate(mContext, db);
        db.execSQL(SQL_CREATE_TABLE_COMPANY);
        db.execSQL(SQL_CREATE_INDEX_COMPANY_COMPANY_NAME);
        db.execSQL(SQL_CREATE_TABLE_PERSON);
        db.execSQL(SQL_CREATE_INDEX_PERSON_LAST_NAME);
        db.execSQL(SQL_CREATE_TABLE_TEAM);
        mOpenHelperCallbacks.onPostCreate(mContext, db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
        mOpenHelperCallbacks.onOpen(mContext, db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        mOpenHelperCallbacks.onUpgrade(mContext, db, oldVersion, newVersion);
    }
}
