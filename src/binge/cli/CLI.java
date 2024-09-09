package binge.cli;

import binge.Config;
import binge.util.IPHelper;
import binge.util.StringFunctions;

public class CLI {
    public static final String[] VERSION_CODES = {"--version", "-v", "/v"};
    public static final String[] HELP_CODES = {"--help", "-h", "/h"};

    /**
     * Returns whether progress should be shown on the command line.
     * The argument should be passed as "--show-progress=true" or "--show-progress=false".
     * If --show-progress is passed but with an invalid value this function will return false.
     * If --show-progress is not passed the function will return the value of Config.DEFAULT_SHOW_PROGRESS.
     *
     * @param args the String array that contains all program arguments.
     * @return whether progress should be shown on the command line or not.
     */
    public static boolean getShowProgress(final String[] args) {
        String pattern = "--show-progress=";
        if (hasStringThatStartsWith(args, pattern)) {
            int i = getArgumentThatStartsWith(args, pattern);
            if (i == -1) {
                System.err.println("CLI.getShowProgress(String[]): Argument not found");
            } else {
                return parseFlexBoolean(args[i].substring(args[i].indexOf(pattern) + pattern.length()));
            }
        }
        return Config.DEFAULT_SHOW_PROGRESS;
    }

    /**
     * Returns whether existing files on the hard drive should be ignored and overridden.
     * The argument should be passed as "--force-update".
     * If --force-update is not passed the function will return the value of Config.DEFAULT_FORCE_UPDATE.
     *
     * @param args the String array that contains all program arguments.
     * @return whether existing files on the hard drive should be ignored and overridden or not.
     */
    public static boolean getForceUpdate(final String[] args) {
        String pattern = "--force-update";
        if (StringFunctions.arrayContainsIgnoreCase(args, pattern)) {
            return true;
        }
        return Config.DEFAULT_FORCE_UPDATE;
    }

    /**
     * Returns the IPv4 address to use as the server to crawl.
     * This is either an argument "--ip=123.123.123.123" or, if not specified or invalid, the value of Config.DEFAULT_IP
     *
     * @param args the String array that contains all program arguments.
     * @return An IPv4 address.
     */
    public static String getIP(final String[] args) {
        String pattern = "--ip=";
        if (hasStringThatStartsWith(args, pattern)) {
            int i = getArgumentThatStartsWith(args, pattern);
            if (i == -1) {
                System.err.println("CLI.getIP(String[]): Argument not found");
            } else {
                if (IPHelper.checkValidIP(args[i])) {
                    return args[i];
                } else {
                    System.err.println("IPv4 address \"" + args[i] + "\" is invalid. Using fallback (\"" + Config.DEFAULT_IP + "\").");
                }
            }
        }
        return Config.DEFAULT_IP;
    }

    /**
     * Returns the location to save series to.
     * The argument should be passed as "--output=path/to/directory" without a trailing slash.
     * If --output is not passed the function will return the value of Config.DEFAULT_SERIES_JSON_DIRECTORY.
     *
     * @param args the String array that contains all program arguments.
     * @return the location to save series to.
     */
    public static String getSeriesOutputDirectory(final String[] args) {
        String pattern = "--output=";
        if (hasStringThatStartsWith(args, pattern)) {
            int i = getArgumentThatStartsWith(args, pattern);
            if (i == -1) {
                System.err.println("CLI.getSeriesOutputDirectory(String[]): Argument not found");
            } else {
                return args[i].substring(args[i].indexOf(pattern) + pattern.length());
            }
        }
        return Config.DEFAULT_SERIES_JSON_DIRECTORY;
    }

    /**
     * Returns true if any String in args[] starts with the String pattern or false if there is no such element.
     *
     * @param args the program arguments created by main(String[]).
     * @param pattern The first characters in the argument.
     * @return true if pattern is found, false if not.
     */
    private static boolean hasStringThatStartsWith(final String[] args, final String pattern) {
        boolean hasString = false;
        for (final String s : args) {
            if (s.startsWith(pattern)) {
                hasString = true;
                break;
            }
        }
        return hasString;
    }

    /**
     * Returns the index in args[] of the first String that starts with the specified String pattern or -1 if there is no such element.
     *
     * @param args the program arguments created by main(String[]).
     * @param pattern The first characters in the argument.
     * @return index in args[index] of found element or -1 if none is found.
     */
    private static int getArgumentThatStartsWith(final String[] args, final String pattern) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].startsWith(pattern)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Compares the specified String to a collection of representations for "true" and returns true if it matched one.
     * Returns false otherwise.
     *
     * @param s the String to check.
     * @return true if String is "true", "1", "yes", "y" or "on" or any capitalization of one of these strings. Else false.
     */
    private static boolean parseFlexBoolean(final String s) {
        return ((s != null) && (s.equalsIgnoreCase("true") || s.equals("1") || s.equalsIgnoreCase("yes") || s.equalsIgnoreCase("y") || s.equalsIgnoreCase("on")));
    }

    /**
     * Returns the seriesId from args.
     * The seriesId must be the only argument that does not contain the equals character ("=").
     * If no seriesId is found the function returns null.
     *
     * @param args the program arguments created by main(String[]).
     * @return seriesId if found, else null.
     */
    public static String getSeriesId(final String[] args) {
        for (final String s : args) {
            if (!s.contains("=")) return s;
        }
        return null;
    }
}
