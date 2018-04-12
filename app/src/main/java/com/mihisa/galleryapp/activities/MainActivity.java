package com.mihisa.galleryapp.activities;

import android.os.Bundle;


import com.mihisa.galleryapp.R;
import com.mihisa.galleryapp.activities.base.SharedMediaActivity;
import com.mihisa.galleryapp.fragments.RvMediaFragment;

public class MainActivity extends SharedMediaActivity implements RvMediaFragment.MediaClickListener {

    public static final String ARGS_PICK_MODE = "pick_mode";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
