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

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.jraf.androidcontentprovidergenerator.sample.provider.base.BaseSQLiteOpenHelperCallbacks;

/**
 * Allows the app to be called when opening/creating/upgrading the db.
 */
public class SampleSQLiteOpenHelperCallbacks extends BaseSQLiteOpenHelperCallbacks {
    @Override
    public void onOpen(Context context, SQLiteDatabase db) {
        super.onOpen(context, db);
    }

    @Override
    public void onPreCreate(Context context, SQLiteDatabase db) {
        super.onPreCreate(context, db);
    }

    @Override
    public void onPostCreate(Context context, SQLiteDatabase db) {
        super.onPostCreate(context, db);
    }

    @Override
    public void onUpgrade(Context context, SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onUpgrade(context, db, oldVersion, newVersion);
    }
}
