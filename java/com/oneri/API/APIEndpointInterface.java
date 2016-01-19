package com.oneri.API;

import com.oneri.Model.Comment;
import com.oneri.Model.Content;
import com.oneri.Model.Relation;
import com.oneri.Model.SimpleRelation;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by quentinleroy on 26/11/15.
 */

public interface APIEndpointInterface {
    // Request method and URL specified in the annotation
    // Callback for the parsed response is the last parameter

    @GET("getRecommendations?")
    Call<List<Content>> getContents(@Query("email") String email, @Query("contentType") String content);

    @GET("test?")
    Call<List<Content>> test(@Query("email") String email, @Query("contentType") String content);

    @GET("getLikedContent?")
    Call<List<Content>> getLikedContents(@Query("email") String email);

    @GET("getMyContent?")
    Call<List<Content>> getMyContents(@Query("email") String email, @Query("type") String type);

    @GET("getRandomContent?")
    Call<List<Content>> getRandomContent(@Query("email") String email);

    @POST("saveRelation?")
    Call<Relation> saveRelation(@Query("email") String email, @Query("title") String title, @Query("contentType") String contentType,
                                @Query("relationType") String relationType, @Query("comment") String comment);

    @POST("saveContact")
    Call<String> saveContact(@Query("email") String email);

    @GET("getSearchResults")
    Call<List<Content>> getSearchResults(@Query("request") String query);

    @GET("getListContent?respType=json")
    Call<List<Content>> getListContent();

    @GET("getRelation")
    Call<List<SimpleRelation>> getRelation(@Query("email") String email, @Query("title") String title, @Query("contentType") String contentType);

    @POST("saveLink")
    Call<String> saveLink(@Query("link") String link);

    @GET("getComments")
    Call<List<Comment>> getComments(@Query("title") String title, @Query("contentType") String contentType);

    @POST("saveContent")
    Call<Content> saveContent(@Query("title") String title, @Query("contentType") String contentType, @Query("creator") String creator,
                              @Query("description") String description, @Query("commercialLink") String commercialLink,
                              @Query("imageURL") String imageURL);

}
