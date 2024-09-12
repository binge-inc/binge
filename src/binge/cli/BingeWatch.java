package binge.cli;

import binge.jsonmodel.Series;
import binge.util.SeriesFunctions;
import com.google.gson.Gson;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static binge.Config.BINGE_DIR;
import static binge.Config.DEFAULT_SERIES_JSON_DIRECTORY;

public class BingeWatch {
    private boolean onlyHelp;
    private String ip;
    private String watchProtocol;
    private String[] args;
    private String jsonSaveDir;
    private String seriesId;
    private boolean forceUpdate;
    private String jsonFileEnding;
    private String fullSeriesJsonPath;

    public BingeWatch(final String[] args) {
        onlyHelp = false;
        if (args.length == 1) {
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
        String seriesJsonRepoDir = "json";
        jsonFileEnding = ".json";
        fullSeriesJsonPath = repoProtocol + "://" + jsonRepo + "/" + seriesJsonRepoDir + "/" + seriesId + jsonFileEnding;
        jsonSaveDir = System.getProperty("user.home") + "/" + BINGE_DIR + "/" + DEFAULT_SERIES_JSON_DIRECTORY;
    }

    public void bingeWatch() {
        if (onlyHelp) return;
        File file = new File(jsonSaveDir + "/" + seriesId + jsonFileEnding);
        String seriesJson = SeriesFunctions.loadSeriesJson(file, fullSeriesJsonPath, forceUpdate);
        if (seriesJson == null) return;
        Series series = new Gson().fromJson(seriesJson, Series.class);
        // ToDo
        SeriesFunctions.listAllEpisodeLinks(series, watchProtocol, ip);
        String url;
        if (SeriesFunctions.hasMovies(series)) {
            url = watchProtocol + "://" + ip + "/redirect/" + series.getSeasons()[1].getEpisodes()[0].getVersions()[0].getStreams()[0].getRedirect();
        } else {
            url = watchProtocol + "://" + ip + "/redirect/" + series.getSeasons()[0].getEpisodes()[0].getVersions()[0].getStreams()[0].getRedirect();
        }
        // ToDo End
        openWebBrowser(url);
        System.out.println("\n" +
                "Press Enter/Return to exit.");
        try {
            int ignored = System.in.read();
        } catch (final IOException e) {
            return;
        }
    }

    /**
     * Opens the default web browser with the specified URL.
     *
     * @param url any URL as a String.
     */
    private void openWebBrowser(final String url) {
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
            System.err.println("BingeWatch.openWebBrowser(String): Both attempts to open the web browser failed with a SecurityException.");
        } catch (final IOException e) {
            System.err.println("BingeWatch.openWebBrowser(String): IO exception.");
        }
    }

    private void printUsage() {
        System.out.println("Usage help for binge watch:");
        // ToDo
    }
}
