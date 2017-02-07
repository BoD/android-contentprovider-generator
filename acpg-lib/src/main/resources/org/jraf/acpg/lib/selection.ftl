<#if header??>
${header}
</#if>
package ${config.providerJavaPackage}.${entity.packageName};

// @formatter:off
import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
<#if config.useSupportLibrary>
import android.support.v4.content.CursorLoader;
<#else>
import android.content.CursorLoader;
</#if>

import ${config.providerJavaPackage}.base.AbstractSelection;
<#list entity.joinedEntities as joinedEntity>
import ${config.providerJavaPackage}.${joinedEntity.packageName}.*;
</#list>

/**
 * Selection for the {@code ${entity.nameLowerCase}} table.
 */
@SuppressWarnings({"unused", "WeakerAccess", "Recycle"})
public class ${entity.nameCamelCase}Selection extends AbstractSelection<${entity.nameCamelCase}Selection> {
    @Override
    protected Uri baseUri() {
        return ${entity.nameCamelCase}Columns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code ${entity.nameCamelCase}Cursor} object, which is positioned before the first entry, or null.
     */
    public ${entity.nameCamelCase}Cursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new ${entity.nameCamelCase}Cursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, null)}.
     */
    public ${entity.nameCamelCase}Cursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code ${entity.nameCamelCase}Cursor} object, which is positioned before the first entry, or null.
     */
    public ${entity.nameCamelCase}Cursor query(Context context, String[] projection) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new ${entity.nameCamelCase}Cursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, null)}.
     */
    public ${entity.nameCamelCase}Cursor query(Context context) {
        return query(context, null);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public CursorLoader getCursorLoader(Context context, String[] projection) {
        return new CursorLoader(context, uri(), projection, sel(), args(), order()) {
            @Override
            public Cursor loadInBackground() {
                return new ${entity.nameCamelCase}Cursor(super.loadInBackground());
            }
        };
    }


    public ${entity.nameCamelCase}Selection id(long... value) {
        addEquals("${entity.nameLowerCase}." + ${entity.nameCamelCase}Columns._ID, toObjectArray(value));
        return this;
    }

    public ${entity.nameCamelCase}Selection idNot(long... value) {
        addNotEquals("${entity.nameLowerCase}." + ${entity.nameCamelCase}Columns._ID, toObjectArray(value));
        return this;
    }

    public ${entity.nameCamelCase}Selection orderById(boolean desc) {
        orderBy("${entity.nameLowerCase}." + ${entity.nameCamelCase}Columns._ID, desc);
        return this;
    }

    public ${entity.nameCamelCase}Selection orderById() {
        return orderById(false);
    }
    <#list entity.getFieldsIncludingJoins() as field>
    <#if field.nameLowerCase != "_id">
    <#switch field.type.name()>
    <#case "BOOLEAN">

    public ${entity.nameCamelCase}Selection <#if field.isForeign>${field.path?uncap_first}${field.nameCamelCase}<#else>${field.nameCamelCaseLowerCase}</#if>(${field.javaTypeSimpleName} value) {
        addEquals(${field.entity.nameCamelCase}Columns.${field.nameUpperCase}, toObjectArray(value));
        return this;
    }
    <#break>
    <#default>

    public ${entity.nameCamelCase}Selection <#if field.isForeign>${field.path?uncap_first}${field.nameCamelCase}<#else>${field.nameCamelCaseLowerCase}</#if>(${field.javaTypeSimpleName}... value) {
        addEquals(${field.entity.nameCamelCase}Columns.${field.nameUpperCase}, <#if field.isConvertionNeeded>toObjectArray(value)<#else>value</#if>);
        return this;
    }

    public ${entity.nameCamelCase}Selection <#if field.isForeign>${field.path?uncap_first}${field.nameCamelCase}<#else>${field.nameCamelCaseLowerCase}</#if>Not(${field.javaTypeSimpleName}... value) {
        addNotEquals(${field.entity.nameCamelCase}Columns.${field.nameUpperCase}, <#if field.isConvertionNeeded>toObjectArray(value)<#else>value</#if>);
        return this;
    }

    </#switch>
    <#switch field.type.name()>
    <#case "DATE">
    public ${entity.nameCamelCase}Selection <#if field.isForeign>${field.path?uncap_first}${field.nameCamelCase}<#else>${field.nameCamelCaseLowerCase}</#if>(<#if field.isNullable>Long<#else>long</#if>... value) {
        addEquals(${field.entity.nameCamelCase}Columns.${field.nameUpperCase}, <#if field.isNullable>value<#else>toObjectArray(value)</#if>);
        return this;
    }

    public ${entity.nameCamelCase}Selection <#if field.isForeign>${field.path?uncap_first}${field.nameCamelCase}<#else>${field.nameCamelCaseLowerCase}</#if>After(Date value) {
        addGreaterThan(${field.entity.nameCamelCase}Columns.${field.nameUpperCase}, value);
        return this;
    }

    public ${entity.nameCamelCase}Selection <#if field.isForeign>${field.path?uncap_first}${field.nameCamelCase}<#else>${field.nameCamelCaseLowerCase}</#if>AfterEq(Date value) {
        addGreaterThanOrEquals(${field.entity.nameCamelCase}Columns.${field.nameUpperCase}, value);
        return this;
    }

    public ${entity.nameCamelCase}Selection <#if field.isForeign>${field.path?uncap_first}${field.nameCamelCase}<#else>${field.nameCamelCaseLowerCase}</#if>Before(Date value) {
        addLessThan(${field.entity.nameCamelCase}Columns.${field.nameUpperCase}, value);
        return this;
    }

    public ${entity.nameCamelCase}Selection <#if field.isForeign>${field.path?uncap_first}${field.nameCamelCase}<#else>${field.nameCamelCaseLowerCase}</#if>BeforeEq(Date value) {
        addLessThanOrEquals(${field.entity.nameCamelCase}Columns.${field.nameUpperCase}, value);
        return this;
    }
    <#break>
    <#case "INTEGER">
    public ${entity.nameCamelCase}Selection <#if field.isForeign>${field.path?uncap_first}${field.nameCamelCase}<#else>${field.nameCamelCaseLowerCase}</#if>Gt(int value) {
        addGreaterThan(${field.entity.nameCamelCase}Columns.${field.nameUpperCase}, value);
        return this;
    }

    public ${entity.nameCamelCase}Selection <#if field.isForeign>${field.path?uncap_first}${field.nameCamelCase}<#else>${field.nameCamelCaseLowerCase}</#if>GtEq(int value) {
        addGreaterThanOrEquals(${field.entity.nameCamelCase}Columns.${field.nameUpperCase}, value);
        return this;
    }

    public ${entity.nameCamelCase}Selection <#if field.isForeign>${field.path?uncap_first}${field.nameCamelCase}<#else>${field.nameCamelCaseLowerCase}</#if>Lt(int value) {
        addLessThan(${field.entity.nameCamelCase}Columns.${field.nameUpperCase}, value);
        return this;
    }

    public ${entity.nameCamelCase}Selection <#if field.isForeign>${field.path?uncap_first}${field.nameCamelCase}<#else>${field.nameCamelCaseLowerCase}</#if>LtEq(int value) {
        addLessThanOrEquals(${field.entity.nameCamelCase}Columns.${field.nameUpperCase}, value);
        return this;
    }
    <#break>
    <#case "LONG">
    public ${entity.nameCamelCase}Selection <#if field.isForeign>${field.path?uncap_first}${field.nameCamelCase}<#else>${field.nameCamelCaseLowerCase}</#if>Gt(long value) {
        addGreaterThan(${field.entity.nameCamelCase}Columns.${field.nameUpperCase}, value);
        return this;
    }

    public ${entity.nameCamelCase}Selection <#if field.isForeign>${field.path?uncap_first}${field.nameCamelCase}<#else>${field.nameCamelCaseLowerCase}</#if>GtEq(long value) {
        addGreaterThanOrEquals(${field.entity.nameCamelCase}Columns.${field.nameUpperCase}, value);
        return this;
    }

    public ${entity.nameCamelCase}Selection <#if field.isForeign>${field.path?uncap_first}${field.nameCamelCase}<#else>${field.nameCamelCaseLowerCase}</#if>Lt(long value) {
        addLessThan(${field.entity.nameCamelCase}Columns.${field.nameUpperCase}, value);
        return this;
    }

    public ${entity.nameCamelCase}Selection <#if field.isForeign>${field.path?uncap_first}${field.nameCamelCase}<#else>${field.nameCamelCaseLowerCase}</#if>LtEq(long value) {
        addLessThanOrEquals(${field.entity.nameCamelCase}Columns.${field.nameUpperCase}, value);
        return this;
    }
    <#break>
    <#case "FLOAT">
    public ${entity.nameCamelCase}Selection <#if field.isForeign>${field.path?uncap_first}${field.nameCamelCase}<#else>${field.nameCamelCaseLowerCase}</#if>Gt(float value) {
        addGreaterThan(${field.entity.nameCamelCase}Columns.${field.nameUpperCase}, value);
        return this;
    }

    public ${entity.nameCamelCase}Selection <#if field.isForeign>${field.path?uncap_first}${field.nameCamelCase}<#else>${field.nameCamelCaseLowerCase}</#if>GtEq(float value) {
        addGreaterThanOrEquals(${field.entity.nameCamelCase}Columns.${field.nameUpperCase}, value);
        return this;
    }

    public ${entity.nameCamelCase}Selection <#if field.isForeign>${field.path?uncap_first}${field.nameCamelCase}<#else>${field.nameCamelCaseLowerCase}</#if>Lt(float value) {
        addLessThan(${field.entity.nameCamelCase}Columns.${field.nameUpperCase}, value);
        return this;
    }

    public ${entity.nameCamelCase}Selection <#if field.isForeign>${field.path?uncap_first}${field.nameCamelCase}<#else>${field.nameCamelCaseLowerCase}</#if>LtEq(float value) {
        addLessThanOrEquals(${field.entity.nameCamelCase}Columns.${field.nameUpperCase}, value);
        return this;
    }
    <#break>
    <#case "DOUBLE">
    public ${entity.nameCamelCase}Selection <#if field.isForeign>${field.path?uncap_first}${field.nameCamelCase}<#else>${field.nameCamelCaseLowerCase}</#if>Gt(double value) {
        addGreaterThan(${field.entity.nameCamelCase}Columns.${field.nameUpperCase}, value);
        return this;
    }

    public ${entity.nameCamelCase}Selection <#if field.isForeign>${field.path?uncap_first}${field.nameCamelCase}<#else>${field.nameCamelCaseLowerCase}</#if>GtEq(double value) {
        addGreaterThanOrEquals(${field.entity.nameCamelCase}Columns.${field.nameUpperCase}, value);
        return this;
    }

    public ${entity.nameCamelCase}Selection <#if field.isForeign>${field.path?uncap_first}${field.nameCamelCase}<#else>${field.nameCamelCaseLowerCase}</#if>Lt(double value) {
        addLessThan(${field.entity.nameCamelCase}Columns.${field.nameUpperCase}, value);
        return this;
    }

    public ${entity.nameCamelCase}Selection <#if field.isForeign>${field.path?uncap_first}${field.nameCamelCase}<#else>${field.nameCamelCaseLowerCase}</#if>LtEq(double value) {
        addLessThanOrEquals(${field.entity.nameCamelCase}Columns.${field.nameUpperCase}, value);
        return this;
    }
    <#break>
    <#case "STRING">
    public ${entity.nameCamelCase}Selection <#if field.isForeign>${field.path?uncap_first}${field.nameCamelCase}<#else>${field.nameCamelCaseLowerCase}</#if>Like(String... value) {
        addLike(${field.entity.nameCamelCase}Columns.${field.nameUpperCase}, value);
        return this;
    }

    public ${entity.nameCamelCase}Selection <#if field.isForeign>${field.path?uncap_first}${field.nameCamelCase}<#else>${field.nameCamelCaseLowerCase}</#if>Contains(String... value) {
        addContains(${field.entity.nameCamelCase}Columns.${field.nameUpperCase}, value);
        return this;
    }

    public ${entity.nameCamelCase}Selection <#if field.isForeign>${field.path?uncap_first}${field.nameCamelCase}<#else>${field.nameCamelCaseLowerCase}</#if>StartsWith(String... value) {
        addStartsWith(${field.entity.nameCamelCase}Columns.${field.nameUpperCase}, value);
        return this;
    }

    public ${entity.nameCamelCase}Selection <#if field.isForeign>${field.path?uncap_first}${field.nameCamelCase}<#else>${field.nameCamelCaseLowerCase}</#if>EndsWith(String... value) {
        addEndsWith(${field.entity.nameCamelCase}Columns.${field.nameUpperCase}, value);
        return this;
    }
    <#break>
    </#switch>

    public ${entity.nameCamelCase}Selection orderBy<#if field.isForeign>${field.path}${field.nameCamelCase}<#else>${field.nameCamelCase}</#if>(boolean desc) {
        orderBy(${field.entity.nameCamelCase}Columns.${field.nameUpperCase}, desc);
        return this;
    }

    public ${entity.nameCamelCase}Selection orderBy<#if field.isForeign>${field.path}${field.nameCamelCase}<#else>${field.nameCamelCase}</#if>() {
        orderBy(${field.entity.nameCamelCase}Columns.${field.nameUpperCase}, false);
        return this;
    }
    </#if>
    </#list>
}
