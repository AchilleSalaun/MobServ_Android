package com.oneri.Model;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by quentinleroy on 14/01/16.
 */
public class Comment {



    @SerializedName("comment")
    String mComment;

    @SerializedName("user")
    String mUser;

    public void Comment(String comment, String user){
        this.mComment = comment;
        this.mUser = user;
    }

    public void Comment(){
        this.mComment = "Comment";
        this.mUser = "User";
    }

    public String getmComment() {
        return mComment;
    }

    public void setmComment(String mComment) {
        this.mComment = mComment;
    }

    public String getmUser() {
        return mUser;
    }

    public void setmUser(String mUser) {
        this.mUser = mUser;
    }

    /***Pour tester si l'objet est bien reçu***/
    public String getJsonString() {
        JSONObject object = new JSONObject();
        try {
            object.put("User", mUser);
            object.put("Comment", mComment);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object.toString();
    }
}
