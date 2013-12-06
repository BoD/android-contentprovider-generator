<#if header??>
${header}
</#if>
package ${config.providerPackage}.wrapper.select;

import ${config.providerPackage}.table.${entity.nameCamelCase}Columns;
import java.util.Date;

/**
 * Selection for the {@code ${entity.nameLowerCase}} table.
 */
public class ${entity.nameCamelCase}Selection extends AbstractSelection<${entity.nameCamelCase}Selection> {

    <#list entity.fields as field>

    <#if field.type.name() == "BOOLEAN">
    public ${entity.nameCamelCase}Selection is${field.nameCamelCaseLowerCase}(${field.type.javaType.simpleName} value) {
        addEquals(${entity.nameCamelCase}Columns.${field.nameUpperCase},  value ? 1 : 0);
        return this;
    }
    <#else>
    public ${entity.nameCamelCase}Selection ${field.nameCamelCaseLowerCase}Eq(${field.type.javaType.simpleName}... value) {
        <#switch field.type.name()>
        <#case "LONG">
        addEquals(${entity.nameCamelCase}Columns.${field.nameUpperCase}, (long[]) value);
        <#break>
        <#case "DOUBLE">
        addEquals(${entity.nameCamelCase}Columns.${field.nameUpperCase}, (double[]) value);
        <#break>
        <#case "FLOAT">
        addEquals(${entity.nameCamelCase}Columns.${field.nameUpperCase}, (float[]) value);
        <#break>
        <#case "INTEGER">
        addEquals(${entity.nameCamelCase}Columns.${field.nameUpperCase}, (int[]) value);
        <#break>
        <#default>
        addEquals(${entity.nameCamelCase}Columns.${field.nameUpperCase}, (Object[]) value);
        </#switch>
        return this;
    }
    </#if>

    public ${entity.nameCamelCase}Selection ${field.nameCamelCaseLowerCase}Gt(${field.type.javaType.simpleName} value) {
    <#switch field.type.name()>
    <#case "DATE">
        addGreaterThan(${entity.nameCamelCase}Columns.${field.nameUpperCase}, value.getTime());
    <#break>
    <#default>
        addGreaterThan(${entity.nameCamelCase}Columns.${field.nameUpperCase}, value);
    </#switch>
        return this;
    }

    public ${entity.nameCamelCase}Selection ${field.nameCamelCaseLowerCase}Lt(${field.type.javaType.simpleName} value) {
    <#switch field.type.name()>
    <#case "DATE">
            addLessThan(${entity.nameCamelCase}Columns.${field.nameUpperCase}, value.getTime());
    <#break>
    <#default>
            addLessThan(${entity.nameCamelCase}Columns.${field.nameUpperCase}, value);
    </#switch>
        return this;
    }
    </#list>
}
