package com.example.daryl.darylschmit_brewskool;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by daryl on 4/14/2015.
 */
public class BeerFragment extends Fragment {
    public static final String EXTRA_BEER_NAME="com.example.daryl.brewskool";

    private TextView mBeerNameField,mBeerStyleField,mABVField,mBeerDescriptionField;

    private HandleBeerJSON obj;
    private GPSTracker gps;

    private String url1 ="https://api.brewerydb.com/v2/beers?key=00e1c1f17d65153ccd720ae77d2b3f07&format=json&name=" ;

    private double currentLatitude;
    private double currentLongitude;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_beer, parent, false);

        mBeerNameField=(TextView)v.findViewById(R.id.beer_name_field);
        mBeerStyleField=(TextView)v.findViewById(R.id.beer_style_field);
        mABVField=(TextView)v.findViewById(R.id.beer_abv_field);
        mBeerDescriptionField=(TextView)v.findViewById(R.id.beer_description_field);

        gps=new GPSTracker(getActivity());

        if(gps.canGetLocation()){

            currentLatitude=gps.getLatitude();
            currentLongitude=gps.getLongitude();

        }

        String BeerSearchName=(String)getActivity().getIntent().getSerializableExtra(EXTRA_BEER_NAME);
        BeerSearchName=BeerSearchName.replaceAll(" ","%20");
        String finalUrl = url1 + BeerSearchName;
        mBeerNameField.setText(finalUrl);
        obj = new HandleBeerJSON(finalUrl);
        obj.fetchJSON();

        while(obj.parsingComplete);


        if(obj.getBeerName()==""){
            mBeerNameField.setText("None");
        }
        else {
            mBeerNameField.setText(obj.getBeerName());
        }
        if(obj.getABV()==""){
            mABVField.setText("None");
        }
        else {
            mABVField.setText(obj.getABV());
        }
        if(obj.getDescription()==""){
            mBeerDescriptionField.setText("None");
        }
        else {
            mBeerDescriptionField.setText(obj.getDescription());
        }
        if(obj.getStyle()==""){
            mBeerStyleField.setText("None");
        }
        else {
            mBeerStyleField.setText(obj.getStyle());
        }



        return v;
    }
}
