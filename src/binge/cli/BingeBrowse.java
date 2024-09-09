package binge.cli;

import binge.util.StringFunctions;

import static binge.Config.BINGE_DIR;
import static binge.Config.DEFAULT_SERIES_JSON_DIRECTORY;

public class BingeBrowse {
    private boolean onlyHelp;
    private String ip;
    private String watchProtocol;
    private String[] args;
    private String jsonSaveDir;
    private String seriesId;
    private boolean forceUpdate;
    private String jsonFileEnding;
    private String fullIndexJsonPath;
    private String fullSeriesJsonPath;

    public BingeBrowse(final String[] args) {
        onlyHelp = false;
        if (args.length >= 2 && StringFunctions.arrayContainsIgnoreCaseAny(CLI.HELP_CODES, args)) {
            printUsage();
            onlyHelp = true;
            return;
        }
        this.args = new String[args.length - 1];
        System.arraycopy(args, 1, this.args, 0, this.args.length);
        ip = CLI.getIP(this.args);
        seriesId = CLI.getSeriesId(this.args);
        forceUpdate = CLI.getForceUpdate(this.args);
        String repoProtocol = "https";
        watchProtocol = "http";
        String jsonRepo = "raw.githubusercontent.com/binge-inc/sto-series-to-json/master";
        String indexJsonRepoDir = "list-json";
        String indexFileName = "series";
        String seriesJsonRepoDir = "json";
        jsonFileEnding = ".json";
        fullIndexJsonPath = repoProtocol + "://" + jsonRepo + "/" + indexJsonRepoDir + "/" + indexFileName + jsonFileEnding;
        if (seriesId != null) {
            fullSeriesJsonPath = repoProtocol + "://" + jsonRepo + "/" + seriesJsonRepoDir + "/" + seriesId + jsonFileEnding;
        } else {
            fullSeriesJsonPath = null;
        }
        jsonSaveDir = System.getProperty("user.home") + "/" + BINGE_DIR + "/" + DEFAULT_SERIES_JSON_DIRECTORY;
    }

    public void bingeBrowse() {
        if (onlyHelp) return;
        // ToDo
        System.out.println("ok");
    }

    private void printUsage() {
        System.out.println("Usage help for binge browse:\n" +
                "ToDo");
        // ToDo
    }
}
