<#if header??>
${header}
</#if>
package ${config.providerJavaPackage};

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import android.util.Log;

import ${config.projectPackageId}.BuildConfig;

public class ${config.sqliteCreateHelperClassName} {
    private static final String TAG = ${config.sqliteCreateHelperClassName}.class.getSimpleName();

    public void onPreCreate(final Context context, final SQLiteDatabase db) {
        if (BuildConfig.DEBUG) Log.d(TAG, "onPreCreate");
        // Insert your db creation code here. This is called before your tables are created.
        // This file will not be overridden.
    }

    public void onPostCreate(final Context context, final SQLiteDatabase db) {
        if (BuildConfig.DEBUG) Log.d(TAG, "onPostCreate");
        // Insert your db creation code here. This is called after your tables are created.
        // This file will not be overridden.
    }

}
