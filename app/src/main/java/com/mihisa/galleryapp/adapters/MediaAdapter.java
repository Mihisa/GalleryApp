package com.mihisa.galleryapp.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.mihisa.galleryapp.data.Media;
import com.mihisa.galleryapp.data.sort.MediaComparator;
import com.mihisa.galleryapp.data.sort.SortingMode;
import com.mihisa.galleryapp.data.sort.SortingOrder;
import com.mihisa.galleryapp.items.ActionsListener;

import org.horaapps.liz.ThemedAdapter;
import org.horaapps.liz.ThemedViewHolder;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.ButterKnife;

/**
 * Created by insight on 23.03.18.
 */

public class MediaAdapter extends ThemedAdapter<MediaAdapter.ViewHolder> {

    private final ArrayList<Media> media;
    private int selectedCount = 0;

    private SortingOrder sortingOrder;
    private SortingMode sortingMode;

    private Drawable placeholder;
    private final ActionsListener actionsListener;

    public MediaAdapter(Context context, SortingMode sortingMode, SortingOrder sortingOrder, ActionsListener actionsListener) {
        super(context);
        media = new ArrayList<>();
        this.sortingMode = sortingMode;
        this.sortingOrder = sortingOrder;
        placeholder = getThemeHelper().getPlaceHolder();
        setHasStableIds(true);
        this.actionsListener = actionsListener;
    }

    private void sort() {
        Collections.sort(media, MediaComparator.getComparator(sortingMode, sortingOrder));
        notifyDataSetChanged();
    }

    private boolean isSelecting = false;

    static class ViewHolder extends ThemedViewHolder{

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
