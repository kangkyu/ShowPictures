package com.example.android.showpictures.utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class PixabayJsonUtils {
    public static String[] getImageLinkFromJson(String photosJsonStr) throws JSONException {
        String[] parsedImageData = null;

        JSONObject photosJson = new JSONObject(photosJsonStr);
        JSONArray imageArray = photosJson.getJSONArray("hits");

        parsedImageData = new String[imageArray.length()];

        for (int i = 0; i < imageArray.length(); i++) {
            String preview;

            JSONObject photoObject = imageArray.getJSONObject(i);
            preview = photoObject.getString("previewURL");

            parsedImageData[i] = preview;
        }

        return parsedImageData;
    }
}
