<#if header??>
${header}
</#if>
package ${config.providerJavaPackage};

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import android.util.Log;

import ${config.projectPackageId}.BuildConfig;

/**
 * Implement your custom database creation or upgrade code here.
 *
 * This file will not be overwritten if you re-run the content provider generator.
 */
public class ${config.sqliteOpenHelperCallbacksClassName} {
    private static final String TAG = ${config.sqliteOpenHelperCallbacksClassName}.class.getSimpleName();

    public void onOpen(final Context context, final SQLiteDatabase db) {
        if (BuildConfig.DEBUG) Log.d(TAG, "onOpen");
        // Insert your db open code here.
    }

    public void onPreCreate(final Context context, final SQLiteDatabase db) {
        if (BuildConfig.DEBUG) Log.d(TAG, "onPreCreate");
        // Insert your db creation code here. This is called before your tables are created.
    }

    public void onPostCreate(final Context context, final SQLiteDatabase db) {
        if (BuildConfig.DEBUG) Log.d(TAG, "onPostCreate");
        // Insert your db creation code here. This is called after your tables are created.
    }

    public void onUpgrade(final Context context, final SQLiteDatabase db, final int oldVersion, final int newVersion) {
        if (BuildConfig.DEBUG) Log.d(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion);
        // Insert your upgrading code here.
        <#if config.optString("sqliteUpgradeHelperClassName")?has_content>
        new ${config.sqliteUpgradeHelperClassName}().onUpgrade(db, oldVersion, newVersion);
        </#if>
    }
}
