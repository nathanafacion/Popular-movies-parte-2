package com.example.android.popularmovies.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.example.android.popularmovies.data.FavoriteContract;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NetworkUtils {
    private static final String TAG = NetworkUtils.class.getSimpleName();
    private static final String UrlBase = "https://api.themoviedb.org/3/movie/";
    //sort_by
    private static final String sortBy = "sort_by";
    //api_key
    private static final String api_key = "api_key";
    private static final String key = "";
    private static final String language = "language";
    private static final String language_br = "pt-BR";

    public static ArrayList<String> getMovieTrailers(int movieId) {
        Uri buildUri = Uri.parse(UrlBase).buildUpon()
                .appendPath(String.valueOf(movieId))
                .appendPath("videos")
                .appendQueryParameter(api_key, key)
                .build();
        ArrayList<String> trailersId = null;
        try {
            URL url = new URL(buildUri.toString());
            Log.v(TAG, "getMoviesList URL: " + url);
            String jsonMovieResponse = NetworkUtils.getResponseFromHttpURL(url);
            trailersId = MovieJson.getTrailersFromJson(jsonMovieResponse);
        } catch (FileNotFoundException e) {
            Log.v(TAG, " haven't reviews");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return trailersId;
    }

    public static List<Movie> getFavoriteList(Context context){
        ContentResolver contentResolver =  context.getContentResolver();
        Cursor c = contentResolver.query(
                FavoriteContract.FavoriteEntry.CONTENT_URI,
                null,
                null ,
                null,
                null);

        List<Movie> movies = new ArrayList<Movie>();
        if(c != null && c.getCount() > 0){
            c.moveToFirst();
            while (c.moveToNext()){
                int id = c.getInt(1);
                Movie movie = getMoviesDetailFavoriteList(id);
                movies.add(movie);
            }
        }
        return movies;
    }

    public static ArrayList<Review> getMovieReviews(int movieId) {
        Uri buildUri = Uri.parse(UrlBase).buildUpon()
                .appendPath(String.valueOf(movieId))
                .appendPath("reviews")
                .appendQueryParameter(api_key, key)
                .build();
        ArrayList<Review> allReviews = new ArrayList<Review>();
        try {
            URL url = new URL(buildUri.toString());
            Log.v(TAG, "getMoviesList URL: " + url);
            String jsonMovieResponse = NetworkUtils.getResponseFromHttpURL(url);

            allReviews = MovieJson.getReviewsFromJson(jsonMovieResponse);
        } catch (FileNotFoundException e) {
            Log.v(TAG, " haven't reviews");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return allReviews;
    }


    public static Movie getMoviesDetailFavoriteList(int id_movie) {
        Uri buildUri = Uri.parse(UrlBase).buildUpon().appendPath(String.valueOf(id_movie))
                .appendQueryParameter(api_key, key)
                .build();

        Movie jsonAllMovies = null;
        try {
            URL url = new URL(buildUri.toString());
            Log.v(TAG, "getMoviesList URL: " + url);
            String jsonMovieResponse = NetworkUtils.getResponseFromHttpURL(url);
            jsonAllMovies = MovieJson.getMovieFavoriteFromJson(jsonMovieResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonAllMovies;
    }


    public static List<Movie> getMoviesList(String filter) {
        Uri buildUri = Uri.parse(UrlBase + filter).buildUpon()
                .appendQueryParameter(api_key, key)
                .build();

        List<Movie> jsonAllMovies = null;
        try {
            URL url = new URL(buildUri.toString());
            Log.v(TAG, "getMoviesList URL: " + url);
            String jsonMovieResponse = NetworkUtils.getResponseFromHttpURL(url);
            jsonAllMovies = MovieJson.getMovieFromJson(jsonMovieResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonAllMovies;
    }

    public static String getResponseFromHttpURL(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        Log.v(TAG, "Connection: " + urlConnection.getResponseCode());
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            Log.v(TAG, "Has Input: " + hasInput);
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

}
