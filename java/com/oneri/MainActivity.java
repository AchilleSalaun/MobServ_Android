package com.oneri;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
       
        actionBar.setDisplayShowTitleEnabled(true);
        
        /** Creating ANDROID Tab */
        Tab tab = actionBar.newTab()
        		.setText("Book")
        		.setTabListener(new CustomTabListener<BookFragment>(this, "book", BookFragment.class));
        
        actionBar.addTab(tab);
        
        
        /** Creating APPLE Tab */
        tab = actionBar.newTab()
        		.setText("Movie")
        		.setTabListener(new CustomTabListener<MovieFragment>(this, "movie", MovieFragment.class));
        
        actionBar.addTab(tab);            
        
        
    }
}