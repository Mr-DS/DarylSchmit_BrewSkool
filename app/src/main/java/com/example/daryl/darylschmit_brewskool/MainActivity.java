package com.example.daryl.darylschmit_brewskool;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends SingleFragmentActivity {

    @Override
    protected android.support.v4.app.Fragment createFragment(){
        return new MainPageFragment();
    }




}