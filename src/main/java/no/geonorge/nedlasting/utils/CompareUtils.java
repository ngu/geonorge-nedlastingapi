package no.geonorge.nedlasting.utils;

public class CompareUtils {

    /**
     * Same as a.equals(b), but handles if a or/and b is null. Returns the given
     * nullEqualsNullValue if a *and* b is null.
     */
    public static boolean safeEquals(Object a, Object b, boolean nullEqualsNullValue) {
        if ((a == null) && (b == null)) {
            return nullEqualsNullValue;
        }
        if (a == null) {
            return false;
        }
        if (b == null) {
            return false;
        }
        return a.equals(b);
    }

}
