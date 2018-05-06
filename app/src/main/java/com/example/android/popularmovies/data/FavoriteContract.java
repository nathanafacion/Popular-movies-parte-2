package com.example.android.popularmovies.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class FavoriteContract {



    public static final class FavoriteEntry implements BaseColumns {

        public static final String CONTENT_AUTHORITY = "com.example.android.popularmovies";
        public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
        public static final String PATH_FAVORITE = "favorite";

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_FAVORITE)
                .build();

        /* Used internally as the name of our weather table. */
        public static final String TABLE_NAME = "favorites";

        public static final String COLUMN_ID_MOVIE = "id_movie";
        public static final String COLUMN_URL_IMAGE = "url_image";


        public static Uri buildWeatherUriWithDate(long date) {
            return CONTENT_URI.buildUpon()
                    .appendPath(Long.toString(date))
                    .build();
        }

    }
}

