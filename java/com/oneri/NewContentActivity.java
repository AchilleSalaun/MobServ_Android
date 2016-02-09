package com.oneri;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.oneri.Adapters.SpinnerAdapter;
import com.oneri.Jsoup.Parsing;
import com.oneri.Model.Content;
import com.oneri.Others.GlobalVars;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class NewContentActivity extends AppCompatActivity  {

    private Toolbar toolbar;
    public static TextView title_text_view;
    public static TextView director_text_view;
    public static TextView description_text_view;
    public static TextView commercialLink_text_view;
    public static ImageView image_image_view;
    public static Button submit_button;
    public static Content content_to_save;

    private String contentType_selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_content);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundResource(R.color.black);
        toolbar.setTitle("New Content");
        setSupportActionBar(toolbar);

        contentType_selected = "Movie";
        content_to_save = new Content();
        content_to_save.setmContentType(GlobalVars.CONTENT_LIST_FLAG_SERVLET.get(0));
        content_to_save.setmCommercialLink("");

        for(int i = 0;i<=5;i++){
            if(GlobalVars.DEBUG_TOAST)Toast.makeText(this,"*" + getResources().getStringArray(R.array.content_types)[i] + "*",Toast.LENGTH_SHORT).show();
        }

        title_text_view = (TextView) findViewById(R.id.new_content_title);
        director_text_view = (TextView) findViewById(R.id.new_content_director);
        description_text_view = (TextView) findViewById(R.id.new_content_description);
        image_image_view = (ImageView) findViewById(R.id.new_content_image);
        submit_button = (Button) findViewById(R.id.new_content_submit);
        commercialLink_text_view = (TextView) findViewById(R.id.new_content_commercialLink);

        Spinner spinner = (Spinner) findViewById(R.id.new_content_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        SpinnerAdapter adapter = new SpinnerAdapter(this, R.layout.spinner_item,
                getResources().getStringArray(R.array.content_types));
        // Specify the layout to use when the list of choices appears
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                contentType_selected = getResources().getStringArray(R.array.content_types)[position];
                if (GlobalVars.DEBUG_TOAST)
                    Toast.makeText(GlobalVars.APP_CONTEXT, contentType_selected, Toast.LENGTH_SHORT).show();
                content_to_save.setmContentType(GlobalVars.CONTENT_LIST_FLAG_SERVLET.get(position));
                String[] content_types = GlobalVars.APP_CONTEXT.getResources().getStringArray(R.array.content_types);
                if (contentType_selected.equals(content_types[2]) || contentType_selected.equals(content_types[3])
                        || contentType_selected.equals(content_types[5])) {
                    Toast.makeText(GlobalVars.APP_CONTEXT, contentType_selected + " not supported", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
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
                Intent intent = new Intent(NewContentActivity.this, SearchableActivity.class);
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
                    Intent intent = new Intent(NewContentActivity.this, ContentActivity.class);
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
            Intent intent = new Intent(this, MyContentActivity.class);
            startActivity(intent);
        }

        if( id == R.id.recommendations){
            if(GlobalVars.DEBUG_TOAST)Toast.makeText(this, "RECOMMENDATIONS", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

        if( id == R.id.logout){
            SharedPreferences.Editor edit = GlobalVars.PREFERENCES.edit();
            edit.putString("email", null);
            edit.commit(); // Apply changes
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

        if( id == R.id.add_content){
            if(GlobalVars.DEBUG_TOAST)Toast.makeText(this, "ADD CONTENT", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, NewContentActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public void sendLink(View v){
        EditText link_et = (EditText) findViewById(R.id.new_content_link);
        link_et.clearFocus();
        String link = link_et.getText().toString();



        String[] content_types = GlobalVars.APP_CONTEXT.getResources().getStringArray(R.array.content_types);
        if(contentType_selected.equals(content_types[0]) || contentType_selected.equals(content_types[1])
                || contentType_selected.equals(content_types[4])) {
            Toast.makeText(this, "In Progress ...", Toast.LENGTH_LONG).show();
            Parsing.getTagValue(link, contentType_selected);
        }
        else{
            Toast.makeText(this, contentType_selected + " not supported", Toast.LENGTH_SHORT).show();
        }

        //Call<String> call_saveLink = GlobalVars.apiService.saveLink(link);
        /*call_saveLink.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Response<String> response, Retrofit retrofit) {
                int statusCode = response.code();
                Log.i("STATUS", "" + response.message());
                Log.i("STATUS", "" + statusCode);
                Log.i("STATUS", "" + response.toString());
                Log.i("STATUS", "" + response.raw());
                Log.i("STATUS", "" + response.isSuccess());
            }

            @Override
            public void onFailure(Throwable t) {
                Log.i("onFailure", t.getMessage());


            }
        });*/
    }

    public void submit_new_content(View v){


        String[] content_types = GlobalVars.APP_CONTEXT.getResources().getStringArray(R.array.content_types);
        if (contentType_selected.equals(content_types[2]) || contentType_selected.equals(content_types[3])
                || contentType_selected.equals(content_types[5])) {
            Toast.makeText(GlobalVars.APP_CONTEXT, contentType_selected + " not supported", Toast.LENGTH_SHORT).show();
        }
        else {
            if(Parsing.SUBMIT_ALLOWED) {

                new AlertDialog.Builder(this)
                        .setTitle("Submit to Database")
                        .setMessage("Are you sure you want to submit this content?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Call<Content> saveContent = GlobalVars.apiService.saveContent(content_to_save.getmTitle(), content_to_save.getmContentType(),
                                        content_to_save.getmCreator(), content_to_save.getmDescription(), content_to_save.getmCommercialLink(),
                                        content_to_save.getmImageURL());

                                saveContent.enqueue(new Callback<Content>() {
                                    @Override
                                    public void onResponse(Response<Content> response, Retrofit retrofit) {
                                        Toast.makeText(GlobalVars.APP_CONTEXT, "Content Submitted to Database", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onFailure(Throwable t) {
                                        Log.i("STATUS", t.getMessage());
                                        Toast.makeText(GlobalVars.APP_CONTEXT, "Content Submitted to Database", Toast.LENGTH_SHORT).show();

                                    }
                                });
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }
            else{
                Toast.makeText(this, "Submit not allowed, no result found", Toast.LENGTH_SHORT).show();
            }
        }


    }


}
