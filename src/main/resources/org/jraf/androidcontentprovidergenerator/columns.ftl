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
public interface ${entity.nameCamelCase}Columns extends BaseColumns {
    String TABLE_NAME = "${entity.nameLowerCase}";
    Uri CONTENT_URI = Uri.parse(${config.providerClassName}.CONTENT_URI_BASE + "/" + TABLE_NAME);

    String _ID = BaseColumns._ID;
    <#list entity.fields as field>
    String ${field.nameUpperCase} = "${field.nameLowerCase}";
    </#list>

    String DEFAULT_ORDER = _ID;

	// @formatter:off
    String[] FULL_PROJECTION = new String[] {
            _ID,
            <#list entity.fields as field>
            ${field.nameUpperCase}<#if field_has_next>,</#if>
            </#list>
    };
    // @formatter:on
}