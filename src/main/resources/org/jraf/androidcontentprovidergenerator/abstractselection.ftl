<#if header??>
${header}
</#if>
package ${config.providerPackage}.base;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class AbstractSelection <T extends AbstractSelection<?>> {
    private static final String EQ = "=?";
    private static final String PAREN_OPEN = "(";
    private static final String PAREN_CLOSE = ")";
    private static final String AND = " and ";
    private static final String OR = " or ";
    private static final String IS_NULL = " is null";
    private static final String IS_NOT_NULL = " is not null";
    private static final String IN = " in(";
    private static final String NOT_IN = " not in(";
    private static final String COMMA = ",";
    private static final String GT = ">?";
    private static final String LT = "<?";
    private static final String GT_EQ = ">=?";
    private static final String LT_EQ = "<=?";
    private static final String NOT_EQ = "<>?";

    private StringBuilder mSelection = new StringBuilder();
    private List<String> mSelectionArgs = new ArrayList<String>(5);

    protected void addEquals(String column, Object[] value) {
        mSelection.append(column);

        if (value == null) {
            // Single null value
            mSelection.append(IS_NULL);
        } else if (value.length > 1) {
            // Multiple values ('in' clause)
            mSelection.append(IN);
            for (int i = 0; i < value.length; i++) {
                mSelection.append("?");
                if (i < value.length - 1) {
                    mSelection.append(COMMA);
                }
                mSelectionArgs.add(valueOf(value[i]));
            }
            mSelection.append(PAREN_CLOSE);
        } else {
            // Single value
            if (value[0] == null) {
                mSelection.append(IS_NULL);
            } else {
                mSelection.append(EQ);
                mSelectionArgs.add(valueOf(value[0]));
            }
        }
    }

    protected void addNotEquals(String column, Object[] value) {
        mSelection.append(column);

        if (value == null) {
            // Single null value
            mSelection.append(IS_NOT_NULL);
        } else if (value.length > 1) {
            // Multiple values ('in' clause)
            mSelection.append(NOT_IN);
            for (int i = 0; i < value.length; i++) {
                mSelection.append("?");
                if (i < value.length - 1) {
                    mSelection.append(COMMA);
                }
                mSelectionArgs.add(valueOf(value[i]));
            }
            mSelection.append(PAREN_CLOSE);
        } else {
            // Single value
            mSelection.append(NOT_EQ);
            mSelectionArgs.add(valueOf(value[0]));
        }
    }

    protected void addGreaterThan(String column, Object value) {
        mSelection.append(column);
        mSelection.append(GT);
        mSelectionArgs.add(valueOf(value));
    }

    protected void addGreaterThanOrEquals(String column, Object value) {
        mSelection.append(column);
        mSelection.append(GT_EQ);
        mSelectionArgs.add(valueOf(value));
    }

    protected void addLessThan(String column, Object value) {
        mSelection.append(column);
        mSelection.append(LT);
        mSelectionArgs.add(valueOf(value));
    }

    protected void addLessThanOrEquals(String column, Object value) {
        mSelection.append(column);
        mSelection.append(LT_EQ);
        mSelectionArgs.add(valueOf(value));
    }

    private String valueOf(Object obj) {
        if (obj instanceof Date) {
            return String.valueOf(((Date) obj).getTime());
        } else if (obj instanceof Boolean) {
            return (Boolean) obj ? "1" : "0";
        }
        return String.valueOf(obj);
    }

    @SuppressWarnings("unchecked")
    public T openParen() {
        mSelection.append(PAREN_OPEN);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T closeParen() {
        mSelection.append(PAREN_CLOSE);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T and() {
        mSelection.append(AND);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T or() {
        mSelection.append(OR);
        return (T) this;
    }
    
    
    protected Object[] toObjectArray(int... array) {
        Object[] res = new Object[array.length];
        for (int i = 0; i < array.length; i++) {
            res[i] = array[i];
        }
        return res;
    }

    protected Object[] toObjectArray(long... array) {
        Object[] res = new Object[array.length];
        for (int i = 0; i < array.length; i++) {
            res[i] = array[i];
        }
        return res;
    }

    protected Object[] toObjectArray(float... array) {
        Object[] res = new Object[array.length];
        for (int i = 0; i < array.length; i++) {
            res[i] = array[i];
        }
        return res;
    }

    protected Object[] toObjectArray(double... array) {
        Object[] res = new Object[array.length];
        for (int i = 0; i < array.length; i++) {
            res[i] = array[i];
        }
        return res;
    }


    /**
     * Returns the selection produced by this object.
     */
    public String sel() {
        return mSelection.toString();
    }

    /**
     * Returns the selection arguments produced by this object.
     */
    public String[] args() {
        int size = mSelectionArgs.size();
        if (size == 0) return null;
        return mSelectionArgs.toArray(new String[size]);
    }
}