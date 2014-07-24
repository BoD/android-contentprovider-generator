<#if header??>
${header}
</#if>
package ${config.providerJavaPackage};

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

import ${config.projectPackageId}.BuildConfig;
<#list model.entities as entity>
import ${config.providerJavaPackage}.${entity.packageName}.${entity.nameCamelCase}Columns;
</#list>

public class ${config.providerClassName} extends ContentProvider {
    private static final String TAG = ${config.providerClassName}.class.getSimpleName();

    private static final String TYPE_CURSOR_ITEM = "vnd.android.cursor.item/";
    private static final String TYPE_CURSOR_DIR = "vnd.android.cursor.dir/";

    public static final String AUTHORITY = "${config.authority}";
    public static final String CONTENT_URI_BASE = "content://" + AUTHORITY;

    public static final String QUERY_NOTIFY = "QUERY_NOTIFY";
    public static final String QUERY_GROUP_BY = "QUERY_GROUP_BY";

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

    private ${config.sqliteOpenHelperClassName} m${config.sqliteOpenHelperClassName};

    @Override
    public boolean onCreate() {
        if (BuildConfig.DEBUG) {
            // Enable logging of SQL statements as they are executed.
            try {
                Class<?> sqliteDebugClass = Class.forName("android.database.sqlite.SQLiteDebug");
                Field field = sqliteDebugClass.getDeclaredField("DEBUG_SQL_STATEMENTS");
                field.setAccessible(true);
                field.set(null, true);

                // Uncomment the following block if you also want logging of execution time (more verbose)
                // field = sqliteDebugClass.getDeclaredField("DEBUG_SQL_TIME");
                // field.setAccessible(true);
                // field.set(null, true);
            } catch (Throwable t) {
                if (BuildConfig.DEBUG) Log.w(TAG, "Could not enable SQLiteDebug logging", t);
            }
        }
        
        m${config.sqliteOpenHelperClassName} = ${config.sqliteOpenHelperClassName}.getInstance(getContext());
        return true;
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
        if (BuildConfig.DEBUG) Log.d(TAG, "insert uri=" + uri + " values=" + values);
        String table = uri.getLastPathSegment();
        long rowId = m${config.sqliteOpenHelperClassName}.getWritableDatabase().insertOrThrow(table, null, values);
        if (rowId == -1) return null;
        String notify;
        if (rowId != -1 && ((notify = uri.getQueryParameter(QUERY_NOTIFY)) == null || "true".equals(notify))) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return uri.buildUpon().appendEncodedPath(String.valueOf(rowId)).build();
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        if (BuildConfig.DEBUG) Log.d(TAG, "bulkInsert uri=" + uri + " values.length=" + values.length);
        String table = uri.getLastPathSegment();
        SQLiteDatabase db = m${config.sqliteOpenHelperClassName}.getWritableDatabase();
        int res = 0;
        db.beginTransaction();
        try {
            for (ContentValues v : values) {
                long id = db.insert(table, null, v);
                db.yieldIfContendedSafely();
                if (id != -1) {
                    res++;
                }
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        String notify;
        if (res != 0 && ((notify = uri.getQueryParameter(QUERY_NOTIFY)) == null || "true".equals(notify))) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return res;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if (BuildConfig.DEBUG)
            Log.d(TAG, "update uri=" + uri + " values=" + values + " selection=" + selection + " selectionArgs=" + Arrays.toString(selectionArgs));
        QueryParams queryParams = getQueryParams(uri, selection);
        int res = m${config.sqliteOpenHelperClassName}.getWritableDatabase().update(queryParams.table, values, queryParams.selection, selectionArgs);
        String notify;
        if (res != 0 && ((notify = uri.getQueryParameter(QUERY_NOTIFY)) == null || "true".equals(notify))) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return res;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        if (BuildConfig.DEBUG) Log.d(TAG, "delete uri=" + uri + " selection=" + selection + " selectionArgs=" + Arrays.toString(selectionArgs));
        QueryParams queryParams = getQueryParams(uri, selection);
        int res = m${config.sqliteOpenHelperClassName}.getWritableDatabase().delete(queryParams.table, queryParams.selection, selectionArgs);
        String notify;
        if (res != 0 && ((notify = uri.getQueryParameter(QUERY_NOTIFY)) == null || "true".equals(notify))) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return res;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        String groupBy = uri.getQueryParameter(QUERY_GROUP_BY);
        if (BuildConfig.DEBUG)
            Log.d(TAG, "query uri=" + uri + " selection=" + selection + " selectionArgs=" + Arrays.toString(selectionArgs) + " sortOrder=" + sortOrder
                    + " groupBy=" + groupBy);
        QueryParams queryParams = getQueryParams(uri, selection);
        Cursor res = m${config.sqliteOpenHelperClassName}.getReadableDatabase().query(queryParams.table, projection, queryParams.selection, selectionArgs, groupBy,
                null, sortOrder == null ? queryParams.orderBy : sortOrder);
        res.setNotificationUri(getContext().getContentResolver(), uri);
        return res;
    }

    @Override
    public ContentProviderResult[] applyBatch(ArrayList<ContentProviderOperation> operations) throws OperationApplicationException {
        HashSet<Uri> urisToNotify = new HashSet<Uri>(operations.size());
        for (ContentProviderOperation operation : operations) {
            urisToNotify.add(operation.getUri());
        }
        SQLiteDatabase db = m${config.sqliteOpenHelperClassName}.getWritableDatabase();
        db.beginTransaction();
        try {
            int numOperations = operations.size();
            ContentProviderResult[] results = new ContentProviderResult[numOperations];
            int i = 0;
            for (ContentProviderOperation operation : operations) {
                results[i] = operation.apply(this, results, i);
                if (operation.isYieldAllowed()) {
                    db.yieldIfContendedSafely();
                }
                i++;
            }
            db.setTransactionSuccessful();
            for (Uri uri : urisToNotify) {
                getContext().getContentResolver().notifyChange(uri, null);
            }
            return results;
        } finally {
            db.endTransaction();
        }
    }

    private static class QueryParams {
        public String table;
        public String selection;
        public String orderBy;
    }

    private QueryParams getQueryParams(Uri uri, String selection) {
        QueryParams res = new QueryParams();
        String id = null;
        int matchedId = URI_MATCHER.match(uri);
        switch (matchedId) {
            <#list model.entities as entity>
            case URI_TYPE_${entity.nameUpperCase}:
            case URI_TYPE_${entity.nameUpperCase}_ID:
                res.table = ${entity.nameCamelCase}Columns.TABLE_NAME;
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
                res.selection = BaseColumns._ID + "=" + id + " and (" + selection + ")";
            } else {
                res.selection = BaseColumns._ID + "=" + id;
            }
        } else {
            res.selection = selection;
        }
        return res;
    }

    public static Uri notify(Uri uri, boolean notify) {
        return uri.buildUpon().appendQueryParameter(QUERY_NOTIFY, String.valueOf(notify)).build();
    }

    public static Uri groupBy(Uri uri, String groupBy) {
        return uri.buildUpon().appendQueryParameter(QUERY_GROUP_BY, groupBy).build();
    }
}
