package com.example.android.popularmovies;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.android.popularmovies.utils.Movie;

public class MainActivity extends AppCompatActivity {

    //private RecyclerView recyclerView;
    private static final String TAG = MainActivity.class.getSimpleName();
    private MovieAdapter movieAdapter;
    private int sizeList = 10;
    public final static String FILTER_POPULAR = "popular";
    public final static String FILTER_RATING= "top_rated";
    public final static String SHOW_FAVORITE= "favorites";
    private static String FILTER = FILTER_POPULAR;
    public static Context mainContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainContext = getApplicationContext();
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.popcorn1);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_sortByPopular) {
            Log.v(TAG,"FILTER_POPULAE " + id);
            FILTER = FILTER_POPULAR;
            MainActivityFragment.loadMoviesData(FILTER);
            return true;
        }
        if (id == R.id.action_sortByRating) {
            Log.v(TAG,"FILTER_RATING " + id);
            FILTER = FILTER_RATING;
            MainActivityFragment.loadMoviesData(FILTER);
            return true;
        }

        if (id == R.id.action_showFavorite) {
            Log.v(TAG,"SHOW_FAVORITE " + id);
            FILTER = SHOW_FAVORITE;
            MainActivityFragment.loadMoviesData(FILTER);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static String getFilter(){
        return FILTER;
    }


}
