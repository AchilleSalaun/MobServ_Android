package com.oneri.Model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by quentinleroy on 23/01/16.
 */

public class CommentModel {

    public Comment mComment;

    public User mUser;

    public void CommentModel(){
        this.mComment = new Comment();
        this.mUser = new User();
    }

    public Comment getmComment() {
        return mComment;
    }

    public void setmComment(Comment mComment) {
        this.mComment = mComment;
    }

    public User getmUser() {
        return mUser;
    }

    public void setmUser(User mUser) {
        this.mUser = mUser;
    }

    /***Pour tester si l'objet est bien reçu***/
    public String getJsonString() {
        JSONObject object = new JSONObject();
        try {
            object.put("Comment", mComment.getmComment());
            object.put("Email", mComment.getmEmail());
            object.put("RelationType", mComment.getmRelationType());
            object.put("ContentType", mComment.getmContentType());
            object.put("Title", mComment.getmTitle());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object.toString();
    }


}
