package com.oneri;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.oneri.Model.Content;
import com.oneri.Model.Relation;
import com.oneri.Model.SimpleRelation;
import com.squareup.picasso.Picasso;

import java.net.URLEncoder;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class ContentActivity extends AppCompatActivity {

    private Content content;
    private Relation relation;
    private ImageView content_relation_likes_imageview;
    private ImageView content_relation_wishes_imageview;
    private ImageView content_relation_do_not_like_imageview;
    private boolean likes_selected;
    private boolean wishes_selected;
    private boolean do_not_like_selected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        content_relation_likes_imageview = (ImageView) findViewById(R.id.content_relation_likes);
        content_relation_wishes_imageview = (ImageView) findViewById(R.id.content_relation_wishes);
        content_relation_do_not_like_imageview = (ImageView) findViewById(R.id.content_relation_do_not_like);


        Intent intent = getIntent();
        content = (Content)intent.getSerializableExtra(MyContentActivity.EXTRA_MESSAGE_CONTENT);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(intent.getStringExtra(MyContentActivity.EXTRA_MESSAGE_TOOLBAR_TITLE));
        toolbar.setBackgroundResource(intent.getIntExtra(MyContentActivity.EXTRA_MESSAGE_COLOR, R.color.black));
        setSupportActionBar(toolbar);

        Call<SimpleRelation> call_get_relation = GlobalVars.apiService.getRelation(GlobalVars.EMAIL_CURRENT_USER,
                        content.getmTitle(), content.getmContentType());
        call_get_relation.enqueue(new Callback<SimpleRelation>() {
            @Override
            public void onResponse(Response<SimpleRelation> response, Retrofit retrofit) {
                SimpleRelation relation = response.body();

                Log.i("OnResponse", "getRelation : " + relation.getRelationType());

                switch (relation.getRelationType()){
                    case GlobalVars.SAVE_RELATION_SERVLET_LIKES:
                        content_relation_likes_imageview.setBackgroundResource(R.drawable.likes_selected);
                        likes_selected = true;
                        break;
                    case GlobalVars.SAVE_RELATION_SERVLET_WAITING:
                        content_relation_wishes_imageview.setBackgroundResource(R.drawable.wishes_selected);
                        wishes_selected = true;
                        break;
                    case GlobalVars.SAVE_RELATION_SERVLET_DOESNT_LIKE:
                        content_relation_do_not_like_imageview.setBackgroundResource(R.drawable.donotlike_selected);
                        do_not_like_selected = true;
                        break;
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.i("OnFailure", "getRelation : " + t.getMessage());
            }
        });

        ImageView imageView = (ImageView) findViewById(R.id.content_image);
        String urlFromDB = content.getmImageURL();
        Picasso.with(this).load(urlFromDB).resize((int) 150, 0).into(imageView);

        TextView title = (TextView) findViewById(R.id.content_title);
        title.setText(content.getmTitle());

        TextView creator = (TextView) findViewById(R.id.content_creator);
        creator.setText(content.getmCreator());

        TextView commercialLink = (TextView) findViewById(R.id.content_commercialLink);
        commercialLink.setText(content.getmCommercialLink());
        commercialLink.setMovementMethod(LinkMovementMethod.getInstance());

        TextView description = (TextView) findViewById(R.id.content_description);
        description.setText(content.getmDescription());


        relation = new Relation();
        relation.setmEmail(GlobalVars.EMAIL_CURRENT_USER);
        relation.setmTitle(content.getmTitle());
        relation.setmContentType(content.getmContentType());
        relation.setmComment("AUCUN COMMENTAIRE DANS LA V1");

    }

    public void saveRelation(View v){
        Integer id = v.getId();
        switch (id){
            case R.id.content_relation_likes:
                relation.setmRelationType(GlobalVars.SAVE_RELATION_SERVLET_LIKES);
                if(likes_selected) {
                    content_relation_likes_imageview.setBackgroundResource(R.drawable.likes);
                    likes_selected = false;
                }
                else {
                    content_relation_likes_imageview.setBackgroundResource(R.drawable.likes_selected);
                    likes_selected = true;
                }
                content_relation_wishes_imageview.setBackgroundResource(R.drawable.wishes);
                content_relation_do_not_like_imageview.setBackgroundResource(R.drawable.donotlike);
                if(GlobalVars.DEBUG_TOAST)Toast.makeText(this, "LIKES", Toast.LENGTH_SHORT).show();
                break;

            case R.id.content_relation_wishes:
                relation.setmRelationType(GlobalVars.SAVE_RELATION_SERVLET_WAITING);
                if(wishes_selected){
                    content_relation_wishes_imageview.setBackgroundResource(R.drawable.wishes);
                    wishes_selected = false;
                }
                else{
                    content_relation_wishes_imageview.setBackgroundResource(R.drawable.wishes_selected);
                    wishes_selected = true;
                }
                content_relation_likes_imageview.setBackgroundResource(R.drawable.likes);
                content_relation_do_not_like_imageview.setBackgroundResource(R.drawable.donotlike);
                if(GlobalVars.DEBUG_TOAST)Toast.makeText(this, "WAITING", Toast.LENGTH_SHORT).show();
                break;

            case R.id.content_relation_do_not_like:
                relation.setmRelationType(GlobalVars.SAVE_RELATION_SERVLET_DOESNT_LIKE);
                if(do_not_like_selected){
                    do_not_like_selected = false;
                    content_relation_do_not_like_imageview.setBackgroundResource(R.drawable.donotlike);
                }
                else{
                    do_not_like_selected = true;
                    content_relation_do_not_like_imageview.setBackgroundResource(R.drawable.donotlike_selected);
                }
                content_relation_likes_imageview.setBackgroundResource(R.drawable.likes);
                content_relation_wishes_imageview.setBackgroundResource(R.drawable.wishes);
                if(GlobalVars.DEBUG_TOAST)Toast.makeText(this, "DOESN'T LIKE", Toast.LENGTH_SHORT).show();
                break;

        }

        Call<Relation> call_saveRelation = GlobalVars.apiService.saveRelation(relation.getmEmail(), relation.getmTitle(),
                                relation.getmContentType(), relation.getmRelationType(), relation.getmComment());

        call_saveRelation.enqueue(new Callback<Relation>() {
            @Override
            public void onResponse(Response<Relation> response, Retrofit retrofit) {
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
                Intent intent = new Intent(ContentActivity.this, SearchableActivity.class);
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
                    Intent intent = new Intent(ContentActivity.this, ContentActivity.class);
                    intent.putExtra(MyContentActivity.EXTRA_MESSAGE_TOOLBAR_TITLE, "Random");
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

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed(){
        Intent intent = new Intent(ContentActivity.this, MainActivity.class);
        startActivity(intent);
    }

}
