package com.oneri.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.oneri.Model.Content;
import com.oneri.Model.Relation;
import com.oneri.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by quentinleroy on 03/12/15.
 */

public class SimpleListViewAdapter extends ArrayAdapter<Content> {

    Context context;
    LayoutInflater inflater;
    int layoutResourceId;

    public SimpleListViewAdapter(Context context, int layoutResourceId, ArrayList<Content> contents) {
        super(context, layoutResourceId, contents);
        this.context = context;
        this.layoutResourceId = layoutResourceId;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RelativeLayout row = (RelativeLayout) convertView;
        ItemHolder holder;
        Content content = getItem(position);

        if (row == null) {
            holder = new ItemHolder();
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = (RelativeLayout) inflater.inflate(layoutResourceId, parent, false);
            final ImageView itemImage = (ImageView)row.findViewById(R.id.list_image);
            final TextView itemTitle = (TextView)row.findViewById(R.id.title);
            final TextView itemCreator = (TextView)row.findViewById(R.id.creator);

            itemTitle.setText(content.getmTitle());
            itemTitle.setTypeface(Typeface.DEFAULT_BOLD);
            //itemTitle.setTextSize((float)25.0);
            itemCreator.setText(content.getmCreator());
            /*** L'URL est recuperee depuis la base de donnees***/
            String urlFromDB = content.getmImageURL();
            Picasso.with(context).load(urlFromDB).into(itemImage);
            /*** Picasso c'est trop bien (https://github.com/codepath/android_guides/wiki/Displaying-Images-with-the-Picasso-Library)***/

            holder.itemImage = itemImage;
            holder.itemTitle = itemTitle;
            holder.itemCreator = itemCreator;
        } else {
            holder = (ItemHolder) row.getTag();
        }

        row.setTag(holder);

        return row;
    }

    public static class ItemHolder
    {
        ImageView itemImage;
        TextView itemTitle;
        TextView itemCreator;
    }

    public static float convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi/160f);
        return px;
    }

}

