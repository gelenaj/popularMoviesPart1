package com.example.android.popularmoviespart1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmoviespart1.data.NetworkUtils;
import com.squareup.picasso.Picasso;


public class DetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ImageView imageView = (ImageView) findViewById(R.id.poster);
        TextView titleTv = (TextView) findViewById(R.id.title);
        TextView yearTv = (TextView) findViewById(R.id.year);
        TextView ratingTv = (TextView) findViewById(R.id.rating);
        TextView synopsisTv = (TextView) findViewById(R.id.synopsis);

        Movie currentMovie = getIntent().getExtras().getParcelable(Movie.class.getSimpleName());
        String requestForPoster = NetworkUtils.makeReuestForPoster(currentMovie.getmPoster());


        Picasso.with(this)
                .load(requestForPoster)
                .into(imageView);

        titleTv.setText(currentMovie.getmTitle());

        String newYear = currentMovie.getmReleaseDate().substring(0, 4);
        yearTv.setText(newYear);

        ratingTv.setText(currentMovie.getmRating() + getString(R.string.out_of_10));
        synopsisTv.setText(currentMovie.getmSynopsis());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
