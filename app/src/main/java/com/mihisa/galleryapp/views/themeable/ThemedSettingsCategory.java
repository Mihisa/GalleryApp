package com.mihisa.galleryapp.views.themeable;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import org.horaapps.liz.ThemeHelper;
import org.horaapps.liz.Themed;

public class ThemedSettingsCategory extends AppCompatTextView implements Themed {
    public ThemedSettingsCategory(Context context) {
        this(context, null);
    }

    public ThemedSettingsCategory(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ThemedSettingsCategory(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void refreshTheme(ThemeHelper themeHelper) {
        themeHelper.setTextViewColor(this, themeHelper.getAccentColor());
    }
}
