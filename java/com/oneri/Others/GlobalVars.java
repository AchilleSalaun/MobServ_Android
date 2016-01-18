package com.oneri.Others;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.preference.PreferenceManager;
import android.view.Display;
import android.view.WindowManager;

import com.crashlytics.android.Crashlytics;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.oneri.API.APIEndpointInterface;
import com.oneri.R;

import java.util.ArrayList;
import java.util.HashMap;

import io.fabric.sdk.android.Fabric;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by quentinleroy on 28/11/15.
 */

/**
 * Pour initialiser quelques variables globales
 * Comme par exemple :
 * l'url de notre webservice
 * retrofit
 * et des correspondance entre le type de contenu et quelques variables (couleur,
 * endpoint du servlet, ...)
 * d'autres au besoin dans le futur
 */

public class GlobalVars extends Application {

    public static SharedPreferences PREFERENCES;

    public static boolean SERVLET_ACHILLE_CHECKED = true;

    public static HashMap<String, Integer> CONTENTTYPE_TO_COLOR;

    public static Integer SEARCH_BAR_WIDTH;

    public static Integer SCREEN_WIDTH;
    public static Integer SCREEN_HEIGHT;

    public static boolean DEBUG_TOAST = false;

    public static Context APP_CONTEXT;

    public static final String SAVE_RELATION_SERVLET_LIKES = "likes";
    public static final String SAVE_RELATION_SERVLET_WAITING = "waiting";
    public static final String SAVE_RELATION_SERVLET_DOESNT_LIKE = "doesn't like";

    public static String EMAIL_CURRENT_USER = "kevin@gmail.com";

    public static ArrayList<String> CONTENT_LIST_FLAG_SERVLET;

    public static ArrayList<Integer> CONTENT_LIST_FLAG_DRAWABLE;

    public static ArrayList<Integer> CONTENT_LIST_FLAG_COLOR;

    public static ArrayList<String> CONTENT_LIST_FLAG;

    /*** Retrofit  ***/
    public static Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            .disableHtmlEscaping()
            .create();

    public static String BASE_URL = "http://oneri-1099.appspot.com";

    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    public static APIEndpointInterface apiService =
            retrofit.create(APIEndpointInterface.class);
    /*** Retrofit ***/

    @Override
    public void onCreate(){
        super.onCreate();

        Fabric.with(this, new Crashlytics());

        PREFERENCES = PreferenceManager.getDefaultSharedPreferences(this);


        SERVLET_ACHILLE_CHECKED = true;


        APP_CONTEXT = getApplicationContext();

        WindowManager wm = (WindowManager) APP_CONTEXT.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        SCREEN_WIDTH = width;
        SCREEN_HEIGHT = height;

        SEARCH_BAR_WIDTH = SCREEN_WIDTH * 2/3;




        CONTENT_LIST_FLAG = new ArrayList<String>();
        CONTENT_LIST_FLAG.add("Movies");
        CONTENT_LIST_FLAG.add("Books");
        CONTENT_LIST_FLAG.add("Video Games");
        CONTENT_LIST_FLAG.add("Comics");
        CONTENT_LIST_FLAG.add("Series");
        CONTENT_LIST_FLAG.add("Music");
        CONTENT_LIST_FLAG_SERVLET = new ArrayList<>();
        CONTENT_LIST_FLAG_SERVLET.add("movie");
        CONTENT_LIST_FLAG_SERVLET.add("book");
        CONTENT_LIST_FLAG_SERVLET.add("video game");
        CONTENT_LIST_FLAG_SERVLET.add("comic");
        CONTENT_LIST_FLAG_SERVLET.add("series");
        CONTENT_LIST_FLAG_SERVLET.add("music");
        CONTENT_LIST_FLAG_DRAWABLE = new ArrayList<>();
        CONTENT_LIST_FLAG_DRAWABLE.add(R.drawable.rect_movie);
        CONTENT_LIST_FLAG_DRAWABLE.add(R.drawable.rect_book);
        CONTENT_LIST_FLAG_DRAWABLE.add(R.drawable.rect_videogame);
        CONTENT_LIST_FLAG_DRAWABLE.add(R.drawable.rect_comic);
        CONTENT_LIST_FLAG_DRAWABLE.add(R.drawable.rect_tvshow);
        CONTENT_LIST_FLAG_DRAWABLE.add(R.drawable.rect_music);
        CONTENT_LIST_FLAG_COLOR = new ArrayList<>();
        CONTENT_LIST_FLAG_COLOR.add(R.color.movieColor);
        CONTENT_LIST_FLAG_COLOR.add(R.color.bookColor);
        CONTENT_LIST_FLAG_COLOR.add(R.color.videoGameColor);
        CONTENT_LIST_FLAG_COLOR.add(R.color.comicColor);
        CONTENT_LIST_FLAG_COLOR.add(R.color.tvshowColor);
        CONTENT_LIST_FLAG_COLOR.add(R.color.musicColor);

        CONTENTTYPE_TO_COLOR = new HashMap<>(6);
        CONTENTTYPE_TO_COLOR.put("movie", CONTENT_LIST_FLAG_COLOR.get(0));
        CONTENTTYPE_TO_COLOR.put("book", CONTENT_LIST_FLAG_COLOR.get(1));
        CONTENTTYPE_TO_COLOR.put("video game", CONTENT_LIST_FLAG_COLOR.get(2));
        CONTENTTYPE_TO_COLOR.put("comic", CONTENT_LIST_FLAG_COLOR.get(3));
        CONTENTTYPE_TO_COLOR.put("series", CONTENT_LIST_FLAG_COLOR.get(4));
        CONTENTTYPE_TO_COLOR.put("music", CONTENT_LIST_FLAG_COLOR.get(5));
    }
}
