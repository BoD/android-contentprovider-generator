<#if header??>
${header}
</#if>
package ${config.providerPackage}.wrapper.cursor;

import java.util.Date;
import java.util.HashMap;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.provider.BaseColumns;

public abstract class AbstractCursorWrapper extends CursorWrapper {
	private HashMap<String, Integer> mColumnIndexes = new HashMap<String, Integer>();
	
    public AbstractCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public long getId() {
        return getLong(BaseColumns._ID);
    }

    protected int getCachedColumnIndexOrThrow(String colName) {
    	Integer index = mColumnIndexes.get(colName);
        if (index == null) {
        	index = getColumnIndexOrThrow(colName);
        	mColumnIndexes.put(colName, Integer.valueOf(index));
        }
        return index.intValue();
    }

    public long getLong(String colName) {
        int index = getCachedColumnIndexOrThrow(colName);
        if (isNull(index)) return Long.MIN_VALUE;
        return getLong(index);
    }
    
    public int getInt(String colName) {
        int index = getCachedColumnIndexOrThrow(colName);
        if (isNull(index)) return Integer.MIN_VALUE;
        return getInt(index);
    }

    public boolean getBoolean(String colName) {
        return (getInt(colName) == 1);
    }

    public double getDouble(String colName) {
        int index = getCachedColumnIndexOrThrow(colName);
        if (isNull(index)) {
            return Double.MIN_VALUE;
        }
        return getDouble(index);
    }

    public float getFloat(String colName) {
        int index = getCachedColumnIndexOrThrow(colName);
        if (isNull(index)) {
            return Float.MIN_VALUE;
        }
        return getFloat(index);
    }

    public Date getDateOrNull(String colName) {
        int index = getCachedColumnIndexOrThrow(colName);
        if (isNull(index)) return null;
        return new Date(getLong(index));
    }

     public String getStringOrNull(String colName) {
        int index = getCachedColumnIndexOrThrow(colName);
        if (isNull(index)) return null;
        return getString(index);
    }
}