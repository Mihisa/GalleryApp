package com.mihisa.galleryapp.util;

import android.app.AlertDialog;
import android.support.annotation.StringRes;
import android.view.View;

import com.mihisa.galleryapp.R;

import org.horaapps.liz.ThemedActivity;

/**
 * Created by insight on 14.03.18.
 */

public class AlertDialogHelper  {

    public static AlertDialog getTextDialog(ThemedActivity activity, @StringRes int title, @StringRes int Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, activity.getDialogStyle());
        View dialogLayout =  activity.getLayoutInflater().inflate(R.layout.dialog_text, null);
        return builder.create();
    }

}
