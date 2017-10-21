package com.example.android.showpictures.utilities;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Scanner;

public class NetworkUtils {

    private static final String FIXABAY_BASE_URL = "https://pixabay.com/api";
    private static final String fixabayApiKey = System.getenv("FIXABAY_API_KEY");

    public static URL buildUrl(String searchTerm) {
        URL url;
        Uri builtUri;

        builtUri = Uri.parse(FIXABAY_BASE_URL).buildUpon()
                .appendQueryParameter("key", fixabayApiKey)
                .appendQueryParameter("editors_choice", "true").build(); // TODO: (2) use searchTerm instead

        url = null;

        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}