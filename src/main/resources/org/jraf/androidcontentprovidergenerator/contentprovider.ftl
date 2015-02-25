<#if header??>
${header}
</#if>
package ${config.providerJavaPackage};

import java.util.Arrays;

import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
<#if config.useAnnotations>
import android.support.annotation.NonNull;
</#if>
import android.util.Log;

import ${config.projectPackageId}.BuildConfig;
import ${config.providerJavaPackage}.base.BaseContentProvider;
<#list model.entities as entity>
import ${config.providerJavaPackage}.${entity.packageName}.${entity.nameCamelCase}Columns;
</#list>

public class ${config.providerClassName} extends BaseContentProvider {
    private static final String TAG = ${config.providerClassName}.class.getSimpleName();

    private static final boolean DEBUG = BuildConfig.DEBUG;

    private static final String TYPE_CURSOR_ITEM = "vnd.android.cursor.item/";
    private static final String TYPE_CURSOR_DIR = "vnd.android.cursor.dir/";

	<#assign authority = "\"" + config.authority?replace(r"${applicationId}", "\" + BuildConfig.APPLICATION_ID + \"") + "\"">
	<#assign authority = authority?replace(r'^"* \+ (.*?)', r"$1", "r")?replace(r'(.*?) \+ "*$', r"$1", "r")>
    public static final String AUTHORITY = ${authority};
    public static final String CONTENT_URI_BASE = "content://" + AUTHORITY;

	<#assign i=0>
    <#list model.entities as entity>
    private static final int URI_TYPE_${entity.nameUpperCase} = ${i};
    <#assign i = i + 1>
    private static final int URI_TYPE_${entity.nameUpperCase}_ID = ${i};
    <#assign i = i + 1>

    </#list>


    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        <#list model.entities as entity>
        URI_MATCHER.addURI(AUTHORITY, ${entity.nameCamelCase}Columns.TABLE_NAME, URI_TYPE_${entity.nameUpperCase});
        URI_MATCHER.addURI(AUTHORITY, ${entity.nameCamelCase}Columns.TABLE_NAME + "/#", URI_TYPE_${entity.nameUpperCase}_ID);
        </#list>
    }

    @Override
    protected SQLiteOpenHelper createSqLiteOpenHelper() {
        return ${config.sqliteOpenHelperClassName}.getInstance(getContext());
    }

    @Override
    protected boolean hasDebug() {
        return DEBUG;
    }

    @Override
    public String getType(Uri uri) {
        int match = URI_MATCHER.match(uri);
        switch (match) {
            <#list model.entities as entity>
            case URI_TYPE_${entity.nameUpperCase}:
                return TYPE_CURSOR_DIR + ${entity.nameCamelCase}Columns.TABLE_NAME;
            case URI_TYPE_${entity.nameUpperCase}_ID:
                return TYPE_CURSOR_ITEM + ${entity.nameCamelCase}Columns.TABLE_NAME;

            </#list>
        }
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        if (DEBUG) Log.d(TAG, "insert uri=" + uri + " values=" + values);
        return super.insert(uri, values);
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        if (DEBUG) Log.d(TAG, "bulkInsert uri=" + uri + " values.length=" + values.length);
        return super.bulkInsert(uri, values);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if (DEBUG) Log.d(TAG, "update uri=" + uri + " values=" + values + " selection=" + selection + " selectionArgs=" + Arrays.toString(selectionArgs));
        return super.update(uri, values, selection, selectionArgs);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        if (DEBUG) Log.d(TAG, "delete uri=" + uri + " selection=" + selection + " selectionArgs=" + Arrays.toString(selectionArgs));
        return super.delete(uri, selection, selectionArgs);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        if (DEBUG)
            Log.d(TAG, "query uri=" + uri + " selection=" + selection + " selectionArgs=" + Arrays.toString(selectionArgs) + " sortOrder=" + sortOrder
                    + " groupBy=" + uri.getQueryParameter(QUERY_GROUP_BY) + " having=" + uri.getQueryParameter(QUERY_HAVING) + " limit=" + uri.getQueryParameter(QUERY_LIMIT));
        return super.query(uri, projection, selection, selectionArgs, sortOrder);
    }

    @Override
    protected QueryParams getQueryParams(Uri uri, String selection, String[] projection) {
        QueryParams res = new QueryParams();
        String id = null;
        int matchedId = URI_MATCHER.match(uri);
        switch (matchedId) {
            <#list model.entities as entity>
            case URI_TYPE_${entity.nameUpperCase}:
            case URI_TYPE_${entity.nameUpperCase}_ID:
                res.table = ${entity.nameCamelCase}Columns.TABLE_NAME;
                res.idColumn = ${entity.nameCamelCase}Columns._ID;
                res.tablesWithJoins = ${entity.allJoinedTableNames}
                res.orderBy = ${entity.nameCamelCase}Columns.DEFAULT_ORDER;
                break;

            </#list>
            default:
                throw new IllegalArgumentException("The uri '" + uri + "' is not supported by this ContentProvider");
        }

        switch (matchedId) {
            <#list model.entities as entity>
            case URI_TYPE_${entity.nameUpperCase}_ID:
            </#list>
                id = uri.getLastPathSegment();
        }
        if (id != null) {
            if (selection != null) {
                res.selection = res.table + "." + res.idColumn + "=" + id + " and (" + selection + ")";
            } else {
                res.selection = res.table + "." + res.idColumn + "=" + id;
            }
        } else {
            res.selection = selection;
        }
        return res;
    }
}
