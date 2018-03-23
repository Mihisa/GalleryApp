package com.mihisa.galleryapp.fragments;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.mihisa.galleryapp.R;

import butterknife.BindView;

/**
 * Created by insight on 23.03.18.
 */

public class RvMediaFragment extends BaseFragment {
    public static final String TAG = "RvMediaFragment";
    public static final String BUNDLE_ALBUM = "album";

    @BindView(R.id.media) RecyclerView rv;
    @BindView(R.id.swipe_refresh) SwipeRefreshLayout refresh;

    private
}
