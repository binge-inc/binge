package binge.cli;

public class BingeWatch {
    public static void bingeWatch(final String[] args) {
        if (args.length == 1) {
            printUsage();
            return;
        }
        String ip = CLI.getIP(args);
        String seriesId = CLI.getSeriesId(args);
        String repoProtocol = "https";
        String watchProtocol = "http";
        String jsonRepo = "raw.githubusercontent.com/binge-inc/sto-series-to-json/master/json/";
        String jsonFileEnding = ".json";
        boolean showProgress = CLI.getShowProgress(args);
        String outputDirectory = CLI.getSeriesOutputDirectory(args);
        String fullJsonPath = repoProtocol + "://" + jsonRepo + seriesId + jsonFileEnding;
        // ToDo

        // ToDo End
        System.out.println("ok");
    }

    private static void printUsage() {
        System.out.println("Usage help for binge watch:");
        // ToDo
    }
}
