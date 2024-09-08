package binge.cli;

import binge.Build;

public class BingeHelp {
    public static void printHelpText() {
        System.out.println("binge " + Build.getFullVersionString() + " commands:\n" +
                "\n" +
                "For help on a specific command try \"binge COMMAND --help\"\n" +
                "\n" +
                "binge find     - Finds the seriesId of a specified series\n" +
                "binge watch    - Watch a series\n" +
                "binge download - Download a series");
    }
}
