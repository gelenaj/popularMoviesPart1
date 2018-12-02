package com.example.android.popularmoviespart1;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.example.android.popularmoviespart1.data.NetworkUtils;

import java.util.List;

public class MovieLoader extends AsyncTaskLoader<List<Movie>> {

    private String mUrl;

    public MovieLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }


    @Override
    public List<Movie> loadInBackground() {
        return NetworkUtils.fetchMovieData(mUrl);

    }
}
