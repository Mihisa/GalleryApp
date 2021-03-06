package com.mihisa.galleryapp.views.themeable;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import org.horaapps.liz.ThemeHelper;
import org.horaapps.liz.Themed;
import org.horaapps.liz.ui.ThemedIcon;

public class ThemedSettingsIcon extends ThemedIcon implements Themed {

    public ThemedSettingsIcon(Context context) {
        this(context, null);
    }

    public ThemedSettingsIcon(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ThemedSettingsIcon(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void refreshTheme(ThemeHelper themeHelper) {
        setColor(themeHelper.getIconColor());
    }
}
