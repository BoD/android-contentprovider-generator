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
package org.jraf.androidcontentprovidergenerator.sample.provider.base;

// @formatter:off
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import android.util.Log;

import org.jraf.androidcontentprovidergenerator.sample.BuildConfig;

/**
 * Override this class to implement your custom database opening, creation or upgrade.
 */
public class BaseSQLiteOpenHelperCallbacks {
    private static final String TAG = BaseSQLiteOpenHelperCallbacks.class.getSimpleName();

    /**
     * Called when the database has been opened.
     * @see android.database.sqlite.SQLiteOpenHelper#onOpen(SQLiteDatabase) onOpen
     */
    public void onOpen(Context context, SQLiteDatabase db) {
        if (BuildConfig.LOG_DEBUG_PROVIDER) Log.d(TAG, "onOpen");
    }

    /**
     * Called before the database tables are created.
     * @see android.database.sqlite.SQLiteOpenHelper#onCreate(SQLiteDatabase) onCreate
     */
    public void onPreCreate(Context context, SQLiteDatabase db) {
        if (BuildConfig.LOG_DEBUG_PROVIDER) Log.d(TAG, "onPreCreate");
    }

    /**
     * Called after the database tables have been created.
     * @see android.database.sqlite.SQLiteOpenHelper#onCreate(SQLiteDatabase) onCreate
     */
    public void onPostCreate(Context context, SQLiteDatabase db) {
        if (BuildConfig.LOG_DEBUG_PROVIDER) Log.d(TAG, "onPostCreate");
    }

    /**
     * Called when the database needs to be upgraded.
     * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(Context, SQLiteDatabase, int, int) onUpgrade
     */
    public void onUpgrade(final Context context, final SQLiteDatabase db, final int oldVersion, final int newVersion) {
        if (BuildConfig.LOG_DEBUG_PROVIDER) Log.d(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion);
    }
}
