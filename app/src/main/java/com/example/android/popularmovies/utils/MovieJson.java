package com.example.android.popularmovies.utils;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MovieJson {

    private static final String TAG = MovieJson.class.getSimpleName();

    public static Movie getMovieFavoriteFromJson(String JsonStr)
            throws JSONException {
        final String id = "id";
        final String path_image = "poster_path";
        final String overview = "overview";
        final String vote_average = "vote_average";
        final String release = "release_date";
        final String original_title = "original_title";
        final String backdrop_path = "backdrop_path";
        JSONObject movieJson = new JSONObject(JsonStr);
        boolean validation = validationFavoriteJson(movieJson);
        Log.v(TAG, "Boolean json : " + validation);

        int movieId;
        String moviePath_image, movieRelease, movieOverview, movieOriginal_title, movieBackdrop_path;
        Double movieVote_average;
        ArrayList<String> movieTrailers;
        ArrayList<Review> movieReviews;

        movieRelease = movieJson.getString(release);
        Log.v(TAG, "MovieJson release: " + movieRelease);
        movieOriginal_title = movieJson.getString(original_title);
        Log.v(TAG, "MovieJson original_title: " + movieOriginal_title);
        movieOverview = movieJson.getString(overview);
        Log.v(TAG, "MovieJson overview: " + movieOverview);
        movieVote_average = movieJson.getDouble(vote_average);
        Log.v(TAG, "MovieJson vote_average: " + movieVote_average);
        movieId = movieJson.getInt(id);
        Log.v(TAG, "MovieJson id: " + movieId);
        moviePath_image = movieJson.getString(path_image);
        Log.v(TAG, "MovieJson path: " + moviePath_image);
        movieBackdrop_path = movieJson.getString(backdrop_path);

        movieTrailers = NetworkUtils.getMovieTrailers(movieId);
        movieReviews = NetworkUtils.getMovieReviews(movieId);
        Movie itemMovie = new Movie(movieId, moviePath_image, movieOriginal_title, movieOverview,
                movieVote_average, movieRelease, movieBackdrop_path, movieTrailers, movieReviews);



        return itemMovie;
}


    public static ArrayList<Movie> getMovieFromJson(String JsonStr)
            throws JSONException {
        final String id = "id";
        final String path_image = "poster_path";
        final String name_results = "results";
        final String overview = "overview";
        final String vote_average = "vote_average";
        final String release = "release_date";
        final String original_title = "original_title";
        final String backdrop_path = "backdrop_path";
        JSONObject moviesJson = new JSONObject(JsonStr);
        boolean validation = validationJson(moviesJson);
        Log.v(TAG, "Boolean json : " + validation);
        if (!validation) return null;

        JSONArray resultsMovie = moviesJson.getJSONArray(name_results);

        ArrayList<Movie> allMovies = new ArrayList<Movie>();

        int movieId;
        String moviePath_image, movieRelease, movieOverview, movieOriginal_title, movieBackdrop_path;
        Double movieVote_average;
        JSONObject movieJson;
        ArrayList<String> movieTrailers;
        ArrayList<Review> movieReviews;
        for (int i = 0; i < resultsMovie.length(); i++) {
            movieJson = resultsMovie.getJSONObject(i);
            movieRelease = movieJson.getString(release);
            Log.v(TAG, "MovieJson release: " + movieRelease);
            movieOriginal_title = movieJson.getString(original_title);
            Log.v(TAG, "MovieJson original_title: " + movieOriginal_title);
            movieOverview = movieJson.getString(overview);
            Log.v(TAG, "MovieJson overview: " + movieOverview);
            movieVote_average = movieJson.getDouble(vote_average);
            Log.v(TAG, "MovieJson vote_average: " + movieVote_average);
            movieId = movieJson.getInt(id);
            Log.v(TAG, "MovieJson id: " + movieId);
            moviePath_image = movieJson.getString(path_image);
            Log.v(TAG, "MovieJson path: " + moviePath_image);
            movieBackdrop_path = movieJson.getString(backdrop_path);

            movieTrailers = NetworkUtils.getMovieTrailers(movieId);
            movieReviews = NetworkUtils.getMovieReviews(movieId);
            Movie itemMovie = new Movie(movieId, moviePath_image, movieOriginal_title, movieOverview,
                    movieVote_average, movieRelease, movieBackdrop_path, movieTrailers, movieReviews);
            allMovies.add(itemMovie);
        }

        return allMovies;
    }

    public static boolean validationJson(JSONObject moviesJson) throws JSONException {
        final String OWM_MESSAGE_CODE = "page";

        int errorCode = moviesJson.getInt(OWM_MESSAGE_CODE);
        Log.v(TAG, "ErrorCode : " + errorCode);
        if (moviesJson.has(OWM_MESSAGE_CODE)) {
            return true;
        }
        return false;
    }

    public static boolean validationFavoriteJson(JSONObject moviesJson) throws JSONException {
        final String OWM_MESSAGE_CODE = "id";

        int errorCode = moviesJson.getInt(OWM_MESSAGE_CODE);
        Log.v(TAG, "ErrorCode : " + errorCode);
        if (moviesJson.has(OWM_MESSAGE_CODE)) {
            return true;
        }
        return false;
    }

    public static ArrayList<Review> getReviewsFromJson(String jsonMovieResponse) throws JSONException {
        final String review_author = "author";
        final String review_content = "content";
        final String review_url = "url";
        JSONObject moviesJson = new JSONObject(jsonMovieResponse);
        ArrayList<String> reviewsList = new ArrayList<>();
        JSONArray trailersJSONArray = moviesJson.getJSONArray("results");

        ArrayList<Review> allReviews = new ArrayList<Review>();
        JSONObject reviewObject;
        String reviewAuthor, reviewUrl, reviewContent;
        for (int i = 0; i < trailersJSONArray.length(); i++) {
            reviewObject = trailersJSONArray.getJSONObject(i);
            reviewAuthor = reviewObject.getString(review_author);
            Log.v(TAG, "reviewAuthor: " + reviewAuthor);
            reviewUrl = reviewObject.getString(review_url);
            Log.v(TAG, "reviewUrl: " + reviewUrl);
            reviewContent = reviewObject.getString(review_content);
            Log.v(TAG, "reviewContent: " + reviewContent);
            Review itemReview = new Review(reviewAuthor, reviewContent, reviewUrl);
            allReviews.add(itemReview);

        }
        return allReviews;
    }

    public static ArrayList<String> getTrailersFromJson(String JsonStr)
            throws JSONException {

        JSONObject moviesJson = new JSONObject(JsonStr);
        ArrayList<String> trailersList = new ArrayList<>();
        JSONObject trailerJson;
        JSONArray trailersJSONArray = moviesJson.getJSONArray("results");
        for (int i = 0; i < trailersJSONArray.length(); i++) {
            trailerJson = trailersJSONArray.getJSONObject(i);
            trailersList.add(trailerJson.getString("key"));
        }
        return trailersList;
    }
}
