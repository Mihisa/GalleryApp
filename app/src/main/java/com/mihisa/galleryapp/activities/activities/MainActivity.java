package com.mihisa.galleryapp.activities.activities;

import android.os.Bundle;


import com.mihisa.galleryapp.R;
import com.mihisa.galleryapp.activities.activities.base.SharedMediaActivity;

public class MainActivity extends SharedMediaActivity {

    public static final String ARGS_PICK_MODE = "pick_mode";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
