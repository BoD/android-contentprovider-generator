package ${config.package};

import android.net.Uri;
import android.provider.BaseColumns;

public class ${entity.nameCamelCase}Columns implements BaseColumns {
    public static final String TABLE_NAME = "${entity.nameLowerCase}";
    public static final Uri CONTENT_URI = Uri.parse(${config.providerClassName}.CONTENT_URI_BASE + "/" + TABLE_NAME);

    public static final String _ID = BaseColumns._ID;

    <#list entity.fields as field>
    public static final String ${field.nameUpperCase} = "${field.nameLowerCase}";
    </#list>

    public static final String DEFAULT_ORDER = _ID;
}