package com.oneri;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.oneri.Adapters.SimpleListViewAdapter;
import com.oneri.Model.Content;
import com.oneri.SlidingTabs.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {

    public static Toolbar toolbar;

    private SlidingTabLayout mSlidingTabLayout;

    public static String TAG_POSITION_CHOSEN = "POSITION IN THE TAB";

    private CustomViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundResource(R.color.movieColor);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.user);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Nav", Toast.LENGTH_SHORT).show();
            }
        });
        setSupportActionBar(toolbar);

        mViewPager = (CustomViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(new ScreenSlidePagerAdapter(getSupportFragmentManager()));
        //mViewPager.setPagingEnabled(false);
        mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setViewPager(mViewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        /*if( id == R.id.search){
            Toast.makeText(this, "SEARCH", Toast.LENGTH_SHORT).show();
        }*/
        if( id == R.id.dice){
            Toast.makeText(this, "RANDOM", Toast.LENGTH_SHORT).show();
            Call<List<Content>> call_random_content = GlobalVars.apiService.getRandomContent();

            call_random_content.enqueue(new Callback<List<Content>>() {
                @Override
                public void onResponse(Response<List<Content>> response, Retrofit retrofit) {
                    int statusCode = response.code();
                    List<Content> contents = response.body();
                    Log.i("STATUS", "" + response.message());
                    Log.i("STATUS", "" + statusCode);
                    Log.i("STATUS", "" + response.toString());
                    Log.i("STATUS", "" + response.raw());
                    Log.i("STATUS", "" + response.isSuccess());
                    Content random_content = contents.get(0);
                    Intent intent = new Intent(MainActivity.this, ContentActivity.class);
                    intent.putExtra(MyContentActivity.EXTRA_MESSAGE, random_content);
                    startActivity(intent);
                }

                @Override
                public void onFailure(Throwable t) {

                }
            });

        }
        if( id == R.id.user){
            Toast.makeText(this, "CAT", Toast.LENGTH_SHORT).show();
            /***LANCER UNE ACTIVITE 'MYCONTENT' ***/
            Intent intent = new Intent(this, MyContentActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position){
            return GlobalVars.CONTENT_LIST_FLAG.get(position);
        }

        @Override
        public Fragment getItem(int position) {
            ScreenSlidePageFragment screenSlidePageFragment = new ScreenSlidePageFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(TAG_POSITION_CHOSEN, position);
            screenSlidePageFragment.setArguments(bundle);
            //toolbar.setBackgroundResource(GlobalVars.CONTENT_LIST_FLAG_COLOR.get(position));
            return screenSlidePageFragment;
        }

        @Override
        public int getCount() {
            return GlobalVars.CONTENT_LIST_FLAG.size();
        }
    }



}
