<#if header??>
${header}
</#if>
package ${config.providerJavaPackage}.${entity.packageName};

// @formatter:off
import ${config.providerJavaPackage}.base.BaseModel;

import java.util.Date;
<#if config.useAnnotations>

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
</#if>

/**
<#if entity.documentation??>
 * ${entity.documentation}
<#else>
 * Bean for the {@code ${entity.nameLowerCase}} table.
</#if>
 */
@SuppressWarnings({"WeakerAccess", "unused", "ConstantConditions"})
public class ${entity.nameCamelCase}Bean implements ${entity.nameCamelCase}Model {
    <#list entity.getFields() as field>
    private ${field.javaTypeSimpleName} m${field.nameCamelCase};
    </#list>
    <#list entity.getFields() as field>

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
    @Override
    public ${field.javaTypeSimpleName} get${field.nameCamelCase}() {
        return m${field.nameCamelCase};
    }

    /**
    <#if field.documentation??>
     * ${field.documentation}
    <#else>
     * Set the {@code ${field.nameLowerCase}} value.
    </#if>
    <#if field.isNullable>
     * Can be {@code null}.
    <#else>
        <#if !field.type.hasNotNullableJavaType()>
     * Must not be {@code null}.
        </#if>
    </#if>
     */
    public void set${field.nameCamelCase}(<#if config.useAnnotations><#if field.isNullable>@Nullable <#else><#if !field.type.hasNotNullableJavaType()>@NonNull </#if></#if></#if>${field.javaTypeSimpleName} ${field.nameCamelCaseLowerCase}) {
        <#if !field.isNullable && !field.type.hasNotNullableJavaType()>
        if (${field.nameCamelCaseLowerCase} == null) throw new IllegalArgumentException("${field.nameCamelCaseLowerCase} must not be null");
        </#if>
        m${field.nameCamelCase} = ${field.nameCamelCaseLowerCase};
    }
    </#list>

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ${entity.nameCamelCase}Bean bean = (${entity.nameCamelCase}Bean) o;
        return m${entity.idField.nameCamelCase} == bean.m${entity.idField.nameCamelCase};
    }

    @Override
    public int hashCode() {
        return (int) (m${entity.idField.nameCamelCase} ^ (m${entity.idField.nameCamelCase} >>> 32));
    }

    /**
     * Instantiate a new ${entity.nameCamelCase}Bean with specified values.
     */
    <#if config.useAnnotations>
    @NonNull
    </#if>
    public static ${entity.nameCamelCase}Bean newInstance(<#list entity.getFields() as field><#if config.useAnnotations><#if field.isNullable>@Nullable <#else><#if !field.type.hasNotNullableJavaType()>@NonNull </#if></#if></#if>${field.javaTypeSimpleName} ${field.nameCamelCaseLowerCase}<#if field_has_next>, </#if></#list>) {
        <#list entity.getFields() as field>
        <#if !field.isNullable && !field.type.hasNotNullableJavaType()>
        if (${field.nameCamelCaseLowerCase} == null) throw new IllegalArgumentException("${field.nameCamelCaseLowerCase} must not be null");
        </#if>
        </#list>
        ${entity.nameCamelCase}Bean res = new ${entity.nameCamelCase}Bean();
        <#list entity.getFields() as field>
        res.m${field.nameCamelCase} = ${field.nameCamelCaseLowerCase};
        </#list>
        return res;
    }

    /**
     * Instantiate a new ${entity.nameCamelCase}Bean with all the values copied from the given model.
     */
    <#if config.useAnnotations>
    @NonNull
    </#if>
    public static ${entity.nameCamelCase}Bean copy(<#if config.useAnnotations>@NonNull </#if>${entity.nameCamelCase}Model from) {
        ${entity.nameCamelCase}Bean res = new ${entity.nameCamelCase}Bean();
        <#list entity.getFields() as field>
        res.m${field.nameCamelCase} = from.get${field.nameCamelCase}();
        </#list>
        return res;
    }

    public static class Builder {
        private ${entity.nameCamelCase}Bean mRes = new ${entity.nameCamelCase}Bean();

        <#list entity.getFields() as field>
        /**
        <#if field.documentation??>
         * ${field.documentation}
        <#else>
         * Set the {@code ${field.nameLowerCase}} value.
        </#if>
        <#if field.isNullable>
         * Can be {@code null}.
        <#else>
            <#if !field.type.hasNotNullableJavaType()>
         * Must not be {@code null}.
            </#if>
        </#if>
         */
        public Builder ${field.nameCamelCaseLowerCase}(<#if config.useAnnotations><#if field.isNullable>@Nullable <#else><#if !field.type.hasNotNullableJavaType()>@NonNull </#if></#if></#if>${field.javaTypeSimpleName} ${field.nameCamelCaseLowerCase}) {
            <#if !field.isNullable && !field.type.hasNotNullableJavaType()>
            if (${field.nameCamelCaseLowerCase} == null) throw new IllegalArgumentException("${field.nameCamelCaseLowerCase} must not be null");
            </#if>
            mRes.m${field.nameCamelCase} = ${field.nameCamelCaseLowerCase};
            return this;
        }

        </#list>
        /**
         * Get a new ${entity.nameCamelCase}Bean built with the given values.
         */
        public ${entity.nameCamelCase}Bean build() {
            <#list entity.getFields() as field>
            <#if !field.isNullable && !field.type.hasNotNullableJavaType()>
            if (mRes.m${field.nameCamelCase} == null) throw new IllegalArgumentException("${field.nameCamelCaseLowerCase} must not be null");
            </#if>
            </#list>
            return mRes;
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }
}
