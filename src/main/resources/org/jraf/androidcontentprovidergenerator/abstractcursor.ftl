<#if header??>
${header}
</#if>
package ${config.providerJavaPackage}.base;

import java.util.Date;
import java.util.HashMap;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.provider.BaseColumns;

public abstract class AbstractCursor extends CursorWrapper {
	private HashMap<String, Integer> mColumnIndexes = new HashMap<String, Integer>();

    public AbstractCursor(Cursor cursor) {
        super(cursor);
    }

    public long getId() {
        return getLongOrNull(BaseColumns._ID);
    }

    protected int getCachedColumnIndexOrThrow(String colName) {
        Integer index = mColumnIndexes.get(colName);
        if (index == null) {
            index = getColumnIndexOrThrow(colName);
            mColumnIndexes.put(colName, index);
        }
        return index;
    }

    public String getStringOrNull(String colName) {
        Integer index = getCachedColumnIndexOrThrow(colName);
        if (isNull(index)) return null;
        return getString(index);
    }

    public Integer getIntegerOrNull(String colName) {
        Integer index = getCachedColumnIndexOrThrow(colName);
        if (isNull(index)) return null;
        return getInt(index);
    }

    public Long getLongOrNull(String colName) {
        Integer index = getCachedColumnIndexOrThrow(colName);
        if (isNull(index)) return null;
        return getLong(index);
    }

    public Float getFloatOrNull(String colName) {
        Integer index = getCachedColumnIndexOrThrow(colName);
        if (isNull(index)) return null;
        return getFloat(index);
    }

    public Double getDoubleOrNull(String colName) {
        Integer index = getCachedColumnIndexOrThrow(colName);
        if (isNull(index)) return null;
        return getDouble(index);
    }

    public Boolean getBoolean(String colName) {
        Integer index = getCachedColumnIndexOrThrow(colName);
        if (isNull(index)) return null;
        return getInt(index) != 0;
    }

    public Date getDate(String colName) {
        Integer index = getCachedColumnIndexOrThrow(colName);
        if (isNull(index)) return null;
        return new Date(getLong(index));
    }
}
