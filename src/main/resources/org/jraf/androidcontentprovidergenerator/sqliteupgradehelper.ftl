<#if header??>
${header}
</#if>
package ${config.providerJavaPackage};

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import ${config.projectPackageId}.BuildConfig;

public class ${config.sqliteUpgradeHelperClassName} {
    private static final String TAG = ${config.sqliteUpgradeHelperClassName}.class.getSimpleName();

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (BuildConfig.DEBUG) Log.d(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion);
        // Insert your upgrading code here.
        // This file will not be overridden.
    }
}
