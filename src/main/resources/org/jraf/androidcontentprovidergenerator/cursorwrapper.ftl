<#if header??>
${header}
</#if>
package ${config.providerPackage};

import java.util.HashMap;

import android.database.Cursor;
import android.database.CursorWrapper;

public class ${entity.nameCamelCase}CursorWrapper extends CursorWrapper {
	private HashMap<String, Integer> mColumnIndexes = new HashMap<String, Integer>();
	
    public ${entity.nameCamelCase}CursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Long getId() {
        Integer index = mColumnIndexes.get("_id");
        if (index == null) {
        	index = getColumnIndexOrThrow("_id");
        	mColumnIndexes.put("type", index);
        }
        if (isNull(index)) return null;
        return getLong(index);
    }
    <#list entity.fields as field>

    public ${field.type.javaType.simpleName} get${field.nameCamelCase}() {
        Integer index = mColumnIndexes.get("${field.nameLowerCase}");
        if (index == null) {
        	index = getColumnIndexOrThrow("${field.nameLowerCase}");
        	mColumnIndexes.put("${field.nameLowerCase}", index);
        }
        <#switch field.type.name()>
        <#case "TEXT">
        return getString(index);
        <#break>
        <#case "BLOB">
        return getBlob(index);
        <#break>
        <#case "INTEGER">
        if (isNull(index)) return null;
        return getLong(index);
        <#break>
        </#switch>
    }
    </#list>
}
