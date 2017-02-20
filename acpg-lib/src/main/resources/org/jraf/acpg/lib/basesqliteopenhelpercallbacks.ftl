<#if header??>
${header}
</#if>
package ${config.providerJavaPackage}.base;

// @formatter:off
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import android.util.Log;

import ${config.packageName}.BuildConfig;

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
        if (BuildConfig.${config.debugLogsFieldName}) Log.d(TAG, "onOpen");
    }

    /**
     * Called before the database tables are created.
     * @see android.database.sqlite.SQLiteOpenHelper#onCreate(SQLiteDatabase) onCreate
     */
    public void onPreCreate(Context context, SQLiteDatabase db) {
        if (BuildConfig.${config.debugLogsFieldName}) Log.d(TAG, "onPreCreate");
    }

    /**
     * Called after the database tables have been created.
     * @see android.database.sqlite.SQLiteOpenHelper#onCreate(SQLiteDatabase) onCreate
     */
    public void onPostCreate(Context context, SQLiteDatabase db) {
        if (BuildConfig.${config.debugLogsFieldName}) Log.d(TAG, "onPostCreate");
    }

    /**
     * Called when the database needs to be upgraded.
     * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(Context, SQLiteDatabase, int, int) onUpgrade
     */
    public void onUpgrade(final Context context, final SQLiteDatabase db, final int oldVersion, final int newVersion) {
        if (BuildConfig.${config.debugLogsFieldName}) Log.d(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion);
    }
}
