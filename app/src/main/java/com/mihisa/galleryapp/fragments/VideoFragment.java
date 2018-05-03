package com.mihisa.galleryapp.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.mihisa.galleryapp.R;
import com.mihisa.galleryapp.data.Media;
import com.mihisa.galleryapp.data.StorageHelper;

import org.horaapps.liz.ThemeHelper;
import org.horaapps.liz.ui.ThemedIcon;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoFragment extends BaseMediaFragment {
    @BindView(R.id.media_view) ImageView previewView;
    @BindView(R.id.video_play_icon) ThemedIcon playVideoIcon;

    @NonNull
    public static VideoFragment newInstance(@NonNull Media media) {
        return BaseMediaFragment.newInstance(new VideoFragment(), media);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_video, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        playVideoIcon.setOnClickListener(v -> {
            Uri uri = StorageHelper.getUriForFile(getContext(), media.getFile());
            Intent intent = new Intent(Intent.ACTION_VIEW).setDataAndType(uri, media.getMimeType());
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(intent);
        });
        RequestOptions requestOptions = new RequestOptions().signature(media.getSignature()).centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
        Glide.with(getContext()).load(media.getUri()).apply(requestOptions).into(previewView);
        setTapListener(previewView);
    }
    @Override
    public void refreshTheme(ThemeHelper themeHelper) {
        playVideoIcon.refreshTheme(themeHelper);
    }
}
