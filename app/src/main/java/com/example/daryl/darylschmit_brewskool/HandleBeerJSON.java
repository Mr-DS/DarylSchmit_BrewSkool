package com.example.daryl.darylschmit_brewskool;

import android.annotation.SuppressLint;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by daryl on 5/2/2015.
 */
public class HandleBeerJSON {
    private String name = "name";
    private String abv = "abv";
    private String description = "description";
    private String style = "style";
    private String urlString = null;

    public volatile boolean parsingComplete = true;
    public HandleBeerJSON(String url){
        this.urlString = url;
    }
    public String getBeerName(){
        return name;
    }
    public String getABV(){
        return abv;
    }
    public String getDescription(){
        return description;
    }
    public String getStyle(){
        return style;
    }

    @SuppressLint("NewApi")
    public void readAndParseJSON(String in) {
        try {
            JSONObject reader = new JSONObject(in);

            JSONArray data  = reader.optJSONArray("data");

            if(data==null){
                name="None";
                abv="None";
                description="None";
                style="None";
            }
            else{
                for(int i=0;i<data.length();i++){
                    JSONObject b=data.getJSONObject(i);
                    JSONObject st=b.getJSONObject("style");
                    name=b.optString("name");
                    abv=b.optString("abv");
                    description=b.optString("description");
                    style=st.optString("name");
                }
            }



            parsingComplete = false;



        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    public void fetchJSON(){
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    URL url = new URL(urlString);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(10000 /* milliseconds */);
                    conn.setConnectTimeout(15000 /* milliseconds */);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    // Starts the query
                    conn.connect();
                    InputStream stream = conn.getInputStream();

                    String data = convertStreamToString(stream);
                    readAndParseJSON(data);

                    stream.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }
    static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
