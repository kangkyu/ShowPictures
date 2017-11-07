package com.example.android.showpictures;

import android.content.res.Configuration;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.android.showpictures.utilities.NetworkUtils;
import com.example.android.showpictures.utilities.PixabayJsonUtils;

import java.net.URL;


public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private PhotosAdapter mPhotosAdapter;
    private StaggeredGridLayoutManager mLayoutManager;
    private ProgressBar mLoadingIndicator;

    private static final int PAGE_START = 1;
    private static final int TOTAL_PAGES = 20;

    private int currentPage = PAGE_START;

    private boolean isLoading = true;
    private int pastVisibleItems, visibleItemCount, totalItemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_photo);

        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        } else {
            mLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        }
        mPhotosAdapter = new PhotosAdapter();

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mPhotosAdapter);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0 && currentPage < TOTAL_PAGES) {
                    int[] firstVisibleItems = null;
                    firstVisibleItems = mLayoutManager.findFirstVisibleItemPositions(firstVisibleItems);

                    if (firstVisibleItems != null && firstVisibleItems.length > 0) {
                        pastVisibleItems = firstVisibleItems[0];
                    }
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                        if (!isLoading) {

                            isLoading = true;

                            currentPage += 1;
                            loadPhotoData();
                        }
                    } else {
                        isLoading = false;
                    }
                }
                if (dy < 0 && currentPage > PAGE_START) {
                    int[] lastVisibleItems = null;
                    lastVisibleItems = mLayoutManager.findLastVisibleItemPositions(lastVisibleItems);

                    if (lastVisibleItems != null && lastVisibleItems.length > 0) {
                        pastVisibleItems = lastVisibleItems[0];
                    }
                    visibleItemCount = mLayoutManager.getChildCount();
                    if ((pastVisibleItems - visibleItemCount) <= 0) {
                        if (!isLoading) {
                            isLoading = true;

                            currentPage -= 1;
                            loadPhotoData();
                        }
                    } else {
                      isLoading = false;
                    }
                }

                // Log.i("Page number", String.valueOf(currentPage));
            }
        });

        loadPhotoData();
    }

    private void loadPhotoData() {
        new FetchPhotos().execute(String.valueOf(currentPage));
    }

    public class FetchPhotos extends AsyncTask<String, Void, String[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected String[] doInBackground(String... params) {
            if (params.length == 0) return null;
            String pageNumber = params[0];
            URL requestPhotosUrl = NetworkUtils.buildUrl(pageNumber);

            String[] jsonStringPhotoData;
            try {
                String jsonWeatherResponse = NetworkUtils.getResponseFromHttpUrl(requestPhotosUrl);
                jsonStringPhotoData = PixabayJsonUtils.getImageLinkFromJson(jsonWeatherResponse);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            return jsonStringPhotoData;
        }

        @Override
        protected void onPostExecute(String[] photoData) {

            mLoadingIndicator.setVisibility(View.INVISIBLE);

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
