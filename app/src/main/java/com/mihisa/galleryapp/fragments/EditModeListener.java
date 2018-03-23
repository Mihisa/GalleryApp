package com.mihisa.galleryapp.fragments;

import android.view.View;

import javax.annotation.Nullable;

/**
 * Created by insight on 23.03.18.
 */

public interface EditModeListener {
    void changeEditMode(boolean editMode, int selected, int total, @Nullable View.OnClickListener listener, @Nullable String title);
}
