package com.example.android.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovies.utils.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends ArrayAdapter<Movie> {
    private static final String TAG = MovieAdapter.class.getSimpleName();
    private List<Movie> mMovieData;
    private ImageView posterView;
    private  TextView idView;

    public MovieAdapter(@NonNull Context context, @NonNull List<Movie> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Gets the AndroidFlavor object from the ArrayAdapter at the appropriate position
        final Movie movie = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.number_list_movie, parent, false);
        }

        posterView = (ImageView) convertView.findViewById(R.id.movie_image);
        Picasso.with(getContext()).load("http://image.tmdb.org/t/p/w185/"+movie.getPath_image()).into(posterView);

        posterView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Class destinationClass = DetailMovieActivity.class;
                Intent  intentMovie = new Intent(v.getContext(),destinationClass);
                intentMovie.putExtra("movie",movie);
                v.getContext().startActivity(intentMovie);

            }
        });
        TextView idView = (TextView) convertView.findViewById(R.id.movie_id_text);
        idView.setText(String.valueOf(movie.getId()));

        return convertView;
    }

    public void setMovieData(List<Movie> movieData) {
        mMovieData = movieData;
        notifyDataSetChanged();
    }
}