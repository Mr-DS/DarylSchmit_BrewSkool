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
public class HandleBreweryJSON {
    private String name = "name";
    private String established="established";
    private String description = "description";
    private String website="website";
    private String breweryID="id";
    private String urlString = null;

    public volatile boolean parsingComplete = true;
    public HandleBreweryJSON(String url){
        this.urlString = url;
    }
    public String getBreweryName(){
        return name;
    }
    public String getEstablished(){
        return established;
    }
    public String getBreweryDescription(){
        return description;
    }
    public String getWebsite(){
        return website;
    }
    public String getBreweryID(){
        return breweryID;
    }

    @SuppressLint("NewApi")
    public void readAndParseJSON(String in) {
        try {
            JSONObject reader = new JSONObject(in);

            JSONArray data  = reader.optJSONArray("data");

            if(data==null){
                name="None";
                established="None";
                website="None";
                description="None";
                breweryID="None";
            }
            else {
                for(int i=0;i<data.length();i++){
                    JSONObject b=data.getJSONObject(i);
                    name=b.optString("name");
                    established=b.optString("established");
                    description=b.optString("description");
                    website=b.optString("website");
                    breweryID=b.getString("id");
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
