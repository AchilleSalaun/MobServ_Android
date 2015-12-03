package com.oneri;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.oneri.Adapters.SimpleListViewAdapter;
import com.oneri.Model.Content;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class RandomContentActivity extends AppCompatActivity {

    private ListView listView;

    private ArrayList<Content> list_random_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_content);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView) findViewById(R.id.list_random_content);

        loadItems();


    }

    public void loadItems(){

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

                list_random_content = new ArrayList<Content>();

                for (int i = 0; i < contents.size(); i = i + 1) {
                    list_random_content.add(contents.get(i));
                }

                Log.i("ONRESPONSE RETROFIT", "FINISHED");
                Log.i("LEFTITEMS", "SIZE : " + list_random_content.size());


                listView.setAdapter(new SimpleListViewAdapter(getApplicationContext(), R.layout.my_content_list_item, list_random_content));
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });


    }

}
