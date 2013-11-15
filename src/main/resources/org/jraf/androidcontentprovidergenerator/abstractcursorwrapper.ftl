<#if header??>
${header}
</#if>
package ${config.providerPackage};

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
        	mColumnIndexes.put(colName, index);
        }
        return index;
    }

    public long getLong(String colName) {
        Integer index = getCachedColumnIndexOrThrow(colName);
        if (isNull(index)) return Long.MIN_VALUE;
        return getLong(index);
    }
    
    public Long getLongOrNull(String colName) {
        Integer index = getCachedColumnIndexOrThrow(colName);
        if (isNull(index)) return null;
        return getLong(index);
    }

    public int getInt(String colName) {
        Integer index = getCachedColumnIndexOrThrow(colName);
        if (isNull(index)) return Integer.MIN_VALUE;
        return getInt(index);
    }

    public boolean getBoolean(String colName) {
        return (getInt(colName) == 1);
    }


    public Double getDoubleOrNull(String colName) {
        Integer index = getCachedColumnIndexOrThrow(colName);
        if (isNull(index)) return null;
        return getDouble(index);
    }
    
    public Date getDateOrNull(String colName) {
        Integer index = getCachedColumnIndexOrThrow(colName);
        if (isNull(index)) return null;
        return new Date(getLong(index));
    }

     public String getStringOrNull(String colName) {
        Integer index = getCachedColumnIndexOrThrow(colName);
        if (isNull(index)) return null;
        return getString(index);
    }
}