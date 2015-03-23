<#if header??>
${header}
</#if>
package ${config.providerJavaPackage};

import android.content.Context;
<#if config.useEncryptedDatabase>
import net.sqlcipher.database.SQLiteDatabase;
<#else>
import android.database.sqlite.SQLiteDatabase;
</#if>
import android.util.Log;

import ${config.projectPackageId}.BuildConfig;

/**
 * Implement your custom database creation or upgrade code here.
 *
 * This file will not be overwritten if you re-run the content provider generator.
 */
public class ${config.providerCallbacksClassName} {
    private static final String TAG = ${config.providerCallbacksClassName}.class.getSimpleName();

    public String onPasswordRequested() {
        if (BuildConfig.DEBUG) Log.d(TAG, "onPasswordRequested");
        //TODO Insert your own password provider 
        return "password";

    }
}
