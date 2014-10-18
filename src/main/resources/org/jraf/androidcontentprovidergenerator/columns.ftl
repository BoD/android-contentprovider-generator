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
    public static final String ${field.nameUpperCase} = "${field.nameLowerCase}";
    </#if>
    </#list>

    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
        <#list entity.fields as field>
	        if (c == ${field.nameUpperCase}) return true;
        </#list>
        }
        return false;
    }
}
