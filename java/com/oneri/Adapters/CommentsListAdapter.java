package com.oneri.Adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.oneri.Model.Comment;
import com.oneri.Model.CommentModel;
import com.oneri.Model.Content;
import com.oneri.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by quentinleroy on 14/01/16.
 */

public class CommentsListAdapter extends ArrayAdapter<Comment> {

    Context context;
    LayoutInflater inflater;
    int layoutResourceId;

    public CommentsListAdapter(Context context, int layoutResourceId, ArrayList<Comment> contents) {
        super(context, layoutResourceId, contents);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RelativeLayout row = (RelativeLayout) convertView;
        ItemHolder holder;
        Comment comment = getItem(position);

        if (row == null) {
            holder = new ItemHolder();
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = (RelativeLayout) inflater.inflate(layoutResourceId, parent, false);
            final TextView itemUser = (TextView)row.findViewById(R.id.comment_user);
            final TextView itemComment = (TextView)row.findViewById(R.id.comment_comment);

            holder.itemUser = itemUser;
            holder.itemComment = itemComment;
        } else {
            holder = (ItemHolder) row.getTag();
        }


        holder.itemUser.setText(comment.getUserName());
        holder.itemComment.setText(comment.getmComment());

        row.setTag(holder);

        return row;
    }

    public static class ItemHolder
    {
        TextView itemUser;
        TextView itemComment;
    }



}

