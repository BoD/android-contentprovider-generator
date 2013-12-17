<#if header??>
${header}
</#if>
package ${config.providerPackage}.${entity.nameLowerCase};

import java.util.Date;

import ${config.providerPackage}.base.AbstractContentValuesWrapper;

/**
 * Content values wrapper for the {@code ${entity.nameLowerCase}} table.
 */
public class ${entity.nameCamelCase}ContentValues extends AbstractContentValuesWrapper {
    <#list entity.fields as field>

    public void put${field.nameCamelCase}(${field.type.javaType.simpleName} value) {
        <#switch field.type.name()>
        <#case "DATE">
        mContentValues.put(${entity.nameCamelCase}Columns.${field.nameUpperCase}, value.getTime());
        <#break>
        <#default>
        mContentValues.put(${entity.nameCamelCase}Columns.${field.nameUpperCase}, value);
        </#switch>
    }

    <#if field.isNullable>
    public void put${field.nameCamelCase}Null() {
        mContentValues.putNull(${entity.nameCamelCase}Columns.${field.nameUpperCase});
    }
    </#if>

    <#switch field.type.name()>
    <#case "DATE">
    public void put${field.nameCamelCase}(Long value) {
        mContentValues.put(${entity.nameCamelCase}Columns.${field.nameUpperCase}, value);
    }

    <#break>
    </#switch>
    </#list>
}
