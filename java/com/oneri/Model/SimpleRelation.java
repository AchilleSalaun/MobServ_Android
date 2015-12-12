package com.oneri.Model;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by quentinleroy on 11/12/15.
 */
public class SimpleRelation {

    @SerializedName("RelationType")
    String relationType;

    public void SimpleRelation(){
        this.relationType = "RelationType";
    }

    public void SimpleRelation(String relationType){
        this.relationType = relationType;
    }

    public String getRelationType(){
        return this.relationType;
    }

    public void setRelationType(String relationType){
        this.relationType = relationType;
    }

    /***Pour tester si l'objet est bien reçu***/
    public String getJsonString() {
        JSONObject object = new JSONObject();
        try {
            object.put("RelatoinType", this.relationType);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object.toString();
    }
}
