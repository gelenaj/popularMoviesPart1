package com.example.android.popularmoviespart1;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.ShareCompat;
import android.widget.ImageView;

import java.util.Date;

public class Movie implements Parcelable {
    private String mTitle;
    private String mPoster;
    private String mSynopsis;
    private String mRating;
    private String mReleaseDate;

    public Movie(String title, String poster, String synopsis, String rating, String releaseDate) {
        mTitle = title;
        mPoster = poster;
        mSynopsis = synopsis;
        mRating = rating;
        mReleaseDate = releaseDate;
    }

    protected Movie(Parcel in) {
        mTitle = in.readString();
        mPoster = in.readString();
        mSynopsis = in.readString();
        mRating = in.readString();
        mReleaseDate = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getmTitle() {
        return mTitle;
    }

    public String getmPoster() {
        return mPoster;
    }

    public String getmSynopsis() {
        return mSynopsis;
    }

    public String getmRating() {
        return mRating;
    }

    public String getmReleaseDate() {
        return mReleaseDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeString(mPoster);
        dest.writeString(mSynopsis);
        dest.writeString(mRating);
        dest.writeString(mReleaseDate);
    }
}
