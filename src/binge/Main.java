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
            } else if (args[0].equalsIgnoreCase("browse")) {
                BingeBrowse bb = new BingeBrowse(args);
                bb.bingeBrowse();
            } else if (args[0].equalsIgnoreCase("config")) {
                BingeConfig bc = new BingeConfig(args);
                bc.bingeConfig();
            } else if (args[0].equalsIgnoreCase("download")) {
                BingeDownload bd = new BingeDownload(args);
                bd.bingeDownload();
            } else if (args[0].equalsIgnoreCase("find")) {
                BingeFind bf = new BingeFind(args);
                bf.bingeFind();
            } else if (args[0].equalsIgnoreCase("watch")) {
                BingeWatch bw = new BingeWatch(args);
                bw.bingeWatch();
            } else {
                System.out.println("Unknown binge command: " + args[0] + "\n" +
                        "For the full command list try \"binge --help\".");
            }
        }
    }
}