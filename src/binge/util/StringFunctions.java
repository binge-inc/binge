package binge.util;

public class StringFunctions {
    /**
     * Returns whether a String array "strings" contains a String "value" case-insensitive.
     *
     * @param strings   any String array.
     * @param value     a String to search for.
     * @return true if value is an entry in strings, else false.
     */
    public static boolean arrayContainsIgnoreCase(final String[] strings, final String value) {
        for (final String s : strings) {
            if (s.equalsIgnoreCase(value)) return true;
        }
        return false;
    }

    /**
     * Returns whether a String array "strings" contains any String of the String array "values" case-insensitive.
     *
     * @param strings   any String array.
     * @param values    a String array with entries to match for.
     * @return true if a String in values is an entry in strings, else false.
     */
    public static boolean arrayContainsIgnoreCaseAny(final String[] strings, final String[] values) {
        for (final String s : strings) {
            for (final String v : values) {
                if (s.equalsIgnoreCase(v)) return true;
            }
        }
        return false;
    }
}
