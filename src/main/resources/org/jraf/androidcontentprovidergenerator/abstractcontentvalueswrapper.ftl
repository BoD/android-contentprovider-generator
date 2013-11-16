<#if header??>
${header}
</#if>
package ${config.providerPackage};

import android.content.ContentValues;

public abstract class AbstractContentValuesWrapper {
    protected ContentValues mContentValues = new ContentValues();

    public ContentValues getContentValues() {
        return mContentValues;
    }
}