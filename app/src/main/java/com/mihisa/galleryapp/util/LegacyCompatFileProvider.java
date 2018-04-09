package com.mihisa.galleryapp.util;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;

import java.io.File;

/**
 * Created by insight on 06.04.18.
 */

public class LegacyCompatFileProvider extends FileProvider {

    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return super.query(uri, projection, selection, selectionArgs, sortOrder);
    }

    public static Uri getUri(Context context, File file) {
        return getUriForFile(context, ApplicationUtils.getPackageName() + ".provider", file);
    }
}
