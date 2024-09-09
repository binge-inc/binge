package binge;

import binge.cli.*;
import binge.util.StringFunctions;

public class Main {
    public static void main(final String[] args) {
        if (args.length == 0) {
            System.out.println("binge " + Build.getFullVersionString() + " does not have a GUI yet.\n" +
                    "For usage help try \"binge --help\".");
        } else {
            if (StringFunctions.arrayContainsIgnoreCase(CLI.VERSION_CODES, args[0])) {
                System.out.println("binge " + Build.getFullVersionString());
            } else if (StringFunctions.arrayContainsIgnoreCase(CLI.HELP_CODES, args[0])) {
                BingeHelp.printHelpText();
            } else if (args[0].equalsIgnoreCase("download")) {
                BingeDownload.bingeDownload(args);
            } else if (args[0].equalsIgnoreCase("find")) {
                BingeFind.bingeFind(args);
            } else if (args[0].equalsIgnoreCase("watch")) {
                BingeWatch.bingeWatch(args);
            } else {
                System.out.println("Unknown binge command: " + args[0] + "\n" +
                        "For the full command list try \"binge --help\".");
            }
        }
    }
}