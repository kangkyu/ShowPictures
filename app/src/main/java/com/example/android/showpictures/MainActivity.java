package com.example.android.showpictures;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.android.showpictures.utilities.NetworkUtils;
import com.example.android.showpictures.utilities.PixabayJsonUtils;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView mPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPhoto = (TextView) findViewById(R.id.tv_photo_data);
        loadPhotoData();
    }

    private void loadPhotoData() {
        String searchTerm = "penguin"; // TODO: (1) use input value
        new FetchPhotos().execute("");
    }

    public class FetchPhotos extends AsyncTask<String, Void, String[]> {

        @Override
        protected String[] doInBackground(String... params) {
            if (params.length == 0) return null;
            String searchTerm = params[0];
            URL requestPhotosUrl = NetworkUtils.buildUrl(searchTerm);

            String[] jsonStringPhotoData = {};
            try {
                String jsonWeatherResponse = NetworkUtils.getResponseFromHttpUrl(requestPhotosUrl);
                Context context = MainActivity.this;
                jsonStringPhotoData = PixabayJsonUtils.getImageLinkFromJson(jsonWeatherResponse); // TODO: parse JSON
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            return jsonStringPhotoData;
        }

        @Override
        protected void onPostExecute(String[] photoData) {
            if (photoData != null) {
                for (String photo : photoData)
                mPhoto.append(photo + "\n\n\n");
            }
        }
    }
}
