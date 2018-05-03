package com.mihisa.galleryapp.views.themeable;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ScrollView;

import org.horaapps.liz.ThemeHelper;
import org.horaapps.liz.Themed;

public class ThemedScrollView extends ScrollView implements Themed {
    public ThemedScrollView(Context context) {
        this(context, null);
    }

    public ThemedScrollView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ThemedScrollView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void refreshTheme(ThemeHelper themeHelper) {
        themeHelper.setScrollViewColor(this);
    }
}
