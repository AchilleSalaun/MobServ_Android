package com.oneri.Model;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by quentinleroy on 23/01/16.
 */

public class User {

    @SerializedName("email")
    private String mUserEmail;

    public void User(){
        this.mUserEmail = "User Email";
    }

    public void User(String userEmail){
        this.mUserEmail = userEmail;
    }

    public String getUserName() {
        return mUserEmail.substring(0,mUserEmail.indexOf("@"));
    }


    public String getmUserEmail() {
        return mUserEmail;
    }

    public void setmUserEmail(String mUserEmail) {
        this.mUserEmail = mUserEmail;
    }

    /***Pour tester si l'objet est bien reçu***/
    public String getJsonString() {
        JSONObject object = new JSONObject();
        try {
            object.put("userName", getUserName());
            object.put("email", this.mUserEmail);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object.toString();
    }
}
