package com.mihisa.galleryapp.activities.data;

import android.database.Cursor;
import android.net.Uri;
import android.os.Parcelable;
import android.provider.MediaStore;

import com.drew.lang.annotations.NotNull;
import com.google.android.exoplayer2.util.ParsableNalUnitBitArray;
import com.mihisa.galleryapp.activities.util.MimeTypeUtils;

import java.io.File;
import java.sql.SQLException;

/**
 * Created by insight on 19.03.18.
 */

public class Media implements CursorHandler, Parcelable {
    private String path = null;
    private long dateModified = -1;
    private String mimeType = MimeTypeUtils.UNKNOWN_MIME_TYPE;
    private int orientation = 0;

    private String uriString = null;

    private long size = -1;
    private boolean selected = false;

    public Media() {}

    public Media(String path, long dateModified) {
        this.path = path;
        this.dateModified = dateModified;
        this.mimeType = MimeTypeUtils.getMimeType(path);
    }

    public Media(File file) {
        this(file.getPath(), file.lastModified());
        this.size = file.length();
        this.mimeType = MimeTypeUtils.getMimeType(path);
    }

    public Media(String path) {
        this(path, -1);
    }

    public Media(Uri mediaUri) {
        this.uriString = mediaUri.toString();
        this.path = null;
        this.mimeType = MimeTypeUtils.getMimeType(uriString);
    }

    public Media(@NotNull Cursor cur) {
        this.path = cur.getString(0);
        this.dateModified = cur.getLong(1);
        this.mimeType = cur.getString(2);
        this.size = cur.getLong(3);
        this.orientation = cur.getInt(4);
    }

    @Override
    public Media handle(Cursor cu) throws SQLException {
        return new Media(cu);
    }

    public static String[] getProjection() {
        return new String[] {
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DATE_TAKEN,
                MediaStore.Images.Media.MIME_TYPE,
                MediaStore.Images.Media.SIZE,
                MediaStore.Images.Media.ORIENTATION
        };
    }

}
