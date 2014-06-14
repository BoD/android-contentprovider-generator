<#if header??>
${header}
</#if>
package ${config.providerJavaPackage}.${entity.nameLowerCase};

import java.util.HashSet;
import java.util.Set;

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
    public static final String ${field.nameUpperCase} = BaseColumns._ID;
    <#else>
    public static final String ${field.nameUpperCase} = "${field.nameLowerCase}";
    </#if>
    </#list>

    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] FULL_PROJECTION = new String[] {
            <#list entity.fields as field>
                <#if field.isId>
            TABLE_NAME + "." + _ID + " AS " + BaseColumns._ID<#if field_has_next>,</#if>
                <#else>
            TABLE_NAME + "." + ${field.nameUpperCase}<#if field_has_next>,</#if>
                </#if>
            </#list>
    };
    // @formatter:on

    public static final Set<String> ALL_COLUMNS = new HashSet<String>();
    static {
        <#list entity.fields as field>
        ALL_COLUMNS.add(${field.nameUpperCase});
        </#list>
    }

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (ALL_COLUMNS.contains(c)) return true;
        }
        return false;
    }
}
