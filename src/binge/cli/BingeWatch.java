package binge.cli;

import binge.jsonmodel.Series;
import binge.util.HTMLDownloader;
import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

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
        File file = new File(jsonSaveDir + "/" + seriesId + jsonFileEnding);
        String seriesJson = loadSeriesJson(file, fullSeriesJsonPath, forceUpdate);
        if (seriesJson == null) return;
        Series series = new Gson().fromJson(seriesJson, Series.class);
        // ToDo
        String url;
        if (hasMovies(series)) {
            url = watchProtocol + "://" + ip + series.getSeasons()[1].getEpisodes()[0].getVersions()[0].getStreams()[0].getPath();
        } else {
            url = watchProtocol + "://" + ip + series.getSeasons()[0].getEpisodes()[0].getVersions()[0].getStreams()[0].getPath();
        }
        // ToDo End
        openWebBrowser(url);
        System.out.println("ok");
    }

    /**
     * Returns whether a series has a movies section or not.
     *
     * @param series any series.
     * @return true if the series has a movies section, else false.
     */
    private static boolean hasMovies(final Series series) {
        String seasonId = series.getSeasons()[0].getSeasonId();
        return seasonId.equalsIgnoreCase("filme") ||
                seasonId.equalsIgnoreCase("alle filme") ||
                seasonId.equalsIgnoreCase("movies") ||
                seasonId.equalsIgnoreCase("all movies");
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

    /**
     * Opens the default web browser with the specified URL.
     *
     * @param url any URL as a String.
     */
    private static void openWebBrowser(final String url) {
        if (!Desktop.isDesktopSupported() || !Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            System.err.println("BingeWatch.openWebBrowser(String): Opening the web browser from within a Java application seems to be not supported on your operating system.");
            return;
        }
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (final URISyntaxException e) {
            System.err.println("BingeWatch.openWebBrowser(String): Malformed URI.");
        } catch (final NullPointerException e) {
            System.err.println("BingeWatch.openWebBrowser(String): uri may not be null.");
        } catch (final UnsupportedOperationException e) {
            System.err.println("BingeWatch.openWebBrowser(String): Unsupported operation.");
        } catch (final IllegalArgumentException e) {
            System.err.println("BingeWatch.openWebBrowser(String): URL could not be converted to URI.");
        } catch (final SecurityException e) {
            System.err.println("BingeWatch.openWebBrowser(String): Security exception.");
        } catch (final IOException e) {
            System.err.println("BingeWatch.openWebBrowser(String): IO exception.");
        }
    }

    private static void printUsage() {
        System.out.println("Usage help for binge watch:");
        // ToDo
    }
}
