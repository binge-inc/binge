package binge.util;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class HTMLDownloader {
    public static String getHTML(final String fullUrl, final String charset, final boolean debug) {
        URL url;
        if (debug) {
            System.out.println("HTMLDownloader.getHTML(String): Checking url " + fullUrl + "...");
        }
        try {
            url = new URL(fullUrl);
        } catch (final MalformedURLException e) {
            throw new RuntimeException(e);
        }
        URLConnection con;
        if (debug) {
            System.out.println("HTMLDownloader.getHTML(String): Connecting...");
        }
        try {
            con = url.openConnection();
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
        if (con.getContentType() == null) {
            System.err.println("Something went wrong. We probably got a 403 or some other non-good server response.");
            return null;
        }
        if (debug) {
            System.out.println("HTMLDownloader.getHTML(String): Downloading HTML...");
        }
        try {
            return IOUtils.toString(con.getInputStream(), charset);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }
}
