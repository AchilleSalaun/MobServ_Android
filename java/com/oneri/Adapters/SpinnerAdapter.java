package com.oneri.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.oneri.Model.Comment;
import com.oneri.R;

import java.util.ArrayList;

/**
 * Created by quentinleroy on 17/01/16.
 */

public class SpinnerAdapter extends ArrayAdapter<String> {

    Context context;
    LayoutInflater inflater;
    int layoutResourceId;

    public SpinnerAdapter(Context context, int layoutResourceId, String[] contents) {
        super(context, layoutResourceId, contents);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView row = (TextView) convertView;
        ItemHolder holder;
        String contentType = getItem(position);

        if (row == null) {
            holder = new ItemHolder();
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = (TextView) inflater.inflate(layoutResourceId, parent, false);
            final TextView itemContentType = (TextView)row.findViewById(R.id.spinner_contentType);

            holder.itemContentType = itemContentType;
        } else {
            holder = (ItemHolder) row.getTag();
        }


        holder.itemContentType.setText(contentType);

        row.setTag(holder);

        return row;
    }

    public static class ItemHolder
    {
        TextView itemContentType;
    }



}

