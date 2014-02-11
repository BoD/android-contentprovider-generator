<#if header??>
${header}
</#if>
package ${config.providerJavaPackage}.base;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;

public abstract class AbstractContentValuesWrapper {
    protected ContentValues mContentValues = new ContentValues();

    /**
     * Returns the {@code uri} argument to pass to the {@code ContentResolver} methods.
     */
    public abstract Uri uri();

    public ContentValues getContentValues() {
        return mContentValues;
    }

    /**
     * Inserts a row into a table using the values stored by this object.
     * 
     * @param contentResolver The content resolver to use.
     */
    public Uri insert(ContentResolver contentResolver) {
        return contentResolver.insert(uri(), getContentValues());
    }
}