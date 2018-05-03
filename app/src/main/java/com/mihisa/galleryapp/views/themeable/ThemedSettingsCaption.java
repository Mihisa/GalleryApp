package com.mihisa.galleryapp.views.themeable;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import org.horaapps.liz.ThemeHelper;
import org.horaapps.liz.Themed;

public class ThemedSettingsCaption extends AppCompatTextView implements Themed {

    public ThemedSettingsCaption(Context context) {
        this(context, null);
    }

    public ThemedSettingsCaption(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ThemedSettingsCaption(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void refreshTheme(ThemeHelper themeHelper) {
        setTextColor(themeHelper.getSubTextColor());
    }
}
