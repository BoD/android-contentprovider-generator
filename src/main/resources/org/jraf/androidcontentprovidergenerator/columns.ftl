<#if header??>
${header}
</#if>
package ${config.providerJavaPackage}.${entity.packageName};

import android.net.Uri;
import android.provider.BaseColumns;

<#if !library>
import ${config.providerJavaPackage}.${config.providerClassName};
</#if>
<#list model.entities as e>
<#if e != entity>
import ${config.providerJavaPackage}.${e.packageName}.${e.nameCamelCase}Columns;
</#if>
</#list>

/**
<#if entity.documentation??>
 * ${entity.documentation}
<#else>
 * Columns for the {@code ${entity.nameLowerCase}} table.
</#if>
 */
public class ${entity.nameCamelCase}Columns implements BaseColumns {
    public static final String TABLE_NAME = "${entity.nameLowerCase}";
    <#if library??>
    public static final Uri CONTENT_URI = Uri.parse("content://${config.authority}/" + TABLE_NAME);
	<#else>
    public static final Uri CONTENT_URI = Uri.parse(${config.providerClassName}.CONTENT_URI_BASE + "/" + TABLE_NAME);
	</#if>

    <#list entity.fields as field>
        <#if field.documentation??>
    /**
     * ${field.documentation}
     */
        </#if>
        <#if field.isId>
            <#if field.nameLowerCase == "_id">
    public static final String _ID = BaseColumns._ID;
            <#else>
    public static final String _ID = "${field.nameOrPrefixed}";

    public static final String ${field.nameUpperCase} = "${field.nameOrPrefixed}";
            </#if>
        <#else>
    public static final String ${field.nameUpperCase} = "${field.nameOrPrefixed}";
        </#if>

    </#list>

    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
        <#list entity.fields as field>
            <#if field.isId>
            _ID<#if field_has_next>,</#if>
            <#else>
            ${field.nameUpperCase}<#if field_has_next>,</#if>
            </#if>
        </#list>
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
        <#list entity.fields as field>
            <#if field.nameLowerCase != "_id">
            if (c.equals(${field.nameUpperCase}) || c.contains("." + ${field.nameUpperCase})) return true;
            </#if>
        </#list>
        }
        return false;
    }

    <#list entity.fields as field>
        <#if field.foreignKey??>
    public static final String PREFIX_${field.foreignKey.entity.nameUpperCase} = TABLE_NAME + "__" + ${field.foreignKey.entity.nameCamelCase}Columns.TABLE_NAME;
        </#if>
    </#list>
}
