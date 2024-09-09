package binge.cli;

import binge.jsonmodel.*;
import binge.util.HTMLDownloader;
import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

import static binge.Config.BINGE_DIR;
import static binge.Config.DEFAULT_SERIES_JSON_DIRECTORY;

public class BingeWatch {
    public static void bingeWatch(final String[] args) {
        if (args.length == 1) {
            printUsage();
            return;
        }
        String[] passedArgs = new String[args.length - 1];
        System.arraycopy(args, 1, passedArgs, 0, passedArgs.length);
        String ip = CLI.getIP(passedArgs);
        String seriesId = CLI.getSeriesId(passedArgs);
        boolean forceUpdate = CLI.getForceUpdate(passedArgs);
        String repoProtocol = "https";
        String watchProtocol = "http";
        String jsonRepo = "raw.githubusercontent.com/binge-inc/sto-series-to-json/master";
        String seriesJsonRepoDir = "json";
        String jsonFileEnding = ".json";
        String fullSeriesJsonPath = repoProtocol + "://" + jsonRepo + "/" + seriesJsonRepoDir + "/" + seriesId + jsonFileEnding;
        String jsonSaveDir = System.getProperty("user.home") + "/" + BINGE_DIR + "/" + DEFAULT_SERIES_JSON_DIRECTORY;
        File file = new File(jsonSaveDir  + "/" + seriesId + jsonFileEnding);
        String seriesJson = loadSeriesJson(file, fullSeriesJsonPath, forceUpdate);
        if (seriesJson == null) return;
        Series series = new Gson().fromJson(seriesJson, Series.class);
        // ToDo
        
        // ToDo End
        System.out.println();
    }

    /**
     * Loads the series json to a String.
     * This can be either from the hard drive or from an online repository.
     * If no data exists on the hard drive a new version will be downloaded, no matter what state the flag forceUpdate has.
     * If forceUpdate is false and data is found on the hard drive, the data on the hard drive will be loaded.
     * If forceUpdate is true a new version will be downloaded and replace the json file on the hard drive if there was any.
     * If the data could not be loaded null is returned.
     *
     * @param seriesJsonFile a File object representing the location of the json file on the hard drive.
     * @param url            an online source to fetch the json from.
     * @param forceUpdate    whether a possibly existing copy of the json file on the hard drive is ignored or not.
     * @return the JSON String representing the queried series or null if it could not be loaded.
     */
    private static String loadSeriesJson(final File seriesJsonFile, final String url, final boolean forceUpdate) {
        String seriesJson;
        if (seriesJsonFile.exists() && !forceUpdate) {
            try {
                seriesJson = FileUtils.readFileToString(seriesJsonFile, "UTF-8");
            } catch (final IOException e) {
                System.err.println("BingeWatch.loadSeriesJson(File, String, boolean): Could not load series JSON from hard drive: " + seriesJsonFile.getAbsolutePath());
                return null;
            }
        } else {
            seriesJson = HTMLDownloader.getHTML(url, "UTF-8", false);
            if (seriesJson == null) {
                System.err.println("BingeWatch.loadSeriesJson(File, String, boolean): Could not download json from web resource " + url);
                return null;
            }
            try {
                FileUtils.write(seriesJsonFile, seriesJson + "\n", "UTF-8");
            } catch (final IOException e) {
                System.err.println("BingeWatch.loadSeriesJson(File, String, boolean): Could not write series JSON to file: " + seriesJsonFile.getAbsolutePath());
            }
        }
        return seriesJson;
    }

    private static void printUsage() {
        System.out.println("Usage help for binge watch:");
        // ToDo
    }
}
