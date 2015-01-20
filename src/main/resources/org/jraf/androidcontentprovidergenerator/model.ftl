<#if header??>
${header}
</#if>
package ${config.providerJavaPackage}.${entity.packageName};

import ${config.providerJavaPackage}.base.BaseModel;

<#if annotations>
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
</#if>

import java.util.Date;

/**
<#if entity.documentation??>
 * ${entity.documentation}
<#else>
 * Data model for the {@code ${entity.nameLowerCase}} table.
</#if>
 */
public interface ${entity.nameCamelCase}Model extends BaseModel {
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
     <#if annotations>
        <#if field.isNullable>
    @Nullable
        <#else>
            <#if !field.type.hasNotNullableJavaType()>
    @NonNull
            </#if>
        </#if>
     </#if>
    ${field.javaTypeSimpleName} get<#if field.isForeign>${field.path}</#if>${field.nameCamelCase}();
        </#if>
    </#list>
}
