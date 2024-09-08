package binge.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;

public class HTMLDownloader {
    public static String getHTML(final String fullUrl, final boolean debug) {
        if (debug) {
            System.out.println("HTMLDownloader.getHTML(String): Checking url " + fullUrl + "...");
        }
        URL url;
        try {
            url = new URL(fullUrl);
        } catch (final MalformedURLException e) {
            throw new RuntimeException(e);
        }
        if (debug) {
            System.out.println("HTMLDownloader.getHTML(String): Connecting...");
        }
        URLConnection con;
        try {
            con = url.openConnection();
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
        Pattern p = Pattern.compile("text/html;\\s+charset=([^\\s]+)\\s*");
        if (con.getContentType() == null) {
            System.err.println("Something went wrong. We probably got a 403 or some other non-good server response.");
            return null;
        } else {
            if (debug) {
                System.out.println("HTMLDownloader.getHTML(String): Matching charset...");
            }
            Matcher m = p.matcher(con.getContentType());
            String charset = m.matches() ? m.group(1) : "ISO-8859-1";
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
}
