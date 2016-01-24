package com.oneri.Model;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by quentinleroy on 14/01/16.
 */

public class Comment {


    @SerializedName("Comment")
    private String mComment;

    @SerializedName("Email")
    private String mEmail;

    @SerializedName("RelationType")
    private String mRelationType;

    @SerializedName("ContentType")
    private String mContentType;

    @SerializedName("Title")
    private String mTitle;


    public void Comment(String comment, String email, String relationType, String contentType, String title){
        this.mComment = comment;
        this.mEmail = email;
        this.mRelationType = relationType;
        this.mContentType = contentType;
        this.mTitle = title;
    }

    public void Comment(){
        this.mComment = "Comment";
        this.mEmail = "Email";
        this.mRelationType = "RelationType";
        this.mContentType = "ContentType";
        this.mTitle = "Title";
    }

    public String getmComment() {
        return mComment;
    }

    public void setmComment(String mComment) {
        this.mComment = mComment;
    }

    public String getmEmail() {
        return mEmail;
    }

    public String getUserName(){
        return mEmail.substring(0,mEmail.indexOf("@"));
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getmRelationType() {
        return mRelationType;
    }

    public void setmRelationType(String mRelationType) {
        this.mRelationType = mRelationType;
    }

    public String getmContentType() {
        return mContentType;
    }

    public void setmContentType(String mContentType) {
        this.mContentType = mContentType;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }



    /***Pour tester si l'objet est bien reçu***/
    public String getJsonString() {
        JSONObject object = new JSONObject();
        try {
            object.put("Comment", mComment);
            object.put("Email", mEmail);
            object.put("RelationType", mRelationType);
            object.put("ContentType", mContentType);
            object.put("Title", mTitle);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object.toString();
    }
}
