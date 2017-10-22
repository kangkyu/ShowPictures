package com.example.android.showpictures;

import android.content.Context;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.android.showpictures.utilities.NetworkUtils;
import com.example.android.showpictures.utilities.PixabayJsonUtils;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private PhotosAdapter mPhotosAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_photo);
        StaggeredGridLayoutManager layoutManager;

        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        } else {
            layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        }
        mPhotosAdapter = new PhotosAdapter();

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mPhotosAdapter);

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
                jsonStringPhotoData = PixabayJsonUtils.getImageLinkFromJson(jsonWeatherResponse);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            return jsonStringPhotoData;
        }

        @Override
        protected void onPostExecute(String[] photoData) {
            if (photoData != null) {
                mPhotosAdapter.setImageData(photoData);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.photos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            mPhotosAdapter.setImageData(null);
            loadPhotoData();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
