<#if header??>
${header}
</#if>
package ${config.providerJavaPackage}.${entity.nameLowerCase};

import java.util.Date;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import ${config.providerJavaPackage}.base.AbstractSelection;

/**
 * Selection for the {@code ${entity.nameLowerCase}} table.
 */
public class ${entity.nameCamelCase}Selection extends AbstractSelection<${entity.nameCamelCase}Selection> {
    @Override
    public Uri uri() {
        return ${entity.nameCamelCase}Columns.CONTENT_URI;
    }
    
    /**
     * Query the given content resolver using this selection.
     * 
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @param sortOrder How to order the rows, formatted as an SQL ORDER BY clause (excluding the ORDER BY itself). Passing null will use the default sort
     *            order, which may be unordered.
     * @return A ${entity.nameCamelCase}CursorWrapper object, which is positioned before the first entry, or null.
     */
    public ${entity.nameCamelCase}CursorWrapper query(ContentResolver contentResolver, String[] projection, String sortOrder) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), sortOrder);
        if (cursor == null) return null;
        return new ${entity.nameCamelCase}CursorWrapper(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null}.
     */
    public ${entity.nameCamelCase}CursorWrapper query(ContentResolver contentResolver, String[] projection) {
        return query(contentResolver, projection, null);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null, null}.
     */
    public ${entity.nameCamelCase}CursorWrapper query(ContentResolver contentResolver) {
        return query(contentResolver, null, null);
    }
    
    
    public ${entity.nameCamelCase}Selection id(long... value) {
        addEquals(${entity.nameCamelCase}Columns._ID, toObjectArray(value));
        return this;
    }
    <#list entity.fields as field>

    public ${entity.nameCamelCase}Selection ${field.nameCamelCaseLowerCase}(${field.javaTypeSimpleName}... value) {
        addEquals(${entity.nameCamelCase}Columns.${field.nameUpperCase}, <#if field.isConvertionNeeded>toObjectArray(value)<#else>value</#if>);
        return this;
    }
    
    public ${entity.nameCamelCase}Selection ${field.nameCamelCaseLowerCase}Not(${field.javaTypeSimpleName}... value) {
        addNotEquals(${entity.nameCamelCase}Columns.${field.nameUpperCase}, <#if field.isConvertionNeeded>toObjectArray(value)<#else>value</#if>);
        return this;
    }

    <#switch field.type.name()>
    <#case "DATE">
    public ${entity.nameCamelCase}Selection ${field.nameCamelCaseLowerCase}(<#if field.isNullable>Long<#else>long</#if>... value) {
        addEquals(${entity.nameCamelCase}Columns.${field.nameUpperCase}, <#if field.isNullable>value<#else>toObjectArray(value)</#if>);
        return this;
    }

    public ${entity.nameCamelCase}Selection ${field.nameCamelCaseLowerCase}After(Date value) {
        addGreaterThan(${entity.nameCamelCase}Columns.${field.nameUpperCase}, value);
        return this;
    }

    public ${entity.nameCamelCase}Selection ${field.nameCamelCaseLowerCase}AfterEq(Date value) {
        addGreaterThanOrEquals(${entity.nameCamelCase}Columns.${field.nameUpperCase}, value);
        return this;
    }

    public ${entity.nameCamelCase}Selection ${field.nameCamelCaseLowerCase}Before(Date value) {
        addLessThan(${entity.nameCamelCase}Columns.${field.nameUpperCase}, value);
        return this;
    }

    public ${entity.nameCamelCase}Selection ${field.nameCamelCaseLowerCase}BeforeEq(Date value) {
        addLessThanOrEquals(${entity.nameCamelCase}Columns.${field.nameUpperCase}, value);
        return this;
    }
    <#break>
    <#case "INTEGER">
    public ${entity.nameCamelCase}Selection ${field.nameCamelCaseLowerCase}Gt(int value) {
        addGreaterThan(${entity.nameCamelCase}Columns.${field.nameUpperCase}, value);
        return this;
    }

    public ${entity.nameCamelCase}Selection ${field.nameCamelCaseLowerCase}GtEq(int value) {
        addGreaterThanOrEquals(${entity.nameCamelCase}Columns.${field.nameUpperCase}, value);
        return this;
    }

    public ${entity.nameCamelCase}Selection ${field.nameCamelCaseLowerCase}Lt(int value) {
        addLessThan(${entity.nameCamelCase}Columns.${field.nameUpperCase}, value);
        return this;
    }

    public ${entity.nameCamelCase}Selection ${field.nameCamelCaseLowerCase}LtEq(int value) {
        addLessThanOrEquals(${entity.nameCamelCase}Columns.${field.nameUpperCase}, value);
        return this;
    }
    <#break>
    <#case "LONG">
    public ${entity.nameCamelCase}Selection ${field.nameCamelCaseLowerCase}Gt(long value) {
        addGreaterThan(${entity.nameCamelCase}Columns.${field.nameUpperCase}, value);
        return this;
    }

    public ${entity.nameCamelCase}Selection ${field.nameCamelCaseLowerCase}GtEq(long value) {
        addGreaterThanOrEquals(${entity.nameCamelCase}Columns.${field.nameUpperCase}, value);
        return this;
    }

    public ${entity.nameCamelCase}Selection ${field.nameCamelCaseLowerCase}Lt(long value) {
        addLessThan(${entity.nameCamelCase}Columns.${field.nameUpperCase}, value);
        return this;
    }

    public ${entity.nameCamelCase}Selection ${field.nameCamelCaseLowerCase}LtEq(long value) {
        addLessThanOrEquals(${entity.nameCamelCase}Columns.${field.nameUpperCase}, value);
        return this;
    }
    <#break>
    <#case "FLOAT">
    public ${entity.nameCamelCase}Selection ${field.nameCamelCaseLowerCase}Gt(float value) {
        addGreaterThan(${entity.nameCamelCase}Columns.${field.nameUpperCase}, value);
        return this;
    }

    public ${entity.nameCamelCase}Selection ${field.nameCamelCaseLowerCase}GtEq(float value) {
        addGreaterThanOrEquals(${entity.nameCamelCase}Columns.${field.nameUpperCase}, value);
        return this;
    }

    public ${entity.nameCamelCase}Selection ${field.nameCamelCaseLowerCase}Lt(float value) {
        addLessThan(${entity.nameCamelCase}Columns.${field.nameUpperCase}, value);
        return this;
    }

    public ${entity.nameCamelCase}Selection ${field.nameCamelCaseLowerCase}LtEq(float value) {
        addLessThanOrEquals(${entity.nameCamelCase}Columns.${field.nameUpperCase}, value);
        return this;
    }
    <#break>
    <#case "DOUBLE">
    public ${entity.nameCamelCase}Selection ${field.nameCamelCaseLowerCase}Gt(double value) {
        addGreaterThan(${entity.nameCamelCase}Columns.${field.nameUpperCase}, value);
        return this;
    }

    public ${entity.nameCamelCase}Selection ${field.nameCamelCaseLowerCase}GtEq(double value) {
        addGreaterThanOrEquals(${entity.nameCamelCase}Columns.${field.nameUpperCase}, value);
        return this;
    }

    public ${entity.nameCamelCase}Selection ${field.nameCamelCaseLowerCase}Lt(double value) {
        addLessThan(${entity.nameCamelCase}Columns.${field.nameUpperCase}, value);
        return this;
    }

    public ${entity.nameCamelCase}Selection ${field.nameCamelCaseLowerCase}LtEq(double value) {
        addLessThanOrEquals(${entity.nameCamelCase}Columns.${field.nameUpperCase}, value);
        return this;
    }
    <#break>
    </#switch>
    </#list>
}
