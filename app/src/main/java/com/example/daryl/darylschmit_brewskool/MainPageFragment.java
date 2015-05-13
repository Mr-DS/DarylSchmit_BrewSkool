package com.example.daryl.darylschmit_brewskool;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by daryl on 4/10/2015.
 */
public class MainPageFragment extends Fragment{


    private EditText mBeerSearchField;
    private EditText mBrewerySearchField;

    private Button mBeerSearchButton;
    private Button mBrewerySearchButton;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_main_page, parent, false);

        mBeerSearchField=(EditText)v.findViewById(R.id.beer_search_field);
        mBrewerySearchField=(EditText)v.findViewById(R.id.brewery_search_field);

        mBeerSearchButton=(Button)v.findViewById(R.id.beer_search_button);
        mBeerSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(getActivity(), BeerActivity.class);
                i.putExtra(BeerFragment.EXTRA_BEER_NAME,mBeerSearchField.getText().toString());
                startActivityForResult(i,1);

            }
        });

        mBrewerySearchButton=(Button)v.findViewById(R.id.brewery_search_button);
        mBrewerySearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(getActivity(), BreweryActivity.class);
                i.putExtra(BreweryFragment.EXTRA_BREWERY_NAME,mBrewerySearchField.getText().toString());
                startActivityForResult(i,1);

            }
        });
        return v;
    }
}