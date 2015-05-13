package com.example.daryl.darylschmit_brewskool;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by daryl on 4/22/2015.
 */
public class BreweryFragment extends Fragment {
    public static final String EXTRA_BREWERY_NAME="com.example.daryl.brewskool";

    private String mBreweryID;

    private double currentLatitude;
    private double currentLongitude;

    private TextView mBreweryNameField,mBreweryEstablishedField,mBreweryWebsiteField,mBreweryDescriptionField,mBreweryLatitude,mBreweryLongitude;
    private TextView mBreweryDistanceToField;
    private HandleBreweryJSON obj;
    private HandleLocationJSON objLocation;

    private GPSTracker gps;

    private String url1="https://api.brewerydb.com/v2/breweries?key=00e1c1f17d65153ccd720ae77d2b3f07&format=json&name=";

    private String url2;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_brewery, parent, false);

        mBreweryNameField=(TextView)v.findViewById(R.id.brewery_name_field);
        mBreweryEstablishedField=(TextView)v.findViewById(R.id.brewery_established_field);
        mBreweryWebsiteField=(TextView)v.findViewById(R.id.brewery_website_field);
        mBreweryDescriptionField=(TextView)v.findViewById(R.id.brewery_description_field);
        mBreweryLatitude=(TextView)v.findViewById(R.id.brewery_region_field);
        mBreweryLongitude=(TextView)v.findViewById(R.id.brewery_address_field);
        mBreweryDistanceToField=(TextView)v.findViewById(R.id.brewery_postal_code_field);

        gps=new GPSTracker(getActivity());

        if(gps.canGetLocation()){

            currentLatitude=gps.getLatitude();
            currentLongitude=gps.getLongitude();

        }

        String BrewerySearchName=(String)getActivity().getIntent().getSerializableExtra(EXTRA_BREWERY_NAME);
        BrewerySearchName=BrewerySearchName.replaceAll(" ", "%20");
        String finalUrl = url1 + BrewerySearchName;
        mBreweryNameField.setText(finalUrl);
        obj = new HandleBreweryJSON(finalUrl);
        obj.fetchJSON();

        while(obj.parsingComplete);
        if(obj.getBreweryName()==null){
            mBreweryNameField.setText("None");
        }
        else {
            mBreweryNameField.setText(obj.getBreweryName());
        }
        if(obj.getEstablished()==null){
            mBreweryEstablishedField.setText("None");
        }
        else {
            mBreweryEstablishedField.setText(obj.getEstablished());
        }
        if(obj.getWebsite()==null){
            mBreweryWebsiteField.setText("None");
        }
        else {
            mBreweryWebsiteField.setText(obj.getWebsite());
        }
        if(obj.getBreweryDescription()==null){
            mBreweryDescriptionField.setText("None");
        }
        else{
            mBreweryDescriptionField.setText(obj.getBreweryDescription());
        }
        if(obj.getBreweryID()==null){
            mBreweryID=null;
        }
        else {
            mBreweryID=obj.getBreweryID();

            if(mBreweryID=="None"){
                mBreweryDescriptionField.setText("None");
                mBreweryLatitude.setText("None");
                mBreweryLongitude.setText("None");
                mBreweryDistanceToField.setText("None");
            }
            else {
                url2="https://api.brewerydb.com/v2/brewery/"+mBreweryID+"/locations?&key=00e1c1f17d65153ccd720ae77d2b3f07&format=json";

                objLocation=new HandleLocationJSON(url2);
                objLocation.fetchJSON();

                while (objLocation.parsingComplete);
                if(objLocation.getBreweryRegion()==""){
                    mBreweryLatitude.setText("None");
                }
                else {
                    mBreweryLatitude.setText(objLocation.getBreweryRegion());
                }
                if(objLocation.getBreweryStreetAddress()==""){
                    mBreweryLongitude.setText("None");
                }
                else{
                    mBreweryLongitude.setText(objLocation.getBreweryStreetAddress());
                }
                if(objLocation.getBreweryPostalCode()==""){
                    mBreweryDistanceToField.setText("None");
                }
                else{
                    mBreweryDistanceToField.setText(objLocation.getBreweryPostalCode());
                }
            }


        }



        /* Attempt at getting distance to brewery
        Location currentLocation=new Location("locationA");

        currentLocation.setLatitude(currentLatitude);
        currentLocation.setLongitude(currentLongitude);

        Location breweryLocation=new Location("locationB");

        double brewLat=new Double(objLocation.getBreweryLatitude());
        double brewLong=new Double(objLocation.getBreweryLongitude());

        breweryLocation.setLatitude(brewLat);
        breweryLocation.setLatitude(brewLong);

        double distanceInMeters=currentLocation.distanceTo(breweryLocation)/1000;
        double distanceInMiles=distanceInMeters*0.000621371;

        mBreweryDistanceToField.setText(Double.toString(distanceInMeters)+" miles");
        */


        return v;
    }
}