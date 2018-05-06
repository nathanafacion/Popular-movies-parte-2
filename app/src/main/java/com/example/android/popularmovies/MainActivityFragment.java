package com.example.android.popularmovies;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.popularmovies.utils.Movie;
import com.example.android.popularmovies.utils.MovieJson;
import com.example.android.popularmovies.utils.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivityFragment extends Fragment {

    private static final String TAG = MainActivityFragment.class.getSimpleName();
    private static MovieAdapter movieAdapter;
    public static ProgressBar mLoadingIndicator;
    public static  GridView gridView;
    private static List<Movie> movieList = new ArrayList<Movie>();
    public static TextView emptyFavoriteView;

    public MainActivityFragment() {
        loadMoviesData(MainActivity.getFilter());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.content_main, container, false);

        movieAdapter = new MovieAdapter(getActivity(), movieList);

        gridView = (GridView) rootView.findViewById(R.id.movie_grid);
        gridView.setAdapter(movieAdapter);

        mLoadingIndicator = (ProgressBar) rootView.findViewById(R.id.pb_loading_indicator);

        emptyFavoriteView = (TextView) rootView.findViewById(R.id.tv_without_favorite);
        return rootView;
    }

    public static void loadMoviesData(String filter) {
        //showMoviesDataView();
        new FetchMoviesTask().execute(filter);
    }

    public static class FetchMoviesTask extends AsyncTask<String, Void, List<Movie>> {
        private final String TAG = FetchMoviesTask.class.getSimpleName();


        protected void onPostExecute(List<Movie> movieData) {
            if (mLoadingIndicator != null) {
                mLoadingIndicator.setVisibility(View.INVISIBLE);
            }
            Log.v(TAG,"onPostExecute: " +  movieData);
            if (movieData != null && movieData.size()> 0)
                movieAdapter.setMovieData(movieData);
            else {
                gridView.setVisibility(View.GONE);
                emptyFavoriteView.setVisibility(View.VISIBLE);
            }
        }

        protected void onPreExecute() {
            super.onPreExecute();
            if (mLoadingIndicator != null) {
                mLoadingIndicator.setVisibility(View.VISIBLE);
                emptyFavoriteView.setVisibility(View.GONE);
            }
        }

        protected List<Movie> doInBackground(String... params) {
            if(params.length == 0){
                return null;
            }
            Log.v(TAG,"Filter usado: " + params[0]);
            String filter = params[0];
            try {
                List<Movie> jsonAllMovies;
                movieList.clear();
                if (filter.equals(MainActivity.SHOW_FAVORITE)){
                    jsonAllMovies = NetworkUtils.getFavoriteList(MainActivity.mainContext);
                } else {
                    jsonAllMovies = NetworkUtils.getMoviesList(filter);
                }
                movieList.addAll(jsonAllMovies);
                return jsonAllMovies;
            } catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }
}