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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.oneri.Adapters.SimpleListViewAdapter;
import com.oneri.Model.Content;
import com.oneri.Others.GlobalVars;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class SearchableActivity extends AppCompatActivity {

    ListView searchedListView;
    SimpleListViewAdapter searchedListViewAdapter;
    ArrayList<Content> searchedContents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        String query = intent.getStringExtra(MainActivity.EXTRA_MESSAGE2);

        toolbar.setTitle(query);
        setSupportActionBar(toolbar);

        searchedListView = (ListView) findViewById(R.id.searched_list_view);

        searchedListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ContentActivity.class);
                intent.putExtra(MyContentActivity.EXTRA_MESSAGE_CONTENT, searchedContents.get(position));
                intent.putExtra(MyContentActivity.EXTRA_MESSAGE_TOOLBAR_TITLE, searchedContents.get(position).getmTitle());
                intent.putExtra(MyContentActivity.EXTRA_MESSAGE_COLOR, GlobalVars.CONTENTTYPE_TO_COLOR.get(searchedContents.get(position).getmContentType()));
                startActivity(intent);
            }
        });


        doMySearch(query);


    }

    public void doMySearch(String query){
        Call<List<Content>> call_get_search_result =  GlobalVars.apiService.getSearchResults(query);
        call_get_search_result.enqueue(new Callback<List<Content>>() {
            @Override
            public void onResponse(Response<List<Content>> response, Retrofit retrofit) {
                int statusCode = response.code();
                List<Content> contents = response.body();
                Log.i("STATUS", "" + response.message());
                Log.i("STATUS", "" + statusCode);
                Log.i("STATUS", "" + response.toString());
                Log.i("STATUS", "" + response.raw());
                Log.i("STATUS", "" + response.isSuccess());

                searchedContents = new ArrayList<Content>();
                for (int i = 0; i < contents.size(); i = i + 1) {
                    searchedContents.add(contents.get(i));
                }


                searchedListView.setAdapter(new SimpleListViewAdapter(GlobalVars.APP_CONTEXT, R.layout.my_content_list_item, searchedContents));
                Log.i("ONRESPONSE RETROFIT", "FINISHED");
                Log.i("LEFTITEMS", "SIZE : " + searchedContents.size());
            }

            @Override
            public void onFailure(Throwable t) {
                Log.i("ONFAILURE", t.getMessage());
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
                if(GlobalVars.DEBUG_TOAST)Toast.makeText(GlobalVars.APP_CONTEXT, "onesarchclicklistened", Toast.LENGTH_SHORT).show();
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(GlobalVars.DEBUG_TOAST)Toast.makeText(GlobalVars.APP_CONTEXT, "onquerytextlistened", Toast.LENGTH_SHORT).show();
                searchView.onActionViewCollapsed();
                Intent intent = new Intent(SearchableActivity.this, SearchableActivity.class);
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
            Toast.makeText(this, "RANDOM", Toast.LENGTH_SHORT).show();
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
                    Intent intent = new Intent(SearchableActivity.this, ContentActivity.class);
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

}
