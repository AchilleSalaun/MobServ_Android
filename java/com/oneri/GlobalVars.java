package com.oneri;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.oneri.API.APIEndpointInterface;

import java.util.ArrayList;

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

    public static ArrayList<String> CONTENT_LIST_FLAG_SERVLET;

    public static ArrayList<Integer> CONTENT_LIST_FLAG_DRAWABLE;

    public static ArrayList<Integer> CONTENT_LIST_FLAG_COLOR;

    public static ArrayList<String> CONTENT_LIST_FLAG;

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

    @Override
    public void onCreate(){
        super.onCreate();
        CONTENT_LIST_FLAG = new ArrayList<String>();
        CONTENT_LIST_FLAG.add("Movies");
        CONTENT_LIST_FLAG.add("Books");
        CONTENT_LIST_FLAG.add("Video Games");
        CONTENT_LIST_FLAG.add("Comics");
        CONTENT_LIST_FLAG.add("TV Shows");
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
    }
}
