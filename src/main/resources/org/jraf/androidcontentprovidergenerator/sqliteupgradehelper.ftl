<#if header??>
${header}
</#if>
package ${config.providerJavaPackage};

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import android.util.Log;

import ${config.projectPackageId}.BuildConfig;

public class ${config.sqliteUpgradeHelperClassName} {
    private static final String TAG = ${config.sqliteUpgradeHelperClassName}.class.getSimpleName();

    public void onUpgrade(final Context context, final SQLiteDatabase db, final int oldVersion, final int newVersion) {
        if (BuildConfig.DEBUG) Log.d(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion);
        // Insert your upgrading code here.
        // This file will not be overridden.
    }
}
