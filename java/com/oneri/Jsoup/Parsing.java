package com.oneri.Jsoup;

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

    public static void getTagValue(String query, String contentType){

        new GetTagFromUrl().execute(query, contentType);
    }

    public static String getTitle(String wikipedia_page_title){

        String title = wikipedia_page_title.replace(" - Wikipedia, the free encyclopedia","");
        return title;
    }

    public static String getImageUrl(String wikipedia_tbody){

        String[] linkOuterH_lines = wikipedia_tbody.split("\n");

        String image_url = "image url";
        for(String s : linkOuterH_lines){
            if(s.contains("class=\"image\"")){
                String[] strings1 = s.split(" ");
                for(String s2 : strings1){
                    if(s2.contains("src=")){
                        image_url = s2.substring(7,s2.length()-1);
                        return image_url;
                    }
                }
            }
        }
        return "image url";
    }

    public static String getCreator2(String wikipedia_tbody){

        String[] linkOuterH_lines = wikipedia_tbody.split("\n");

        String creator = "creator";
        Integer count = 0;
        for(String s : linkOuterH_lines){
            if(s.contains("Directed by") || s.contains("Developer")){
                //String delimiters = "\\s|<|>";
                //String delimiters = "<|>";
                String delimiters = "(\" )|>|<";
                String[] strings1 = linkOuterH_lines[count+1].split(delimiters);
                for(String s2 : strings1){
                    if(s2.contains("title=")){
                        creator = s2.substring(s2.indexOf("title=\"")+7,s2.length()-1);
                        return creator;
                    }
                }
            }
            count ++;
        }
        return "creator";
    }

    public static String getFirstURLIMDBSearchResult(String title){
        String title_url_encoded = URLEncoder.encode(title);
        String url = "http://www.imdb.com/find?ref_=nv_sr_fn&q=" + title_url_encoded + "&s=all";
        String first_url_imdb_search_result = "";
        Document doc;
        try {
            doc = Jsoup.connect(url).get();
            Element link = doc.select("div[id=main]").select("table[class=findList]").select("tr[class=findResult odd]").get(0).select("td[class=result_text]").select("a").get(0); // selectionne le contenu wikipedia pour l'app
            first_url_imdb_search_result = "http://www.imdb.com/" + link.attr("href");
        }catch(IOException e){
            return "IOException in Jsoup.connect(url).get() getFirstURLIMDBSearchResult";
        }
        return first_url_imdb_search_result;
    }

    public static String getIMDBMovieDirector(String url){
        String imdb_movie_director = "";
        Document doc;
        try {
            doc = Jsoup.connect(url).get();
            Element link = doc.select("div[id=pagecontent]").select("div[id=main_top]").select("td[id=overview-top]").select("div[itemprop=director]").select("span[itemprop=name]").get(0); // selectionne le contenu wikipedia pour l'app
            imdb_movie_director = link.text();
        }catch(IOException e){
            return "IOException in Jsoup.connect(url).get() getIMDBMovieDirector";
        }
        return imdb_movie_director;
    }

    public static String getIMDBTvSeriesCreator(String url){
        String imdb_movie_director = "";
        Document doc;
        try {
            doc = Jsoup.connect(url).get();
            Element link = doc.select("div[id=pagecontent]").select("div[id=main_top]").select("td[id=overview-top]").select("div[itemprop=creator]").select("span[itemprop=name]").get(0); // selectionne le contenu wikipedia pour l'app
            imdb_movie_director = link.text();
        }catch(IOException e){
            return "IOException in Jsoup.connect(url).get() getIMDBMovieDirector";
        }
        return imdb_movie_director;
    }

    public static String getIMDBSmallDescription(String url){
        String imdb_small_description = "";
        Document doc;
        try {
            doc = Jsoup.connect(url).get();
            Element link = doc.select("div[id=pagecontent]").select("div[id=main_top]").select("p[itemprop=description]").get(0); // selectionne le contenu wikipedia pour l'app
            imdb_small_description = link.text();
        }catch(IOException e){
            return "IOException in Jsoup.connect(url).get() getIMDBSmallDescription";
        }
        return imdb_small_description;
    }

    public static String getIMDBTitleName(String url){
        String imdb_title_name = "";
        Document doc;
        try {
            doc = Jsoup.connect(url).get();
            Element link = doc.select("div[id=pagecontent]").select("div[id=main_top]").select("h1[class=header]").select("span[itemprop=name]").get(0); // selectionne le contenu wikipedia pour l'app
            imdb_title_name = link.text();
        }catch(IOException e){
            return "IOException in Jsoup.connect(url).get() getIMDBTitleName";
        }
        return imdb_title_name;
    }

    public static String getIMDBImageURL(String url){
        String imdb_image_url = "";
        Document doc;
        try {
            doc = Jsoup.connect(url).get();
            Element link = doc.select("div[id=pagecontent]").select("div[id=main_top]").select("td[id=img_primary]").select("div[class=image]").select("a[href]").select("img").get(0); // selectionne le contenu wikipedia pour l'app
            imdb_image_url = link.attr("src");
        }catch(IOException e){
            return "IOException in Jsoup.connect(url).get() getIMDBImageURL";
        }
        return imdb_image_url;
    }

    //Deprecated
    public static String getSmallIMDBDescription(String title){
        title = URLEncoder.encode(title).toLowerCase();
        String imdb_url_search = "http://www.imdb.com/find?ref_=nv_sr_fn&q=" + title + "&s=all";

        String imdb_description = "";
        Document doc;
        try {
            doc = Jsoup.connect(imdb_url_search).get();
            Element link = doc.select("tbody").first();
            String linkOuterH = link.outerHtml();

            //String line = linkOuterH.split("\n")[2];
            String line = linkOuterH;
            String delimiters = "(\" )|>|<|/s";
            String[] tokens = line.split(delimiters);
            for(String s : tokens){
                if(s.contains("href=")){
                    String imdb_url_title = "http://www.imdb.com/" + s.substring(8,s.length()-1);
                    line = s.substring(6,s.length()-1);
                    Document doc2;
                    try{
                        doc2 = Jsoup.connect(imdb_url_title).get();
                        Element link2 = doc2.select("tbody").get(0);
                        String link2OuterH = link2.outerHtml();
                        String[] lines = link2OuterH.split("\n");
                        for(String s2 : lines){
                            if(s2.contains("itemprop=\"description")){
                                imdb_description = s2.substring(s2.indexOf("itemprop=\"description\"> ")+23, s2.indexOf("</p> <p></p> "));
                                return imdb_description;
                            }
                        }

                    }catch(IOException e){
                        return line;
                    }
                    return "imdb2 " + title;
                }
            }
            return line;


        }
        catch(IOException e){
            return "imdb description";
        }


    }

    public static String getCreator(String wikipedia_tbody){

        String[] strings = wikipedia_tbody.split(" ");
        String creator = "";
        int count = 0;
        for(String s : strings){
            System.out.println("/"+s+"/");
            if(s.equals("Directed")){
                creator = strings[count+2] + " " + strings[count+3];
                return creator;
            }
            count ++;
        }

        return "creator";
    }

    private static class GetTagFromUrl extends AsyncTask<String, Void, String[]>{

        @Override
        protected String[] doInBackground(String... urls){

            String user_input = urls[0];

            String user_input_contentType = urls[1];

            String imdb_url_content = getFirstURLIMDBSearchResult(user_input);

            String title = getIMDBTitleName(imdb_url_content);

            String creator = "creator encule";
            Log.i("SALUTSALUT", "*" + user_input_contentType + "*");
            Log.i("SALUTSALUT", "*" + GlobalVars.APP_CONTEXT.getResources().getStringArray(R.array.content_types)[0] + "*");

            if(user_input_contentType.equals(GlobalVars.APP_CONTEXT.getResources().getStringArray(R.array.content_types)[0]))
                creator = getIMDBMovieDirector(imdb_url_content);
            if(user_input_contentType.equals(GlobalVars.APP_CONTEXT.getResources().getStringArray(R.array.content_types)[4]))
                creator = getIMDBTvSeriesCreator(imdb_url_content);


            String small_description = getIMDBSmallDescription(imdb_url_content);

            String image_url = getIMDBImageURL(imdb_url_content);

            String[] to_returns = {title, creator,image_url, small_description};

            return to_returns;
        }

        @Override
        protected void onPostExecute(String[] result){
            Toast.makeText(GlobalVars.APP_CONTEXT, result[0], Toast.LENGTH_SHORT).show();
            Toast.makeText(GlobalVars.APP_CONTEXT, result[1], Toast.LENGTH_SHORT).show();
            Toast.makeText(GlobalVars.APP_CONTEXT, result[2], Toast.LENGTH_SHORT).show();
            Toast.makeText(GlobalVars.APP_CONTEXT, result[3], Toast.LENGTH_SHORT).show();

            NewContentActivity.title_text_view.setText(result[0]);
            NewContentActivity.director_text_view.setText(result[1]);
            Picasso.with(GlobalVars.APP_CONTEXT).load(result[2]).resize((int) 150, 0).into(NewContentActivity.image_image_view);
            NewContentActivity.description_text_view.setText(result[3]);




        }
    }
}


