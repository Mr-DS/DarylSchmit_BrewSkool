package com.example.daryl.darylschmit_brewskool;

import android.support.v4.app.Fragment;

/**
 * Created by daryl on 4/22/2015.
 */
public class BreweryActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment(){
        return new BreweryFragment();
    }
}