package com.oneri.Model;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by quentinleroy on 02/12/15.
 */


public class Relation {

    @SerializedName("Email")
    String mEmail;

    @SerializedName("Title")
    String mTitle;

    @SerializedName("ContentType")
    String mContentType;

    @SerializedName("RelationType")
    String mRelationType;

    @SerializedName("Comment")
    String mComment;

    public void Relation(String email, String title, String contentType, String relationType, String comment){
        this.mEmail = email;
        this.mTitle = title;
        this.mContentType = contentType;
        this.mRelationType = relationType;
        this.mComment = comment;
    }

    public void Relation(){
        this.mEmail = "Email";
        this.mTitle = "Title";
        this.mContentType = "ContentType";
        this.mRelationType = "RelationType";
        this.mComment = "Comment";
    }

    public String getmEmail(){
        return this.mEmail;
    }

    public String getmTitle(){
        return this.mTitle;
    }

    public String getmContentType(){
        return this.mContentType;
    }

    public String getmRelationType(){
        return this.mRelationType;
    }

    public String getmComment(){
        return this.mComment;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public void setmContentType(String mContentType) {
        this.mContentType = mContentType;
    }

    public void setmRelationType(String mRelationType) {
        this.mRelationType = mRelationType;
    }

    public void setmComment(String mComment) {
        this.mComment = mComment;
    }

    /***Pour tester si l'objet est bien reçu***/
    public String getJsonString() {
        JSONObject object = new JSONObject();
        try {
            object.put("Email", mEmail);
            object.put("Title", mTitle);
            object.put("ContentType", mContentType);
            object.put("RelationType", mRelationType);
            object.put("Comment", mComment);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object.toString();
    }
}
