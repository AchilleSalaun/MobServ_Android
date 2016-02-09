package com.oneri.Jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URLEncoder;

/**
 * Created by quentinleroy on 24/01/16.
 */

public class SeriesParser {

    public static String getFirstURLIMDBSearchResult(String title){
        String title_url_encoded = URLEncoder.encode(title);
        //String url = "http://www.imdb.com/find?ref_=nv_sr_fn&q=" + title_url_encoded + "&s=all";
        String url = "http://www.imdb.com/find?q=" + title_url_encoded + "&s=tt&ttype=tv&ref_=fn_tv";
        String first_url_imdb_search_result = "";
        Document doc;
        try {
            doc = Jsoup.connect(url).get();
            Elements links_tmp = doc.select("div[id=main]").select("table[class=findList]").select("tr[class=findResult odd]");
            if(links_tmp.size()!=0){
                Elements links = links_tmp.get(0).
                        select("td[class=result_text]").select("a");
                if(links.size()!=0){
                    Element link = links.get(0);
                    first_url_imdb_search_result = "http://www.imdb.com/" + link.attr("href");
                }
                else{
                    first_url_imdb_search_result = "";
                }
            }
            else{
                first_url_imdb_search_result = "";
            }


        }catch(IOException e){
            return "IOException in Jsoup.connect(url).get() getFirstURLIMDBSearchResult";
        }
        return first_url_imdb_search_result;
    }

    public static String getIMDBMovieCreator(String url){
        String imdb_series_director = "";
        Document doc;
        try {
            doc = Jsoup.connect(url).get();
            Elements links = doc.select("div[id=wrapper]").select("div[id=content-2-wide]").select("div[class=plot_summary_wrapper]").
                    select("div[class=credit_summary_item]").select("span[itemprop=name]");
            if(links.size()!=0){
                Element link = links.get(0);
                imdb_series_director = link.text();
            }
            else{
                imdb_series_director = "";
            }
        }catch(IOException e){
            return "IOException in Jsoup.connect(url).get() getIMDBMovieDirector";
        }
        return imdb_series_director;
    }

    public static String getIMDBSmallDescription(String url){
        String imdb_small_description = "";
        Document doc;
        try {
            doc = Jsoup.connect(url).get();
            Elements links = doc.select("div[id=wrapper]").select("div[id=content-2-wide]").
                    select("div[class=plot_summary_wrapper]").select("div[class=summary_text]");
            if(links.size()!=0){
                Element link = links.get(0);
                imdb_small_description = link.text();
            }
            else{
                imdb_small_description = "";
            }
        }catch(IOException e){
            return "IOException in Jsoup.connect(url).get() getIMDBSmallDescription";
        }
        return imdb_small_description;
    }


    public static String getIMDBTitleName(String url){
        String imdb_title_name = "";
        Document doc;
        System.out.println("URL MOVIE : " + url);
        try {
            doc = Jsoup.connect(url).get();
            Elements links = doc.select("div[id=wrapper]").select("div[id=root]").select("div[id=content-2-wide]").
                    select("div[id=main_top]").select("div[class=title_wrapper]").
                    select("h1[itemprop=name]");
            if(links.size()!=0){
                Element link = links.get(0);
                imdb_title_name = link.text();
            }
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
            Elements links = doc.select("div[id=content-2-wide]").select("div[class=poster]").select("img");
             // selectionne le contenu wikipedia pour l'app
            if(links.size()!=0){
                Element link = links.get(0);
                imdb_image_url = link.attr("src");
            }
            else{
                imdb_image_url = Parsing.no_image_url;
            }
        }catch(IOException e){
            return "IOException in Jsoup.connect(url).get() getIMDBImageURL";
        }
        return imdb_image_url;
    }

    public static String getAmazonMovieCommercialLink(String search_query){

        String url_encoded_search_query = URLEncoder.encode(search_query.toLowerCase());
        String amazon_commercial_link = "";
        String url = "http://www.amazon.co.uk/s/ref=nb_sb_ss_c_0_4?url=search-alias%3Ddvd&field-keywords=" +
                url_encoded_search_query + "&sprefix=" + url_encoded_search_query + "%2Caps%2C170";
        Document doc;
        System.out.println(url_encoded_search_query);
        try{
            doc = Jsoup.connect(url).timeout(100000).get();

            //Element link = doc.select("div[id=main]").select("div[id=rightContainerATF]").select("li[id=result_0]").select("div[class=a-row a-spacing-mini]").select("a[class=a-link-normal s-access-detail-page a-text-normal]").get(0);

            Elements links_tmp = doc.select("div[id=main]").select("div[id=rightContainerATF]").select("li[id=result_0]").
                    select("div[class=a-row a-spacing-mini]").select("div[class=a-row a-spacing-none]");
            if(links_tmp.size()!=0){
                Elements links = links_tmp.select("a[href]");
                if(links.size()!=0){
                    Element link = links.get(0);
                    amazon_commercial_link = link.attr("href");
                }
                else{
                    amazon_commercial_link = "";
                }
            }
            else{
                amazon_commercial_link = "";
            }

        }catch(IOException e){
            //return "IOException Jsoup.connect(url).get(0) getAmazonMovieCommercialLink " + url_encoded_search_query;
            return e.getMessage();
        }

        return amazon_commercial_link;
    }

}
