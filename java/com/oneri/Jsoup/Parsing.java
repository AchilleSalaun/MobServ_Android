package com.oneri.Jsoup;

import android.content.res.Resources;
import android.graphics.Movie;
import android.os.AsyncTask;
import android.provider.Settings;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.oneri.NewContentActivity;
import com.oneri.Others.GlobalVars;
import com.oneri.R;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by quentinleroy on 14/01/16.
 */
public class Parsing {

    static String no_image_url = "http://www.designsbybethann.com/pictures/Flowers/none%20flowers.jpg";
    static boolean submit_allowed;
    public static boolean SUBMIT_ALLOWED;


    public static void getTagValue(String query, String contentType){

        new GetTagFromUrl().execute(query, contentType);
    }


    private static class GetTagFromUrl extends AsyncTask<String, Void, String[]>{

        @Override
        protected String[] doInBackground(String... urls){

            SUBMIT_ALLOWED = false;

            String user_input = urls[0];

            String user_input_contentType = urls[1];

            String url_content = "";
            String title = "title";
            String creator = "creator";
            String image_url = "image url";
            String description = "description";
            String commercialLink = "commercialLink";

            Log.i("SALUTSALUT", "*" + user_input_contentType + "*");
            Log.i("SALUTSALUT", "*" + GlobalVars.APP_CONTEXT.getResources().getStringArray(R.array.content_types)[0] + "*");

            String[] content_types = GlobalVars.APP_CONTEXT.getResources().getStringArray(R.array.content_types);
            if(user_input_contentType.equals(content_types[0])) {
                url_content = MovieParser.getFirstURLIMDBSearchResult(user_input);
                if(url_content!="") {
                    title = MovieParser.getIMDBTitleName(url_content);
                    creator = MovieParser.getIMDBMovieDirector(url_content);
                    description = MovieParser.getIMDBSmallDescription(url_content);
                    image_url = MovieParser.getIMDBImageURL(url_content);
                    commercialLink = MovieParser.getAmazonCommercialLink(title);
                    submit_allowed = true;
                }
                else{
                    title = "No result found";
                    creator = "";
                    description = "";
                    commercialLink = "";
                    image_url = no_image_url;
                    submit_allowed = false;
                }
            }
            if(user_input_contentType.equals(content_types[1])) {
                url_content = BookParser.getFirstURLIblistSearchResult(user_input);
                if(url_content!="") {
                    title = BookParser.getIblistTitleName(url_content);
                    creator = BookParser.getIblistAuthor(url_content);
                    description = BookParser.getIblistDescription(url_content);
                    image_url = BookParser.getIblistImageURL(url_content);
                    submit_allowed = true;
                }
                else{
                    title = "No result found";
                    creator = "";
                    description = "";
                    commercialLink = "";
                    image_url = no_image_url;
                    submit_allowed = false;
                }
            }
            if(user_input_contentType.equals(content_types[2])){
                title = creator = description = image_url = content_types[2] + " not supported";
            }
            if(user_input_contentType.equals(content_types[3])){
                title = creator = description = image_url = content_types[3] + " not supported";
            }
            if(user_input_contentType.equals(content_types[4])) {
                url_content = SeriesParser.getFirstURLIMDBSearchResult(user_input);
                if(url_content!="") {
                    title = SeriesParser.getIMDBTitleName(url_content);
                    creator = SeriesParser.getIMDBSeriesCreator(url_content);
                    description = SeriesParser.getIMDBSmallDescription(url_content);
                    image_url = SeriesParser.getIMDBImageURL(url_content);
                    commercialLink = SeriesParser.getAmazonCommercialLink(title);
                    submit_allowed = true;
                }
                else
                {
                    title = "No result found";
                    creator = "";
                    description = "";
                    commercialLink = "";
                    image_url = no_image_url;
                    submit_allowed = false;
                }
            }
            if(user_input_contentType.equals(content_types[5])){
                title = creator = description = image_url = content_types[5] + " not supported";
            }

            String[] to_returns = {title, creator,image_url, description, commercialLink};

            return to_returns;
        }

        @Override
        protected void onPostExecute(String[] result){
            if(GlobalVars.DEBUG_TOAST)Toast.makeText(GlobalVars.APP_CONTEXT, result[0], Toast.LENGTH_SHORT).show();
            if(GlobalVars.DEBUG_TOAST)Toast.makeText(GlobalVars.APP_CONTEXT, result[1], Toast.LENGTH_SHORT).show();
            if(GlobalVars.DEBUG_TOAST)Toast.makeText(GlobalVars.APP_CONTEXT, result[2], Toast.LENGTH_SHORT).show();
            if(GlobalVars.DEBUG_TOAST)Toast.makeText(GlobalVars.APP_CONTEXT, result[3], Toast.LENGTH_SHORT).show();
            if(GlobalVars.DEBUG_TOAST)Toast.makeText(GlobalVars.APP_CONTEXT, result[4], Toast.LENGTH_SHORT).show();


            NewContentActivity.title_text_view.setText(result[0]);
            NewContentActivity.director_text_view.setText(result[1]);
            Picasso.with(GlobalVars.APP_CONTEXT).load(result[2]).resize((int) 150, 0).into(NewContentActivity.image_image_view);
            NewContentActivity.description_text_view.setText(result[3]);
            NewContentActivity.commercialLink_text_view.setText(result[4]);

            NewContentActivity.content_to_save.setmTitle(result[0]);
            NewContentActivity.content_to_save.setmCreator(result[1]);
            NewContentActivity.content_to_save.setmImageURL(result[2]);
            NewContentActivity.content_to_save.setmDescription(result[3]);
            NewContentActivity.content_to_save.setmCommercialLink(result[4]);


            if(submit_allowed)Toast.makeText(GlobalVars.APP_CONTEXT, "Ready to be submitted", Toast.LENGTH_SHORT).show();
            else Toast.makeText(GlobalVars.APP_CONTEXT, "No result found", Toast.LENGTH_SHORT).show();
            SUBMIT_ALLOWED = submit_allowed;



        }
    }
}


