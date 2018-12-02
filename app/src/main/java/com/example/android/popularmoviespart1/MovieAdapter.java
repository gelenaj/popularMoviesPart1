package com.example.android.popularmoviespart1;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.popularmoviespart1.data.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private ArrayList<Movie> mMovie;
    private MainActivity mContext;
    public static final String LOG_TAG = MovieAdapter.class.getSimpleName();

    MovieAdapter(MainActivity mContext, ArrayList<Movie> mMovie) {
        this.mContext = mContext;
        this.mMovie = mMovie;
    }


    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.ViewHolder holder, final int position) {
        Picasso.with(mContext)
                .load(NetworkUtils.makeReuestForPoster(mMovie.get(holder.getAdapterPosition()).getmPoster()))
                .into(holder.img);
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra(Movie.class.getSimpleName(), mMovie.get(position));
                mContext.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mMovie.size();
    }

    void clear() {
        mMovie.clear();
        notifyDataSetChanged();
    }

    void addAllMovies(List<Movie> movies) {
        mMovie.addAll(movies);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;


        public ViewHolder(View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.poster);

        }

    }


}
