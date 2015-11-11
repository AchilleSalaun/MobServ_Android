package com.oneri.list;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.oneri.R;

public class LazyAdapter extends BaseAdapter {
    
    private Context mContext;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader;
    static final String KEY_TITLE = "Title";
    static final String KEY_CREATOR = "Creator";
    static final String KEY_TYPE = "ContentType";
    static final String KEY_THUMB_URL = "ImageURL";

    public LazyAdapter(Context context, ArrayList<HashMap<String, String>> d) {
        mContext = context;
        data=d;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(mContext.getApplicationContext());
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.list_row, null);

        TextView title = (TextView)vi.findViewById(R.id.title); // title
        TextView artist = (TextView)vi.findViewById(R.id.creator); // artist name
        TextView duration = (TextView)vi.findViewById(R.id.type); // duration
        ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image); // thumb image
        
        HashMap<String, String> song = new HashMap<String, String>();
        song = data.get(position);
        
        // Setting all values in listview
        title.setText(song.get(KEY_TITLE));
        artist.setText(song.get(KEY_CREATOR));
        duration.setText(song.get(KEY_TYPE));
        imageLoader.DisplayImage(song.get(KEY_THUMB_URL), thumb_image);
        return vi;
    }
}