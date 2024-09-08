package binge.util;

public class StringFunctions {
    public static boolean arrayContainsIgnoreCase(final String[] strings, final String value) {
        for (final String s : strings) {
            if (s.equalsIgnoreCase(value)) return true;
        }
        return false;
    }
}
