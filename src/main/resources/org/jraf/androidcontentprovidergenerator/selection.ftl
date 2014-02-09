<#if header??>
${header}
</#if>
package ${config.providerJavaPackage}.${entity.nameLowerCase};

import java.util.Date;

import ${config.providerJavaPackage}.base.AbstractSelection;

/**
 * Selection for the {@code ${entity.nameLowerCase}} table.
 */
public class ${entity.nameCamelCase}Selection extends AbstractSelection<${entity.nameCamelCase}Selection> {
    public ${entity.nameCamelCase}Selection id(long... value) {
        addEquals(${entity.nameCamelCase}Columns._ID, toObjectArray(value));
        return this;
    }
    <#list entity.fields as field>

    public ${entity.nameCamelCase}Selection ${field.nameCamelCaseLowerCase}(${field.javaType.simpleName}... value) {
        addEquals(${entity.nameCamelCase}Columns.${field.nameUpperCase}, <#if field.isConvertionNeeded>toObjectArray(value)<#else>value</#if>);
        return this;
    }
    
    public ${entity.nameCamelCase}Selection ${field.nameCamelCaseLowerCase}Not(${field.javaType.simpleName}... value) {
        addNotEquals(${entity.nameCamelCase}Columns.${field.nameUpperCase}, <#if field.isConvertionNeeded>toObjectArray(value)<#else>value</#if>);
        return this;
    }

    <#switch field.type.name()>
    <#case "DATE">
    public ${entity.nameCamelCase}Selection ${field.nameCamelCaseLowerCase}(<#if field.isNullable>Long<#else>long</#if>... value) {
        addEquals(${entity.nameCamelCase}Columns.${field.nameUpperCase}, <#if field.isNullable>value<#else>toObjectArray(value)</#if>);
        return this;
    }

    public ${entity.nameCamelCase}Selection ${field.nameCamelCaseLowerCase}After(Date value) {
        addGreaterThan(${entity.nameCamelCase}Columns.${field.nameUpperCase}, value);
        return this;
    }

    public ${entity.nameCamelCase}Selection ${field.nameCamelCaseLowerCase}AfterEq(Date value) {
        addGreaterThanOrEquals(${entity.nameCamelCase}Columns.${field.nameUpperCase}, value);
        return this;
    }

    public ${entity.nameCamelCase}Selection ${field.nameCamelCaseLowerCase}Before(Date value) {
        addLessThan(${entity.nameCamelCase}Columns.${field.nameUpperCase}, value);
        return this;
    }

    public ${entity.nameCamelCase}Selection ${field.nameCamelCaseLowerCase}BeforeEq(Date value) {
        addLessThanOrEquals(${entity.nameCamelCase}Columns.${field.nameUpperCase}, value);
        return this;
    }
    <#break>
    <#case "INTEGER">
    public ${entity.nameCamelCase}Selection ${field.nameCamelCaseLowerCase}Gt(int value) {
        addGreaterThan(${entity.nameCamelCase}Columns.${field.nameUpperCase}, value);
        return this;
    }

    public ${entity.nameCamelCase}Selection ${field.nameCamelCaseLowerCase}GtEq(int value) {
        addGreaterThanOrEquals(${entity.nameCamelCase}Columns.${field.nameUpperCase}, value);
        return this;
    }

    public ${entity.nameCamelCase}Selection ${field.nameCamelCaseLowerCase}Lt(int value) {
        addLessThan(${entity.nameCamelCase}Columns.${field.nameUpperCase}, value);
        return this;
    }

    public ${entity.nameCamelCase}Selection ${field.nameCamelCaseLowerCase}LtEq(int value) {
        addLessThanOrEquals(${entity.nameCamelCase}Columns.${field.nameUpperCase}, value);
        return this;
    }
    <#break>
    <#case "LONG">
    public ${entity.nameCamelCase}Selection ${field.nameCamelCaseLowerCase}Gt(long value) {
        addGreaterThan(${entity.nameCamelCase}Columns.${field.nameUpperCase}, value);
        return this;
    }

    public ${entity.nameCamelCase}Selection ${field.nameCamelCaseLowerCase}GtEq(long value) {
        addGreaterThanOrEquals(${entity.nameCamelCase}Columns.${field.nameUpperCase}, value);
        return this;
    }

    public ${entity.nameCamelCase}Selection ${field.nameCamelCaseLowerCase}Lt(long value) {
        addLessThan(${entity.nameCamelCase}Columns.${field.nameUpperCase}, value);
        return this;
    }

    public ${entity.nameCamelCase}Selection ${field.nameCamelCaseLowerCase}LtEq(long value) {
        addLessThanOrEquals(${entity.nameCamelCase}Columns.${field.nameUpperCase}, value);
        return this;
    }
    <#break>
    <#case "FLOAT">
    public ${entity.nameCamelCase}Selection ${field.nameCamelCaseLowerCase}Gt(float value) {
        addGreaterThan(${entity.nameCamelCase}Columns.${field.nameUpperCase}, value);
        return this;
    }

    public ${entity.nameCamelCase}Selection ${field.nameCamelCaseLowerCase}GtEq(float value) {
        addGreaterThanOrEquals(${entity.nameCamelCase}Columns.${field.nameUpperCase}, value);
        return this;
    }

    public ${entity.nameCamelCase}Selection ${field.nameCamelCaseLowerCase}Lt(float value) {
        addLessThan(${entity.nameCamelCase}Columns.${field.nameUpperCase}, value);
        return this;
    }

    public ${entity.nameCamelCase}Selection ${field.nameCamelCaseLowerCase}LtEq(float value) {
        addLessThanOrEquals(${entity.nameCamelCase}Columns.${field.nameUpperCase}, value);
        return this;
    }
    <#break>
    <#case "DOUBLE">
    public ${entity.nameCamelCase}Selection ${field.nameCamelCaseLowerCase}Gt(double value) {
        addGreaterThan(${entity.nameCamelCase}Columns.${field.nameUpperCase}, value);
        return this;
    }

    public ${entity.nameCamelCase}Selection ${field.nameCamelCaseLowerCase}GtEq(double value) {
        addGreaterThanOrEquals(${entity.nameCamelCase}Columns.${field.nameUpperCase}, value);
        return this;
    }

    public ${entity.nameCamelCase}Selection ${field.nameCamelCaseLowerCase}Lt(double value) {
        addLessThan(${entity.nameCamelCase}Columns.${field.nameUpperCase}, value);
        return this;
    }

    public ${entity.nameCamelCase}Selection ${field.nameCamelCaseLowerCase}LtEq(double value) {
        addLessThanOrEquals(${entity.nameCamelCase}Columns.${field.nameUpperCase}, value);
        return this;
    }
    <#break>
    </#switch>
    </#list>
}
