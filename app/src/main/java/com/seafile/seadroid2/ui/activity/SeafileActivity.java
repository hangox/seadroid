package com.seafile.seadroid2.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created With Android Studio
 * User 47
 * Date 2015/5/16
 * Time 16:21
 */
public class SeafileActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) onRestoreState(savedInstanceState);
    }

    public void onRestoreState(Bundle savedInstanceState){

    }
}
