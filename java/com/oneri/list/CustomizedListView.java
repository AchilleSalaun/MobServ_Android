package com.oneri.list;

import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.oneri.R;

public class CustomizedListView extends Activity {
	// All static variables
	static final String URL = "http://oneri-1099.appspot.com/getListContent?respType=xml";
	// XML node keys
	static final String KEY_SONG = "song"; // parent node
	static final String KEY_ID = "id";
	static final String KEY_TITLE = "Title";
	static final String KEY_CREATOR = "Creator";
	static final String KEY_TYPE = "ContentType";
	static final String KEY_THUMB_URL = "ImageURL";
	
	ListView list;
    LazyAdapter adapter;

//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.main);
//
//
//		ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
//
//		XMLParser parser = new XMLParser();
//		String xml = parser.getXmlFromUrl(URL); // getting XML from URL
//		Log.d("debug", xml);
//		Document doc = parser.getDomElement(xml); // getting DOM element
//
//		NodeList nl = doc.getElementsByTagName(KEY_SONG);
//		// looping through all song nodes <song>
//		for (int i = 0; i < nl.getLength(); i++) {
//			// creating new HashMap
//			HashMap<String, String> map = new HashMap<String, String>();
//			Element e = (Element) nl.item(i);
//			// adding each child node to HashMap key => value
//			map.put(KEY_ID, parser.getValue(e, KEY_ID));
//			map.put(KEY_TITLE, parser.getValue(e, KEY_TITLE));
//			map.put(KEY_CREATOR, parser.getValue(e, KEY_CREATOR));
//			map.put(KEY_TYPE, parser.getValue(e, KEY_TYPE));
//			map.put(KEY_THUMB_URL, parser.getValue(e, KEY_THUMB_URL));
//
//			// adding HashList to ArrayList
//			songsList.add(map);
//		}
//
//
//		list=(ListView)findViewById(R.id.list);
//
//		// Getting adapter by passing xml data ArrayList
//        adapter=new LazyAdapter(this, songsList);
//        list.setAdapter(adapter);
//
//
//        // Click event for single list row
//        list.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
//
//
//			}
//		});
//	}
}