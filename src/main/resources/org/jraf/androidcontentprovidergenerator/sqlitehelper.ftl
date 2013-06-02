<#if header??>
${header}
</#if>
package ${config.providerPackage};

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import ${config.projectPackage}.Config;
import ${config.projectPackage}.Constants;

public class ${config.sqliteHelperClassName} extends SQLiteOpenHelper {
    private static final String TAG = Constants.TAG + ${config.sqliteHelperClassName}.class.getSimpleName();

    public static final String DATABASE_NAME = "${config.databaseName}";
    private static final int DATABASE_VERSION = 1;

    // @formatter:off
    <#list model.entities as entity>
    private static final String SQL_CREATE_TABLE_${entity.nameUpperCase} = "CREATE TABLE IF NOT EXISTS "
            + ${entity.nameCamelCase}Columns.TABLE_NAME + " ( "
            + ${entity.nameCamelCase}Columns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            <#list entity.fields as field>
            + ${entity.nameCamelCase}Columns.${field.nameUpperCase} + " ${field.type}<#if field_has_next>,</#if> "
            </#list>
            <#list entity.constraints as constraint>
            + ", CONSTRAINT ${constraint.nameUpperCase} ${constraint.definitionUpperCase}"
            </#list>
            + " );";

    </#list>
    // @formatter:on

    public ${config.sqliteHelperClassName}(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        if (Config.LOGD) Log.d(TAG, "onCreate");
        <#list model.entities as entity>
        db.execSQL(SQL_CREATE_TABLE_${entity.nameUpperCase});
        </#list>
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (Config.LOGD) Log.d(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion);
    }
}
