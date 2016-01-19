package com.oneri.Model;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by quentinleroy on 28/11/15.
 */

public class Content implements Serializable {



    @SerializedName("Title")
    String mTitle;

    @SerializedName("ContentType")
    String mContentType;

    @SerializedName("Description")
    String mDescription;

    @SerializedName("Creator")
    String mCreator;

    @SerializedName("CommercialLink")
    String mCommercialLink;

    @SerializedName("ImageURL")
    String mImageURL;

    public Content(String title, String contentType, String description, String creator, String commercialLink, String imageURL) {
        this.mTitle = title;
        this.mContentType = contentType;
        this.mDescription = description;
        this.mCreator = creator;
        this.mCommercialLink = commercialLink;
        this.mImageURL = imageURL;
    }

    public Content(){
        this.mTitle = "Title";
        this.mContentType = "ContentType";
        this.mDescription = "Description";
        this.mCreator = "Creator";
        this.mCommercialLink = "CommercialLink";
        this.mImageURL = "ImageURL";
    }

    public String getmTitle(){
        return this.mTitle;
    }

    public String getmContentType(){
        return this.mContentType;
    }

    public String getmDescription(){
        return this.mDescription;
    }

    public String getmCreator(){
        return this.mCreator;
    }

    public String getmCommercialLink(){
        return this.mCommercialLink;
    }

    public String getmImageURL(){
        return this.mImageURL;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public void setmContentType(String mContentType) {
        this.mContentType = mContentType;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public void setmCreator(String mCreator) {
        this.mCreator = mCreator;
    }

    public void setmImageURL(String mImageURL) {
        this.mImageURL = mImageURL;
    }

    public void setmCommercialLink(String mCommercialLink) {
        this.mCommercialLink = mCommercialLink;
    }

    /***Pour tester si l'objet est bien reçu***/
    public String getJsonString() {
        JSONObject object = new JSONObject();
        try {
            object.put("Title", mTitle);
            object.put("ContentType", mContentType);
            object.put("Description", mDescription);
            object.put("Creator", mCreator);
            object.put("CommercialLink", mCommercialLink);
            object.put("ImageURL", mImageURL);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object.toString();
    }

}
