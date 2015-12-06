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
import android.widget.TextView;

import com.oneri.Model.Content;
import com.oneri.R;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by quentinleroy on 26/11/15.
 */

public class ItemsAdapter extends ArrayAdapter<Content> {

    int drawableRectColor;
    Context context;
    LayoutInflater inflater;
    int layoutResourceId;
    float imageWidth;
    List<Content> contents;
    Integer size;

    public ItemsAdapter(Context context, int layoutResourceId, List<Content> contents, int drawableRectColor, int size) {
        super(context, layoutResourceId, contents);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.drawableRectColor = drawableRectColor;
        this.contents = contents;
        this.size = size;

        float width = ((Activity)context).getWindowManager().getDefaultDisplay().getWidth();
        float margin = (int)convertDpToPixel(10f, (Activity)context);
        // two images, three margins of 10dips
        imageWidth = ((width - (3 * margin)) / 2);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FrameLayout row = (FrameLayout) convertView;
        ItemHolder holder;
        Content content = getItem(position);


        if (row == null) {
            holder = new ItemHolder();
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = (FrameLayout) inflater.inflate(layoutResourceId, parent, false);
            row.setBackgroundResource(drawableRectColor);
            ImageView itemImage = (ImageView)row.findViewById(R.id.item_image);
            TextView itemTitle = (TextView)row.findViewById(R.id.item_title);
            //TextView itemDescription = (TextView)row.findViewById(R.id.item_description);
            //TextView itemCommercialLink = (TextView)row.findViewById(R.id.item_commercialLink);
            TextView itemCreator = (TextView)row.findViewById(R.id.item_creator);

            holder.itemImage = itemImage;
            holder.itemTitle = itemTitle;
            //holder.itemDescription = itemDescription;
            //holder.itemCommercialLink = itemCommercialLink;
            holder.itemCreator = itemCreator;
        } else {
            holder = (ItemHolder) row.getTag();
        }

        holder.itemTitle.setText(content.getmTitle());
        holder.itemTitle.setTypeface(Typeface.DEFAULT_BOLD);
        holder.itemTitle.setTextSize((float)25.0);
        //holder.itemDescription.setText(""/*content.getmDescription()*/);
        holder.itemCreator.setText(content.getmCreator());
        //holder.itemCommercialLink.setText(""/*content.getmCommercialLink()*/);
        /*** L'URL est recuperee depuis la base de donnees***/
        String urlFromDB = content.getmImageURL();
        //Picasso.with(context).load(urlFromDB).resize((int) imageWidth, 0).into(itemImage);
        Picasso.with(context).load(urlFromDB)./*memoryPolicy(MemoryPolicy.NO_CACHE).*/into(holder.itemImage);
        /*** Picasso c'est trop bien (https://github.com/codepath/android_guides/wiki/Displaying-Images-with-the-Picasso-Library)***/


        row.setTag(holder);

        return row;
    }

    @Override
    public int getCount(){
        //return this.contents.size();
        return size;
    }

    public static class ItemHolder
    {
        ImageView itemImage;
        TextView itemTitle;
        //TextView itemDescription;
        //TextView itemCommercialLink;
        TextView itemCreator;
    }

    public static float convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi/160f);
        return px;
    }

}
