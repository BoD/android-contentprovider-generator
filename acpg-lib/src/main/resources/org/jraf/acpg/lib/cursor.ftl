<#if header??>
${header}
</#if>
package ${config.providerJavaPackage}.${entity.packageName};

// @formatter:off
import java.util.Date;

import android.database.Cursor;
<#if config.useAnnotations>
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
</#if>

import ${config.providerJavaPackage}.base.AbstractCursor;
<#list entity.joinedEntities as joinedEntity>
import ${config.providerJavaPackage}.${joinedEntity.packageName}.*;
</#list>

/**
 * Cursor wrapper for the {@code ${entity.nameLowerCase}} table.
 */
@SuppressWarnings({"WeakerAccess", "unused", "UnnecessaryLocalVariable"})
public class ${entity.nameCamelCase}Cursor extends AbstractCursor implements ${entity.nameCamelCase}Model {
    public ${entity.nameCamelCase}Cursor(Cursor cursor) {
        super(cursor);
    }
<#list entity.getFieldsIncludingJoins() as field>
    <#if field.isId && field.nameLowerCase != '_id'>

    @Override
    public long getId() {
        return getLongOrNull(${field.entity.nameCamelCase}Columns._ID);
    }
    </#if>

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
    <#if config.useAnnotations>
        <#if field.isNullable>
    @Nullable
        <#else>
            <#if !field.type.hasNotNullableJavaType()>
    @NonNull
            </#if>
        </#if>
    </#if>
    <#if !field.isForeign>
    @Override
    </#if>
    public ${field.javaTypeSimpleName} get<#if field.isForeign>${field.path}</#if>${field.nameCamelCase}() {
    <#switch field.type.name()>
        <#case "STRING">
        String res = getStringOrNull(${field.entity.nameCamelCase}Columns.${field.nameUpperCase});
        <#break>
        <#case "INTEGER">
        Integer res = getIntegerOrNull(${field.entity.nameCamelCase}Columns.${field.nameUpperCase});
        <#break>
        <#case "LONG">
        Long res = getLongOrNull(${field.entity.nameCamelCase}Columns.${field.nameUpperCase});
        <#break>
        <#case "FLOAT">
        Float res = getFloatOrNull(${field.entity.nameCamelCase}Columns.${field.nameUpperCase});
        <#break>
        <#case "DOUBLE">
        Double res = getDoubleOrNull(${field.entity.nameCamelCase}Columns.${field.nameUpperCase});
        <#break>
        <#case "BOOLEAN">
        Boolean res = getBooleanOrNull(${field.entity.nameCamelCase}Columns.${field.nameUpperCase});
        <#break>
        <#case "DATE">
        Date res = getDateOrNull(${field.entity.nameCamelCase}Columns.${field.nameUpperCase});
        <#break>
        <#case "BYTE_ARRAY">
        byte[] res = getBlobOrNull(${field.entity.nameCamelCase}Columns.${field.nameUpperCase});
        <#break>
        <#case "ENUM">
        Integer intValue = getIntegerOrNull(${field.entity.nameCamelCase}Columns.${field.nameUpperCase});
            <#if field.isNullable>
        if (intValue == null) return null;
            <#else>
        if (intValue == null)
            throw new NullPointerException("The value of '${field.nameLowerCase}' in the database was null, which is not allowed according to the model definition");
            </#if>
        return ${field.javaTypeSimpleName}.values()[intValue];
        <#break>
    </#switch>
    <#if field.type.name() != "ENUM">
        <#if !field.isNullable>
        if (res == null)
            throw new NullPointerException("The value of '${field.nameLowerCase}' in the database was null, which is not allowed according to the model definition");
        </#if>
        return res;
    </#if>
    }
</#list>
}
