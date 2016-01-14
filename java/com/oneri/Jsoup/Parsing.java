package com.oneri.Jsoup;

import android.provider.Settings;
import android.widget.Toast;

import com.oneri.Others.GlobalVars;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by quentinleroy on 14/01/16.
 */
public class Parsing {

    public static String getTagValue(String tag){

        Document doc;
        try{
            //String html = "<p> <a href='https://fr.wikipedia.org/wiki/Mario_Bros./'><b>mario</b></a> link.</p>";
            //doc = Jsoup.parse("https://fr.wikipedia.org/wiki/Mario_Bros.");

            doc = Jsoup.connect("https://fr.wikipedia.org/wiki/Mario_Bros.").get();
            String titre = doc.title();
            Element link = doc.select("tbody").first(); // selectionne le contenu wikipedia pour l'app


            String linkText = link.text(); // version texte du contenu html
            String linkOuterH = link.outerHtml(); // version balise du contenu html

            String[] strings = linkText.split(" ");


            String to_return = "";
            int count = 0;
            for(String s : strings){
                System.out.println("/"+s+"/");
                if(s.equals("Directed")){
                    to_return = strings[count+2] + " " + strings[count+3];
                    System.out.println(strings[count+2] + " " + strings[count+3]);
                    Toast.makeText(GlobalVars.APP_CONTEXT, strings[count+2] + " " + strings[count+3],Toast.LENGTH_SHORT).show();
                }
                count ++;
            }
            //System.out.println("le titre de la page : " +titre);
            //System.out.println("le texte"+text);

            //System.out.println("le texte entre les balises :"+linkText);
            //System.out.println("la balise entière : "+linkOuterH);

            //Toast.makeText(GlobalVars.APP_CONTEXT, linkText, Toast.LENGTH_SHORT).show();

            return to_return;

        }catch(IOException e){


        }

        return "";
    }

}

