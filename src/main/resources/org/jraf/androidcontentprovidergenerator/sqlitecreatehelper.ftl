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

    public void onCreate(final Context context, final SQLiteDatabase db) {
        if (BuildConfig.DEBUG) Log.d(TAG, "Creating database");
        // Insert your db creation code here.
        // This file will not be overridden.
    }

}
