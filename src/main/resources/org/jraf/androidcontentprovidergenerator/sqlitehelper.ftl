<#if header??>
${header}
</#if>
package ${config.providerJavaPackage};

import android.annotation.TargetApi;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.DefaultDatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import ${config.projectPackageId}.BuildConfig;
<#list model.entities as entity>
import ${config.providerJavaPackage}.${entity.nameLowerCase}.${entity.nameCamelCase}Columns;
</#list>

public class ${config.sqliteHelperClassName} extends SQLiteOpenHelper {
    private static final String TAG = ${config.sqliteHelperClassName}.class.getSimpleName();

    public static final String DATABASE_FILE_NAME = "${config.databaseFileName}";
    private static final int DATABASE_VERSION = 1;

    // @formatter:off
    <#list model.entities as entity>
    private static final String SQL_CREATE_TABLE_${entity.nameUpperCase} = "CREATE TABLE IF NOT EXISTS "
            + ${entity.nameCamelCase}Columns.TABLE_NAME + " ( "
            + ${entity.nameCamelCase}Columns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            <#list entity.fields as field>
                <#if field.isNullable>
                    <#if field.hasDefaultValue>
            + ${entity.nameCamelCase}Columns.${field.nameUpperCase} + " ${field.type.sqlType} DEFAULT '${field.defaultValue}'<#if field_has_next>,</#if> "
                    <#else>
            + ${entity.nameCamelCase}Columns.${field.nameUpperCase} + " ${field.type.sqlType}<#if field_has_next>,</#if> "
                    </#if>
                <#else>
            + ${entity.nameCamelCase}Columns.${field.nameUpperCase} + " ${field.type.sqlType} NOT NULL<#if field_has_next>,</#if> "
                </#if>
            </#list>
            <#list entity.constraints as constraint>
            + ", CONSTRAINT ${constraint.nameUpperCase} ${constraint.definitionUpperCase}"
            </#list>
            + " );";

    <#list entity.fields as field>
    <#if field.isIndex>
    private static final String SQL_CREATE_INDEX_${entity.nameUpperCase}_${field.nameUpperCase} = "CREATE INDEX IDX_${entity.nameUpperCase}_${field.nameUpperCase} "
            + " ON " + ${entity.nameCamelCase}Columns.TABLE_NAME + " ( " + ${entity.nameCamelCase}Columns.${field.nameUpperCase} + " );";
    </#if>
    </#list>
    </#list>
    // @formatter:on

    public static ${config.sqliteHelperClassName} newInstance(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            return newInstancePreHoneycomb(context);
        }
        return newInstancePostHoneycomb(context);
    }


    /*
     * Pre Honeycomb.
     */

    private static ${config.sqliteHelperClassName} newInstancePreHoneycomb(Context context) {
        return new ${config.sqliteHelperClassName}(context, DATABASE_FILE_NAME, null, DATABASE_VERSION);
    }

    private ${config.sqliteHelperClassName}(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    /*
     * Post Honeycomb.
     */

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static ${config.sqliteHelperClassName} newInstancePostHoneycomb(Context context) {
        return new ${config.sqliteHelperClassName}(context, DATABASE_FILE_NAME, null, DATABASE_VERSION, new DefaultDatabaseErrorHandler());
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private ${config.sqliteHelperClassName}(Context context, String name, CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        if (BuildConfig.DEBUG) Log.d(TAG, "onCreate");
        <#list model.entities as entity>
        db.execSQL(SQL_CREATE_TABLE_${entity.nameUpperCase});
        <#list entity.fields as field>
        <#if field.isIndex>
        db.execSQL(SQL_CREATE_INDEX_${entity.nameUpperCase}_${field.nameUpperCase});
        </#if>
        </#list>
        </#list>
    }

    <#if config.enableForeignKeys >
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }
    </#if>

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (BuildConfig.DEBUG) Log.d(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion);
    }
}
