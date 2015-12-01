package com.oneri;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.oneri.Model.Content;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by quentinleroy on 26/11/15.
 */

public class ItemsAdapter extends ArrayAdapter<Content> {

    int drawableRectColor;
    Context context;
    LayoutInflater inflater;
    int layoutResourceId;
    float imageWidth;

    public ItemsAdapter(Context context, int layoutResourceId, ArrayList<Content> contents, int drawableRectColor) {
        super(context, layoutResourceId, contents);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.drawableRectColor = drawableRectColor;

        float width = ((Activity)context).getWindowManager().getDefaultDisplay().getWidth();
        float margin = (int)convertDpToPixel(10f, (Activity)context);
        // two images, three margins of 10dips
        imageWidth = ((width - (3 * margin)) / 2);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FrameLayout row = (FrameLayout) convertView;
        ItemHolder holder;
        final Content content = getItem(position);

        if (row == null) {
            holder = new ItemHolder();
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = (FrameLayout) inflater.inflate(layoutResourceId, parent, false);
            row.setBackgroundResource(drawableRectColor);
            final ImageView itemImage = (ImageView)row.findViewById(R.id.item_image);
            final TextView itemTitle = (TextView)row.findViewById(R.id.item_title);
            final TextView itemDescription = (TextView)row.findViewById(R.id.item_description);
            final TextView itemCommercialLink = (TextView)row.findViewById(R.id.item_commercialLink);
            final TextView itemCreator = (TextView)row.findViewById(R.id.item_creator);



            itemTitle.setText(content.getmTitle());
            itemDescription.setText(content.getmDescription());
            itemCreator.setText(content.getmCreator());
            itemCommercialLink.setText(content.getmCommercialLink());
            /*** L'URL est recuperee depuis la base de donnes***/
            String urlFromDB = content.getmImageURL();
            Picasso.with(context).load(urlFromDB).resize((int) imageWidth, 0).into(itemImage);
            /*** Picasso c'est trop bien (https://github.com/codepath/android_guides/wiki/Displaying-Images-with-the-Picasso-Library)***/


            holder.itemImage = itemImage;
            holder.itemTitle = itemTitle;
            holder.itemDescription = itemDescription;
            holder.itemCommercialLink = itemCommercialLink;
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
        TextView itemDescription;
        TextView itemCommercialLink;
        TextView itemCreator;
    }

    public static float convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi/160f);
        return px;
    }

}
