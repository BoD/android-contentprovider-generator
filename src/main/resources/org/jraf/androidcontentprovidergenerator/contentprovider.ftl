<#if header??>
${header}
</#if>
package ${config.providerPackage};

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

import ${config.projectPackage}.Config;
import ${config.projectPackage}.Constants;

public class ${config.providerClassName} extends ContentProvider {
    private static final String TAG = Constants.TAG + ${config.providerClassName}.class.getSimpleName();

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

    private ${config.sqliteHelperClassName} m${config.sqliteHelperClassName};

    @Override
    public boolean onCreate() {
        m${config.sqliteHelperClassName} = new ${config.sqliteHelperClassName}(getContext());
        return true;
    }

    @Override
    public String getType(Uri uri) {
        final int match = URI_MATCHER.match(uri);
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
        if (Config.LOGD_PROVIDER) Log.d(TAG, "insert uri=" + uri + " values=" + values);
        final String table = uri.getLastPathSegment();
        final long rowId = m${config.sqliteHelperClassName}.getWritableDatabase().insert(table, null, values);
        String notify;
        if (rowId != -1 && ((notify = uri.getQueryParameter(QUERY_NOTIFY)) == null || "true".equals(notify))) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return uri.buildUpon().appendEncodedPath(String.valueOf(rowId)).build();
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        if (Config.LOGD_PROVIDER) Log.d(TAG, "bulkInsert uri=" + uri + " values.length=" + values.length);
        final String table = uri.getLastPathSegment();
        final SQLiteDatabase db = m${config.sqliteHelperClassName}.getWritableDatabase();
        int res = 0;
        db.beginTransaction();
        try {
            for (final ContentValues v : values) {
                final long id = db.insert(table, null, v);
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
        if (Config.LOGD_PROVIDER) Log.d(TAG, "update uri=" + uri + " values=" + values + " selection=" + selection);
        final QueryParams queryParams = getQueryParams(uri, selection);
        final int res = m${config.sqliteHelperClassName}.getWritableDatabase().update(queryParams.table, values, queryParams.selection, selectionArgs);
        String notify;
        if (res != 0 && ((notify = uri.getQueryParameter(QUERY_NOTIFY)) == null || "true".equals(notify))) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return res;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        if (Config.LOGD_PROVIDER) Log.d(TAG, "delete uri=" + uri + " selection=" + selection);
        final QueryParams queryParams = getQueryParams(uri, selection);
        final int res = m${config.sqliteHelperClassName}.getWritableDatabase().delete(queryParams.table, queryParams.selection, selectionArgs);
        String notify;
        if (res != 0 && ((notify = uri.getQueryParameter(QUERY_NOTIFY)) == null || "true".equals(notify))) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return res;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        final String groupBy = uri.getQueryParameter(QUERY_GROUP_BY);
        if (Config.LOGD_PROVIDER) Log.d(TAG, "query uri=" + uri + " selection=" + selection + " sortOrder=" + sortOrder + " groupBy=" + groupBy);
        final QueryParams queryParams = getQueryParams(uri, selection);
        final Cursor res = m${config.sqliteHelperClassName}.getReadableDatabase().query(queryParams.table, projection, queryParams.selection, selectionArgs, groupBy,
                null, sortOrder == null ? queryParams.orderBy : sortOrder);
        res.setNotificationUri(getContext().getContentResolver(), uri);
        return res;
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
