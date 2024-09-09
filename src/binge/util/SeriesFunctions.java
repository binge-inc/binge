package binge.util;

import binge.jsonmodel.Episode;
import binge.jsonmodel.Season;
import binge.jsonmodel.Series;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class SeriesFunctions {
    public static void listAllEpisodeLinks(final Series series, final String watchProtocol, final String ip) {
        for (int i = 0; i < series.getSeasons().length; i++) {
            Season season = series.getSeasons()[i];
            for (int j = 0; j < season.getEpisodes().length; j++) {
                Episode episode = season.getEpisodes()[j];
                String showTitle = episode.getName();
                if (showTitle == null || showTitle.isEmpty()) {
                    showTitle = episode.getAlt();
                }
                // ToDo: Implement version and hoster prioritization
                if (isMovieSeason(season)) {
                    System.out.println(sanitizeSeasonId(season.getSeasonId()) + " " + (j + 1) + ": " + showTitle + ": " + watchProtocol + "://" + ip + episode.getVersions()[0].getStreams()[0].getPath());
                } else {
                    System.out.println(season.getSeasonId() + " Folge " + (j + 1) + ": " + showTitle + ": " + watchProtocol + "://" + ip + episode.getVersions()[0].getStreams()[0].getPath());
                }
            }
        }
    }

    public static String sanitizeSeasonId(final String seasonId) {
        if (seasonId.equalsIgnoreCase("alle filme") || seasonId.equalsIgnoreCase("filme")) {
            return "Film";
        } else if (seasonId.equalsIgnoreCase("all movies") || seasonId.equalsIgnoreCase("movies")) {
            return "Movie";
        }
        return seasonId;
    }

    /**
     * Returns whether a series has a movies section or not.
     *
     * @param series any series.
     * @return true if the series has a movies section, else false.
     */
    public static boolean hasMovies(final Series series) {
        return isMovieSeason(series.getSeasons()[0]);
    }

    public static boolean isMovieSeason(final Season season) {
        String seasonId = season.getSeasonId();
        return seasonId.equalsIgnoreCase("filme") || seasonId.equalsIgnoreCase("alle filme") || seasonId.equalsIgnoreCase("movies") || seasonId.equalsIgnoreCase("all movies");
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
    public static String loadSeriesJson(final File seriesJsonFile, final String url, final boolean forceUpdate) {
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
}
