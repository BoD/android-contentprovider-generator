<#if header??>
${header}
</#if>
package ${config.providerJavaPackage}.${entity.packageName};

import android.net.Uri;
import android.provider.BaseColumns;

import ${config.providerJavaPackage}.${config.providerClassName};

/**
 * Columns for the {@code ${entity.nameLowerCase}} table.
 */
public class ${entity.nameCamelCase}Columns implements BaseColumns {
    public static final String TABLE_NAME = "${entity.nameLowerCase}";
    public static final Uri CONTENT_URI = Uri.parse(${config.providerClassName}.CONTENT_URI_BASE + "/" + TABLE_NAME);

    <#list entity.fields as field>
    <#if field.isId>
    public static final String ${field.nameUpperCase} = new String(BaseColumns._ID);
    <#else>
    public static final String ${field.nameUpperCase} = new String("${field.nameLowerCase}");
    </#if>
    </#list>

    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;
    
    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            <#list entity.fields as field>
            ${field.nameUpperCase}<#if field_has_next>,</#if>
            </#list>
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
        <#list entity.fields as field>
            if (c == ${field.nameUpperCase}) return true;
        </#list>
        }
        return false;
    }

    public static String getQualifiedColumnName(String columnName) {
        <#list entity.fields as field>
            <#if field.isId>
        if (columnName == _ID) return TABLE_NAME + "." + columnName + " AS " + _ID;
            <#else>
        if (columnName == ${field.nameUpperCase}) return TABLE_NAME + "." + columnName + " AS " + TABLE_NAME + "__" + columnName;
            </#if>
        </#list>
        return null;
    }

    public static String getAlias(String columnName) {
        <#list entity.fields as field>
            <#if !field.isId>
        if (columnName == ${field.nameUpperCase}) return TABLE_NAME + "__" + columnName;
            </#if>
        </#list>
        return null;
    }
}
