<#if header??>
${header}
</#if>
package ${config.providerPackage}.wrapper.cursor;

import java.util.Date;

import android.database.Cursor;
import ${config.providerPackage}.table.${entity.nameCamelCase}Columns;
import ${config.projectPackage}.BuildConfig;

/**
 * Cursor wrapper for the {@code ${entity.nameLowerCase}} table.
 */
public class ${entity.nameCamelCase}CursorWrapper extends AbstractCursorWrapper {
    public ${entity.nameCamelCase}CursorWrapper(Cursor cursor) {
        super(cursor);
    }
    <#list entity.fields as field>

    public ${field.type.javaType.simpleName} get${field.nameCamelCase}() {
        <#switch field.type.name()>
        <#case "TEXT">
        return getStringOrNull(${entity.nameCamelCase}Columns.${field.nameUpperCase});
        <#break>
        <#case "BLOB">
        Integer index = getCachedColumnIndexOrThrow(${entity.nameCamelCase}Columns.${field.nameUpperCase});
        return getBlob(index);
        <#break>
        <#case "INTEGER">
        return getInt(${entity.nameCamelCase}Columns.${field.nameUpperCase});
        <#break>
        <#case "FLOAT">
        return getFloat(${entity.nameCamelCase}Columns.${field.nameUpperCase});
        <#break>
        <#case "DOUBLE">
        return getDouble(${entity.nameCamelCase}Columns.${field.nameUpperCase});
        <#break>
        <#case "DATE">
        return getDateOrNull(${entity.nameCamelCase}Columns.${field.nameUpperCase});
        <#break>
        <#case "BOOLEAN">
        return getBoolean(${entity.nameCamelCase}Columns.${field.nameUpperCase});
        <#break>
        <#case "LONG">
        return getLong(${entity.nameCamelCase}Columns.${field.nameUpperCase});
        <#break>
        </#switch>
    }
    </#list>
}
