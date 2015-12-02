package com.oneri;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class VerySimpleLoginActivity extends AppCompatActivity {

    private EditText emailTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_very_simple_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        emailTv = (EditText) findViewById(R.id.email);
    }

    public void signIn(View v){
        GlobalVars.EMAIL_CURRENT_USER = (String) emailTv.getText().toString();
        Call<String> call_save_contact = GlobalVars.apiService.saveContact(GlobalVars.EMAIL_CURRENT_USER);
        call_save_contact.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Response<String> response, Retrofit retrofit) {
                int statusCode = response.code();

                Log.i("STATUS", "" + response.message());
                Log.i("STATUS", "" + statusCode);
                Log.i("STATUS", "" + response.toString());
                Log.i("STATUS", "" + response.raw());
                Log.i("STATUS", "" + response.isSuccess());

                Intent intent = new Intent(VerySimpleLoginActivity.this, MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Throwable t) {

                Log.i("FAIL", t.getMessage());
                Intent intent = new Intent(VerySimpleLoginActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });

    }

}
