package com.example.android.popularmoviespart1.data;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.example.android.popularmoviespart1.Movie;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static android.util.Log.e;

public final class NetworkUtils {

    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();
    private static final String THE_MOVIE_DB_IMAGE_REQUEST_URL = "https://image.tmdb.org/t/p/w185/";

    public static List<Movie> fetchMovieData(String requestURl) {
        URL url = createUrl(requestURl);

        String jsonResponse = makeHttpRequest(url);
        List<Movie> movies = extractFeatureFromJson(jsonResponse);
        return movies;

    }

    private static URL createUrl(String stringURL) {
        URL url = null;
        try {
            url = new URL(stringURL);
        } catch (MalformedURLException e) {
            e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }


    private static String makeHttpRequest(URL url) {
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving The Movie JSON results", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static java.util.List<Movie> extractFeatureFromJson(String movieJSON) {
        if (TextUtils.isEmpty(movieJSON)) {
            return null;
        }
        List<Movie> movies = new ArrayList<>();


        try {

            JSONObject baseJsonResponse = new JSONObject(movieJSON);
            if (baseJsonResponse.has("results")) {
                JSONArray movieArray = baseJsonResponse.getJSONArray("results");

                for (int i = 0; i < movieArray.length(); i++) {
                    JSONObject currentMovie = movieArray.getJSONObject(i);

                    String title = currentMovie.getString("title");
                    String poster = currentMovie.getString("poster_path").substring(1);
                    String synopsis = currentMovie.getString("overview");
                    String rating = currentMovie.getString("vote_average");
                    String releaseDate = currentMovie.getString("release_date");

                    Movie movie = new Movie(title, poster, synopsis, rating, releaseDate);
                    movies.add(movie);
                }
            } else {
                Log.i(LOG_TAG, "Did Not find JSON Object");
            }
        } catch (JSONException e) {
            e(LOG_TAG, "Problem parsing the movie JSON results", e);
        }
        return movies;
    }


    public static String makeReuestForPoster(String path) {
        Uri.Builder uriBuilder = Uri.parse(THE_MOVIE_DB_IMAGE_REQUEST_URL)
                .buildUpon()
                .appendPath(path);
        return uriBuilder.toString();
    }
}