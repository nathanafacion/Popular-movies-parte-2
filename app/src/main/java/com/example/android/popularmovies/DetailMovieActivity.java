package com.example.android.popularmovies;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.popularmovies.data.FavoriteContract;
import com.example.android.popularmovies.data.FavoriteDbHelper;
import com.example.android.popularmovies.utils.Movie;
import com.example.android.popularmovies.utils.Review;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DetailMovieActivity extends AppCompatActivity {

    private static final String TAG = DetailMovieActivity.class.getSimpleName();
    private Boolean isFavorite = false;
    private static int id_movie;
    private static String url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        Intent intentDetailItem = getIntent();

        TextView title = findViewById(R.id.tv_title_detail);
        TextView overview = findViewById(R.id.tv_overview_detail);
        TextView release = findViewById(R.id.tv_release_detail);
        TextView average = findViewById(R.id.tv_average_detail);
        ImageView poster = findViewById(R.id.iv_poster_detail);
        ImageButton favorite = findViewById(R.id.ib_favorite);
        if (intentDetailItem != null) {
            Movie movie = intentDetailItem.getExtras().getParcelable("movie");
            id_movie = movie.getId();
            url = movie.getPath_image();

            title.setText(movie.getOriginal_title());
            overview.setText(movie.getOverview());
            release.setText( getString(R.string.release)+ ": " + AlterDate(movie.getRelease_date()));
            average.setText( getString(R.string.average)+ ": " + String.valueOf(movie.getVote_average()));
            Picasso.with(this).load("http://image.tmdb.org/t/p/w780/" + movie.getBackdrop_path()).into(poster);

            ArrayList<Review> reviews = movie.getReviews();
            LinearLayout detailLayoutVideo = findViewById(R.id.ll_video);
            ArrayList<String> videos  = movie.getTrailers();

            isFavorite = checkISFavorite();
            if(isFavorite) {
                favorite.setBackground(getDrawable(R.drawable.icons8_star_filled_24));
            } else {
                favorite.setBackground(getDrawable(R.drawable.icons8_star_24));
            }

            int cont = 1;
            for(final String IDvideo : videos) {
                View reviewLayout = LayoutInflater.from(this).inflate(R.layout.number_list_video, detailLayoutVideo, false);
                TextView idView = reviewLayout.findViewById(R.id.tv_id);
                idView.setText(getString(R.string.trailer) +" "+ cont);
                reviewLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String url = "https://www.youtube.com/watch?v=" + IDvideo;
                        Intent  intentMovie = new Intent(Intent.ACTION_VIEW);
                        intentMovie.setData(Uri.parse(url));
                        v.getContext().startActivity(intentMovie);

                    }
                });
                detailLayoutVideo.addView(reviewLayout);

                cont++;
            }

            LinearLayout detailLayoutReviews = findViewById(R.id.ll_review);

            for(Review review : reviews) {
                View reviewLayout = LayoutInflater.from(this).inflate(R.layout.number_list_review, detailLayoutReviews, false);

                String content = String.valueOf(review.getContent());
                String author = review.getAuthor();

                TextView contentView = reviewLayout.findViewById(R.id.tv_content);
                TextView authorView = reviewLayout.findViewById(R.id.tv_author);
                authorView.setText(author + ": ");
                contentView.setText(content);
                detailLayoutReviews.addView(reviewLayout);
            }

//            reviewsList.setAdapter(new ReviewAdapter(this, reviews));

            Log.v(TAG, " Detail id: " + movie.getId());
            Log.v(TAG, " Detail title: " + movie.getOriginal_title());
            Log.v(TAG, " Detail overview: " + movie.getOverview());
            Log.v(TAG, " Detail release: " + movie.getRelease_date());
            Log.v(TAG, " Detail average: " + movie.getVote_average());
        }

    }

    public String AlterDate( String date){
        String newRelease[] = new String[3];
        newRelease = date.split("-");
        return newRelease[2]+"/"+newRelease[1] + "/" + newRelease[0];
    }

    public void alterFavorite(View view){
        ContentResolver contentResolver = view.getContext().getContentResolver();
        ContentValues cv = new ContentValues();
        cv.put( FavoriteContract.FavoriteEntry.COLUMN_ID_MOVIE,id_movie);
        cv.put( FavoriteContract.FavoriteEntry.COLUMN_URL_IMAGE,url);
        if(!isFavorite) {
            view.setBackground(getDrawable(R.drawable.icons8_star_filled_24));
            contentResolver.insert(
                    FavoriteContract.FavoriteEntry.CONTENT_URI, cv);
        } else {
            view.setBackground(getDrawable(R.drawable.icons8_star_24));
            contentResolver.delete(
                    FavoriteContract.FavoriteEntry.CONTENT_URI,
                    FavoriteContract.FavoriteEntry.COLUMN_ID_MOVIE + " = ? " ,
                    new String[]{String.valueOf(id_movie)});

        }
        isFavorite = !isFavorite;
        Log.e(TAG,"alterFavorite");
    }

    public boolean checkISFavorite(){
        ContentResolver contentResolver = this.getContentResolver();
        Cursor c = contentResolver.query(
                FavoriteContract.FavoriteEntry.CONTENT_URI,
                new String[] {FavoriteContract.FavoriteEntry.COLUMN_ID_MOVIE},
                FavoriteContract.FavoriteEntry.COLUMN_ID_MOVIE + " = ? " ,
                new String[]{String.valueOf(id_movie)},
                null);
        if(c!=null && c.getCount() > 0){
            isFavorite = true;
        }
        return isFavorite;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

}