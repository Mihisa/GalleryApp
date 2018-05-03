package com.mihisa.galleryapp.settings;

import org.horaapps.liz.ThemedActivity;

public class ThemedSetting {
    private ThemedActivity activity;

    public ThemedSetting(ThemedActivity activity) {
        this.activity = activity;
    }

    public ThemedSetting() {}

    public ThemedActivity getActivity() {
        return activity;
    }
}
