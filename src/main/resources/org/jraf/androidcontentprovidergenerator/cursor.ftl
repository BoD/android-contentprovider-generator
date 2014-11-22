<#if header??>
${header}
</#if>
package ${config.providerJavaPackage}.${entity.packageName};

import java.util.Date;

import android.database.Cursor;

import ${config.providerJavaPackage}.base.AbstractCursor;
<#list entity.joinedEntities as joinedEntity>
import ${config.providerJavaPackage}.${joinedEntity.packageName}.*;
</#list>

/**
 * Cursor wrapper for the {@code ${entity.nameLowerCase}} table.
 */
public class ${entity.nameCamelCase}Cursor extends AbstractCursor {
    public ${entity.nameCamelCase}Cursor(Cursor cursor) {
        super(cursor);
    }
    <#list entity.getFieldsIncludingJoins() as field>
        <#if !field.isId>

    /**
    <#if field.documentation??>
     * ${field.documentation}
    <#else>
     * Get the {@code ${field.nameLowerCase}} value.
    </#if>
        <#if field.isNullable>
     * Can be {@code null}.
        <#else>
            <#if !field.type.hasNotNullableJavaType()>
     * Cannot be {@code null}.
            </#if>
        </#if>
     */
    public ${field.javaTypeSimpleName} get<#if field.isForeign>${field.path}</#if>${field.nameCamelCase}() {
            <#switch field.type.name()>
            <#case "STRING">
        Integer index = getCachedColumnIndexOrThrow(${field.entity.nameCamelCase}Columns.${field.nameUpperCase});
        return getString(index);
            <#break>
            <#case "INTEGER">
        return getIntegerOrNull(${field.entity.nameCamelCase}Columns.${field.nameUpperCase});
            <#break>
            <#case "LONG">
        return getLongOrNull(${field.entity.nameCamelCase}Columns.${field.nameUpperCase});
            <#break>
            <#case "FLOAT">
        return getFloatOrNull(${field.entity.nameCamelCase}Columns.${field.nameUpperCase});
            <#break>
            <#case "DOUBLE">
        return getDoubleOrNull(${field.entity.nameCamelCase}Columns.${field.nameUpperCase});
            <#break>
            <#case "BOOLEAN">
        return getBoolean(${field.entity.nameCamelCase}Columns.${field.nameUpperCase});
            <#break>
            <#case "DATE">
        return getDate(${field.entity.nameCamelCase}Columns.${field.nameUpperCase});
            <#break>
            <#case "BYTE_ARRAY">
        Integer index = getCachedColumnIndexOrThrow(${field.entity.nameCamelCase}Columns.${field.nameUpperCase});
        return getBlob(index);
            <#break>
            <#case "ENUM">
        Integer intValue = getIntegerOrNull(${field.entity.nameCamelCase}Columns.${field.nameUpperCase});
        if (intValue == null) return null;
        return ${field.javaTypeSimpleName}.values()[intValue];
            <#break>
            </#switch>
    }
        </#if>
    </#list>
}
