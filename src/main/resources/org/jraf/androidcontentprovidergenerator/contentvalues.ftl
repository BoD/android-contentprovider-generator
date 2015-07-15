<#if header??>
${header}
</#if>
package ${config.providerJavaPackage}.${entity.packageName};

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.net.Uri;
<#if config.useAnnotations>
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
</#if>

import ${config.providerJavaPackage}.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code ${entity.nameLowerCase}} table.
 */
public class ${entity.nameCamelCase}ContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return ${entity.nameCamelCase}Columns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, <#if config.useAnnotations>@Nullable</#if> ${entity.nameCamelCase}Selection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(Context context, <#if config.useAnnotations>@Nullable</#if> ${entity.nameCamelCase}Selection where) {
        return context.getContentResolver().update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }
    <#list entity.fields as field>
        <#if field.nameLowerCase != "_id">

    <#if field.documentation??>
    /**
     * ${field.documentation}
     */
    </#if>
    <#if config.useAnnotations && !field.isNullable && !field.type.hasNotNullableJavaType()>
    public ${entity.nameCamelCase}ContentValues put${field.nameCamelCase}(@NonNull ${field.javaTypeSimpleName} value) {
    <#elseif config.useAnnotations && field.isNullable>
    public ${entity.nameCamelCase}ContentValues put${field.nameCamelCase}(@Nullable ${field.javaTypeSimpleName} value) {
    <#else>
    public ${entity.nameCamelCase}ContentValues put${field.nameCamelCase}(${field.javaTypeSimpleName} value) {
    </#if>
            <#if !field.isNullable && !field.type.hasNotNullableJavaType()>
        if (value == null) throw new IllegalArgumentException("${field.nameCamelCaseLowerCase} must not be null");
            </#if>
            <#switch field.type.name()>
            <#case "DATE">
        mContentValues.put(${entity.nameCamelCase}Columns.${field.nameUpperCase}, <#if field.isNullable>value == null ? null : </#if>value.getTime());
            <#break>
            <#case "ENUM">
        mContentValues.put(${entity.nameCamelCase}Columns.${field.nameUpperCase}, <#if field.isNullable>value == null ? null : </#if>value.ordinal());
            <#break>
            <#default>
        mContentValues.put(${entity.nameCamelCase}Columns.${field.nameUpperCase}, value);
            </#switch>
        return this;
    }

            <#if field.isNullable>
    public ${entity.nameCamelCase}ContentValues put${field.nameCamelCase}Null() {
        mContentValues.putNull(${entity.nameCamelCase}Columns.${field.nameUpperCase});
        return this;
    }
            </#if>
            <#switch field.type.name()>
            <#case "DATE">

    public ${entity.nameCamelCase}ContentValues put${field.nameCamelCase}(<#if field.isNullable><#if config.useAnnotations>@Nullable </#if>Long<#else>long</#if> value) {
        mContentValues.put(${entity.nameCamelCase}Columns.${field.nameUpperCase}, value);
        return this;
    }
            <#break>
            </#switch>
        </#if>
    </#list>
}
