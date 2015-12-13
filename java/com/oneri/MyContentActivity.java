package com.oneri;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.oneri.Adapters.ExpandableListAdapter;
import com.oneri.Model.Content;
import com.oneri.Others.GlobalVars;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class MyContentActivity extends AppCompatActivity {

    public static String EXTRA_MESSAGE_CONTENT = "com.oneri.appcompatactivity(content)";
    public static String EXTRA_MESSAGE_TOOLBAR_TITLE = "com.oneri.appcompatactivity(toolbartitle)";
    public static String EXTRA_MESSAGE_COLOR = "com.oneri.appcompatactivity(color)";



    public static ArrayList<String> HEADER_LIST_TAG;

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<Content>> listDataChild;

    ArrayList<Content> like_list;
    ArrayList<Content> wish_list;
    ArrayList<Content> do_not_like_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_content);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundResource(R.color.colorPrimaryDark);
        toolbar.setTitle("My Content");
        setSupportActionBar(toolbar);

        HEADER_LIST_TAG = new ArrayList<>();
        HEADER_LIST_TAG.add("My List");
        HEADER_LIST_TAG.add("I like");
        HEADER_LIST_TAG.add("I do not like");


        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                if(GlobalVars.DEBUG_TOAST)Toast.makeText(
                        getApplicationContext(),
                        listDataHeader.get(groupPosition)
                                + " : "
                                + listDataChild.get(
                                listDataHeader.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT)
                        .show();
                Intent intent = new Intent(getApplicationContext(), ContentActivity.class);
                intent.putExtra(EXTRA_MESSAGE_CONTENT, listDataChild.get(
                        listDataHeader.get(groupPosition)).get(
                        childPosition));
                intent.putExtra(EXTRA_MESSAGE_TOOLBAR_TITLE, listDataChild.get(
                        listDataHeader.get(groupPosition)).get(
                        childPosition).getmTitle());
                intent.putExtra(MyContentActivity.EXTRA_MESSAGE_COLOR,
                        GlobalVars.CONTENTTYPE_TO_COLOR.get(listDataChild.get(
                                listDataHeader.get(groupPosition)).get(
                                childPosition).getmContentType()));

                startActivity(intent);
                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                if(GlobalVars.DEBUG_TOAST)Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                if(GlobalVars.DEBUG_TOAST)Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT).show();

            }
        });

        // preparing list data
        prepareListData();

    }

    /*
    * Preparing the list data
    */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<Content>>();

        // Adding child data
        listDataHeader.add(HEADER_LIST_TAG.get(0));
        listDataHeader.add(HEADER_LIST_TAG.get(1));
        listDataHeader.add(HEADER_LIST_TAG.get(2));

        loadItems();
    }




    private void loadItems(){
        Call<List<Content>> call_liked_content = GlobalVars.apiService.getLikedContents(GlobalVars.EMAIL_CURRENT_USER );
        Call<List<Content>> call_wish_content = GlobalVars.apiService.getMyContents(GlobalVars.EMAIL_CURRENT_USER, "myList");
        Call<List<Content>> call_do_not_liked_content = GlobalVars.apiService.getMyContents(GlobalVars.EMAIL_CURRENT_USER, "contentUserDoesntLike");

        call_liked_content.enqueue(new Callback<List<Content>>() {
            @Override
            public void onResponse(Response<List<Content>> response, Retrofit retrofit) {
                int statusCode = response.code();
                List<Content> contents = response.body();

                Log.i("STATUS", "" + response.message());
                Log.i("STATUS", "" + statusCode);
                Log.i("STATUS", "" + response.toString());
                Log.i("STATUS", "" + response.raw());
                Log.i("STATUS", "" + response.isSuccess());

                like_list = new ArrayList<Content>();
                for (int i = 0; i < contents.size(); i = i + 1) {
                    like_list.add(contents.get(i));
                }
                listDataChild.put(listDataHeader.get(1), like_list);
                Log.i("ONRESPONSE RETROFIT", "FINISHED");
                Log.i("LEFTITEMS", "SIZE : " + like_list.size());
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
        call_wish_content.enqueue(new Callback<List<Content>>() {
            @Override
            public void onResponse(Response<List<Content>> response, Retrofit retrofit) {
                int statusCode = response.code();
                List<Content> contents = response.body();

                Log.i("STATUS", "" + response.message());
                Log.i("STATUS", "" + statusCode);
                Log.i("STATUS", "" + response.toString());
                Log.i("STATUS", "" + response.raw());
                Log.i("STATUS", "" + response.isSuccess());

                wish_list = new ArrayList<Content>();

                for (int i = 0; i < contents.size(); i = i + 1) {
                    wish_list.add(contents.get(i));
                }

                listDataChild.put(listDataHeader.get(0), wish_list);
                Log.i("ONRESPONSE RETROFIT", "FINISHED");
                Log.i("LEFTITEMS", "SIZE : " + wish_list.size());
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
        call_do_not_liked_content.enqueue(new Callback<List<Content>>() {
            @Override
            public void onResponse(Response<List<Content>> response, Retrofit retrofit) {
                int statusCode = response.code();
                List<Content> contents = response.body();

                Log.i("STATUS", "" + response.message());
                Log.i("STATUS", "" + statusCode);
                Log.i("STATUS", "" + response.toString());
                Log.i("STATUS", "" + response.raw());
                Log.i("STATUS", "" + response.isSuccess());

                do_not_like_list = new ArrayList<Content>();

                for (int i = 0; i < contents.size(); i = i + 1) {
                    do_not_like_list.add(contents.get(i));
                }

                listDataChild.put(listDataHeader.get(2), do_not_like_list);
                Log.i("ONRESPONSE RETROFIT", "FINISHED");
                Log.i("LEFTITEMS", "SIZE : " + do_not_like_list.size());

                listAdapter = new ExpandableListAdapter(getApplicationContext(), listDataHeader, listDataChild);
                // setting list adapter
                expListView.setAdapter(listAdapter);
                expListView.expandGroup(0);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        // Get the SearchView and set the searchable configuration
        //SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        // Assumes current activity is the searchable activity
        //searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(true); // Do not iconify the widget; expand it by default

        searchView.setMaxWidth(GlobalVars.SEARCH_BAR_WIDTH);

        searchView.setOnSearchClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (GlobalVars.DEBUG_TOAST)
                    Toast.makeText(GlobalVars.APP_CONTEXT, "onesarchclicklistened", Toast.LENGTH_SHORT).show();
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (GlobalVars.DEBUG_TOAST)
                    Toast.makeText(GlobalVars.APP_CONTEXT, "onquerytextlistened", Toast.LENGTH_SHORT).show();
                searchView.onActionViewCollapsed();
                Intent intent = new Intent(MyContentActivity.this, SearchableActivity.class);
                intent.putExtra(MainActivity.EXTRA_MESSAGE2, query);
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                searchView.onActionViewCollapsed();
                return false;
            }
        });
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if( id == R.id.dice){
            if(GlobalVars.DEBUG_TOAST)Toast.makeText(this, "RANDOM", Toast.LENGTH_SHORT).show();
            Call<List<Content>> call_random_content = GlobalVars.apiService.getRandomContent(GlobalVars.EMAIL_CURRENT_USER);

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
                    Intent intent = new Intent(MyContentActivity.this, ContentActivity.class);
                    intent.putExtra(MyContentActivity.EXTRA_MESSAGE_TOOLBAR_TITLE, "Random Content");
                    intent.putExtra(MyContentActivity.EXTRA_MESSAGE_CONTENT, random_content);
                    intent.putExtra(MyContentActivity.EXTRA_MESSAGE_COLOR, GlobalVars.CONTENTTYPE_TO_COLOR.get(random_content.getmContentType()));
                    startActivity(intent);
                }

                @Override
                public void onFailure(Throwable t) {

                }
            });
        }
        if( id == R.id.user){
            if(GlobalVars.DEBUG_TOAST)Toast.makeText(this, "CAT", Toast.LENGTH_SHORT).show();
            /***LANCER UNE ACTIVITE 'MYCONTENT' ***/
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

        if( id == R.id.recommendations){
            if(GlobalVars.DEBUG_TOAST)Toast.makeText(this, "RECOMMENDATIONS", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

        if( id == R.id.logout){
            Intent intent = new Intent(this, VerySimpleLoginActivity.class);
            startActivity(intent);
        }

        if( id == R.id.logout){
            SharedPreferences.Editor edit = GlobalVars.PREFERENCES.edit();
            edit.putString("email", null);
            edit.commit(); // Apply changes
            Intent intent = new Intent(this, VerySimpleLoginActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


    /***TO-DO
     * OnResume : Recharger entierement tout
     */



}
