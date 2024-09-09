package binge.util;

public class StringFunctions {
    /**
     * Returns whether a String array "strings" contains a String "value" case-insensitive.
     *
     * @param strings any String array.
     * @param value   a String to search for.
     * @return true if value is an entry in strings, else false.
     */
    public static boolean arrayContainsIgnoreCase(final String[] strings, final String value) {
        for (final String s : strings) {
            if (s.equalsIgnoreCase(value)) return true;
        }
        return false;
    }
}
