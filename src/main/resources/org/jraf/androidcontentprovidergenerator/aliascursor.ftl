<#if header??>
${header}
</#if>
package ${config.providerJavaPackage}.base;

import android.database.Cursor;
import android.database.CursorWrapper;

<#list model.entities as entity>
import ${config.providerJavaPackage}.${entity.packageName}.${entity.nameCamelCase}Columns;
</#list>

public class AliasCursor extends CursorWrapper {
    public AliasCursor(Cursor cursor) {
        super(cursor);
    }

    @Override
    public int getColumnIndexOrThrow(String columnName) throws IllegalArgumentException {
        int res = getColumnIndex(columnName);
        if (res == -1) throw new IllegalArgumentException("column '" + columnName + "' does not exist");
        return res;
    }

    @Override
    public int getColumnIndex(String columnName) {
        int res = super.getColumnIndex(columnName);
        if (res == -1) {
            // Could not find the column, try with its alias
            String alias = getAlias(columnName);
            if (alias == null) return -1;
            return super.getColumnIndex(alias);
        }
        return res;
    }

    private static String getAlias(String columnName) {
        String res = null;
<#list model.entities as entity>
        // ${entity.nameCamelCase}
        res = ${entity.nameCamelCase}Columns.getAlias(columnName);
        if (res != null) return res;

</#list>
        return null;
    }
}