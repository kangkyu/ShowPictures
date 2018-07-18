package com.example.android.showpictures.utilities;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    private static final String PIXABAY_BASE_URL = "https://pixabay.com/api";
    private static final String pixabayApiKey = System.getenv("FIXABAY_API_KEY"); // TODO: this does NOT work!

    public static URL buildUrl(String pageNumber) {
        URL url;
        Uri builtUri;

        builtUri = Uri.parse(PIXABAY_BASE_URL).buildUpon()
                .appendQueryParameter("key", pixabayApiKey)
                .appendQueryParameter("editors_choice", "true")
                .appendQueryParameter("page", pageNumber)
                .build();

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
