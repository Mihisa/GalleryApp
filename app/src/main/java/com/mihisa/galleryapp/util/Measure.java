package com.mihisa.galleryapp.util;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.google.android.exoplayer2.util.ParsableNalUnitBitArray;

/**
 * Created by insight on 02.04.18.
 */

public class Measure {
    public static final String TAG = "Measure";

    public static int pxToDp(int px, Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(px * (displayMetrics.ydpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static float dpToPx(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
    }

    public static int getStatusBarHeight(Resources r) {
        int resourceId = r.getIdentifier("status_bar_height", "dimen", "android");
        if(resourceId > 0)
            return r.getDimensionPixelSize(resourceId);
        return 0;
     }

    public static int getNavBarHeight(Context ct) {
        return getNavigationBarSize(ct).y;
    }

    public static Point getNavigationBarSize(Context context) {
        Point appUsableSize = getAppUsableScreenSize(context);
        Point realScreenSize = getRealScreenSize(context);

        //navigation bar on the right
        if(appUsableSize.x < realScreenSize.x) {
            return new Point(realScreenSize.x - appUsableSize.x, appUsableSize.y);
        }
        if (appUsableSize.y < realScreenSize.y) {
            return new Point(appUsableSize.x, realScreenSize.y - appUsableSize.y);
        }
        return new Point();
    }

    private static Point getAppUsableScreenSize(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size;
    }

    private static Point getRealScreenSize(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getRealSize(size);
        return size;
    }

    public static int rotateBy(int current, int degrees) {
    /*int rotation = current + degrees;
    if (rotation > 359) rotation -=360;
    if (rotation < 0) rotation +=360;*/
        return (current + degrees) % 360;
    }
}
