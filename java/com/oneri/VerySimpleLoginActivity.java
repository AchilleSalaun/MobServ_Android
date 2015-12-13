package com.oneri;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import com.oneri.Others.GlobalVars;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class VerySimpleLoginActivity extends AppCompatActivity {


    private EditText emailTv;
    private Switch switch_achille;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_very_simple_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Login");
        setSupportActionBar(toolbar);
        emailTv = (EditText) findViewById(R.id.email);
        switch_achille = (Switch) findViewById(R.id.switch_achille);
        switch_achille.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    GlobalVars.SERVLET_ACHILLE_CHECKED = true;
                else
                    GlobalVars.SERVLET_ACHILLE_CHECKED = false;
            }
        });
    }

    public void signIn(View v){

        SharedPreferences.Editor edit = GlobalVars.PREFERENCES.edit();
        edit.putString("email", emailTv.getText().toString());
        edit.commit();

        GlobalVars.EMAIL_CURRENT_USER = GlobalVars.PREFERENCES.getString("email", "kevin@gmail.com");


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
