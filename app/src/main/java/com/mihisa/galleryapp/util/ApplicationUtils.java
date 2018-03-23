package com.mihisa.galleryapp.util;

import android.content.Context;
import android.support.annotation.NonNull;

import com.mihisa.galleryapp.BuildConfig;
/**
 * Created by insight on 22.03.18.
 */

public class ApplicationUtils {
    private static String PACKAGE_NAME;

    public static void init(@NonNull Context context) {
        PACKAGE_NAME = context.getPackageName();
    }

    /**
     * Get the Application's package name specified in Manifest
     */
    @NonNull
    public static String getPackageName() {
        return PACKAGE_NAME;
    }

    @NonNull
    public static String getAppVersion() {
        return BuildConfig.VERSION_NAME;
    }
}
