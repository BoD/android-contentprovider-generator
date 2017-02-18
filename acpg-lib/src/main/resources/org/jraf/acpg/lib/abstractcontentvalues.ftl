<#if header??>
${header}
</#if>
package ${config.providerJavaPackage}.base;

// @formatter:off
import android.content.Context;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;

@SuppressWarnings("unused")
public abstract class AbstractContentValues<T extends AbstractContentValues<?>> {
    protected final ContentValues mContentValues = new ContentValues();
    private Boolean mNotify;

    /**
     * Returns the {@code uri} argument to pass to the {@code ContentResolver} methods.
     */
    protected abstract Uri baseUri();

    /**
     * Returns the {@code uri} argument to pass to the {@code ContentResolver} methods.
     */
    public Uri uri() {
        Uri uri = baseUri();
        if (mNotify != null) uri = BaseContentProvider.notify(uri, mNotify);
        return uri;
    }

    @SuppressWarnings("unchecked")
    public T notify(boolean notify) {
        mNotify = notify;
        return (T) this;
    }

    /**
     * Returns the {@code ContentValues} wrapped by this object.
     */
    public ContentValues values() {
        return mContentValues;
    }

    /**
     * Inserts a row into a table using the values stored by this object.
     *
     * @param contentResolver The content resolver to use.
     */
    public Uri insert(ContentResolver contentResolver) {
        return contentResolver.insert(uri(), values());
    }

    /**
     * Inserts a row into a table using the values stored by this object.
     *
     * @param context The context to use.
     */
    public Uri insert(Context context) {
        return context.getContentResolver().insert(uri(), values());
    }
}
