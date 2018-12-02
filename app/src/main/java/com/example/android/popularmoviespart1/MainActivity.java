package com.example.android.popularmoviespart1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.app.LoaderManager;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements android.app.LoaderManager.LoaderCallbacks<List<Movie>> {
    //step 1: setup variables
    public static final int MOVIE_LOADER_ID = 1;
    public static String API_KEY = "82b02bb8cddb7503cf4418f18effcb61";
    public static final String DYNAMIC_MOVIES_URL = "http://api.themoviedb.org/3/movie";
    public static final String API_PARAM = "api_key";
    private ProgressBar mProgressBar;
    public static final String LOG_TAG = MainActivity.class.getName();
    private MovieAdapter mAdapter;
    private TextView mEmptyStateTextView;
    private RecyclerView recyclerView;
    private LoaderManager loaderManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final ConnectivityManager connectivityManager;
        NetworkInfo networkInfo;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressBar = findViewById(R.id.progressBar);
        mEmptyStateTextView = findViewById(R.id.empty);

        int numOfColumns = 2;
        recyclerView = findViewById(R.id.recycle);
        recyclerView.setLayoutManager(new GridLayoutManager(this, numOfColumns));
        recyclerView.setHasFixedSize(true);
        mAdapter = new MovieAdapter(this, new ArrayList<Movie>());

        recyclerView.setAdapter(mAdapter);


        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            loaderManager = getLoaderManager();
            loaderManager.restartLoader(MOVIE_LOADER_ID, null, MainActivity.this);
        } else {
            mProgressBar.setVisibility(View.GONE);
            mEmptyStateTextView.setVisibility(View.VISIBLE);
            mEmptyStateTextView.setText(R.string.no_internet);

        }

    }

    @Override
    public Loader<List<Movie>> onCreateLoader(int id, final Bundle args) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String orderBy = sharedPreferences.getString(getString(R.string.settings_order_by_key), getString(R.string.settings_order_by_popular));

        Uri.Builder uriBuilder = Uri.parse(DYNAMIC_MOVIES_URL)
                .buildUpon()
                .appendPath(orderBy)
                .appendQueryParameter(API_PARAM, API_KEY);
        Log.e(LOG_TAG, "**************URL = " + uriBuilder.toString());
        return new MovieLoader(this, uriBuilder.toString());


    }

    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> data) {
        //mAdapter.clear();
        if (data != null && !data.isEmpty()) {
            mAdapter.addAllMovies(data);
            mProgressBar.setVisibility(View.GONE);
        } else {
            mProgressBar.setVisibility(View.GONE);
            mEmptyStateTextView.setVisibility(View.VISIBLE);
            mEmptyStateTextView.setText("Currently there are no movies");
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Movie>> loader) {
        mAdapter.clear();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
