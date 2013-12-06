<#if header??>
${header}
</#if>
package ${config.providerPackage};

import java.util.Date;

/**
 * Selection for the {@code ${entity.nameLowerCase}} table.
 */
public class ${entity.nameCamelCase}Selection extends AbstractSelection<${entity.nameCamelCase}Selection> {
    public ${entity.nameCamelCase}Selection id(Long... value) {
        addEquals(${entity.nameCamelCase}Columns._ID, (Object[]) value);
        return this;
    }

    <#list entity.fields as field>

    public ${entity.nameCamelCase}Selection ${field.nameCamelCaseLowerCase}(${field.type.javaType.simpleName}... value) {
        addEquals(${entity.nameCamelCase}Columns.${field.nameUpperCase}, (Object[]) value);
        return this;
    }

    <#switch field.type.name()>
    <#case "DATE">
    public ${entity.nameCamelCase}Selection ${field.nameCamelCaseLowerCase}(Long... value) {
        addEquals(${entity.nameCamelCase}Columns.${field.nameUpperCase}, (Object[]) value);
        return this;
    }
    <#break>
    <#case "INTEGER">
    public ${entity.nameCamelCase}Selection ${field.nameCamelCaseLowerCase}Gt(long value) {
        addGreaterThan(${entity.nameCamelCase}Columns.${field.nameUpperCase}, value);
        return this;
    }

    public ${entity.nameCamelCase}Selection ${field.nameCamelCaseLowerCase}Lt(long value) {
        addLessThan(${entity.nameCamelCase}Columns.${field.nameUpperCase}, value);
        return this;
    }
    <#break>
    <#case "FLOAT">
    public ${entity.nameCamelCase}Selection ${field.nameCamelCaseLowerCase}Gt(double value) {
        addGreaterThan(${entity.nameCamelCase}Columns.${field.nameUpperCase}, value);
        return this;
    }

    public ${entity.nameCamelCase}Selection ${field.nameCamelCaseLowerCase}Lt(double value) {
        addLessThan(${entity.nameCamelCase}Columns.${field.nameUpperCase}, value);
        return this;
    }
    <#break>
    </#switch>
    </#list>
}
