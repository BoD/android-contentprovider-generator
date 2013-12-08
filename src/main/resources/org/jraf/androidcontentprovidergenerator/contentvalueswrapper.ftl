<#if header??>
${header}
</#if>
package ${config.providerPackage}.wrapper.contentvalues;

import java.util.Date;
import ${config.providerPackage}.table.${entity.nameCamelCase}Columns;

/**
 * Content values wrapper for the {@code ${entity.nameLowerCase}} table.
 */
public class ${entity.nameCamelCase}ContentValues extends AbstractContentValuesWrapper {
    <#list entity.fields as field>

    public void put${field.nameCamelCase}(${field.type.javaType.simpleName} value) {
        <#switch field.type.name()>
        <#case "DATE">
        mContentValues.put(${entity.nameCamelCase}Columns.${field.nameUpperCase}, Long.valueOf(value.getTime()));
        <#break>
        <#case "LONG">
        mContentValues.put(${entity.nameCamelCase}Columns.${field.nameUpperCase}, Long.valueOf(value));
        <#break>
        <#case "INTEGER">
        mContentValues.put(${entity.nameCamelCase}Columns.${field.nameUpperCase}, Integer.valueOf(value));
        <#break>
        <#case "FLOAT">
        mContentValues.put(${entity.nameCamelCase}Columns.${field.nameUpperCase}, Float.valueOf(value));
        <#break>
        <#case "DOUBLE">
        mContentValues.put(${entity.nameCamelCase}Columns.${field.nameUpperCase}, Double.valueOf(value));
        <#break>
        <#case "BOOLEAN">
        mContentValues.put(${entity.nameCamelCase}Columns.${field.nameUpperCase}, Boolean.valueOf(value));
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

    </#list>
}
