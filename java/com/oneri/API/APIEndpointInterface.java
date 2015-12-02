package com.oneri.API;

import com.oneri.Model.Content;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by quentinleroy on 26/11/15.
 */

public interface APIEndpointInterface {
    // Request method and URL specified in the annotation
    // Callback for the parsed response is the last parameter

    @GET("getRecommendations?")
    Call<List<Content>> getContents(@Query("email") String email, @Query("contentType") String content);

    @GET("getLikedContent?")
    Call<List<Content>> getLikedContents(@Query("email") String email);

    @GET("getMyContent?")
    Call<List<Content>> getMyContents(@Query("email") String email, @Query("type") String type);

}
