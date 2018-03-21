package com.mihisa.galleryapp.activities.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by insight on 15.03.18.
 */

public class HandlingAlbums extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "folders.db";
    private static final String TABLE_ALBUMS = "folders";

    public static final int EXCLUDED = 1;
    public static final int INCLUDED = 2;

    private static final String ALBUM_PATH = "path";
    private static final String ALBUM_ID = "id";
    private static final String ALBUM_PINNED = "pinned";
    private static final String ALBUM_COVER_PATH = "cover_path";
    private static final String ALBUM_STATUS = "status";
    private static final String ALBUM_SORTING_MODE = "sorting_mode";
    private static final String ALBUM_SORTING_ORDER = "sorting_order";

    private static HandlingAlbums mInstance = null;

    private HandlingAlbums(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static HandlingAlbums getInstance(Context context) {
        if(mInstance == null) {
            mInstance = new HandlingAlbums(context);
        }
        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " +
                TABLE_ALBUMS + "(" +
                ALBUM_PATH + " TEXT," +
                ALBUM_ID + " INTEGER," +
                ALBUM_PINNED + " INTEGER," +
                ALBUM_COVER_PATH + " TEXT, " +
                ALBUM_STATUS + " INTEGER, " +
                ALBUM_SORTING_MODE + " INTEGER, " +
                ALBUM_SORTING_ORDER + " INTEGER)");
        db.execSQL(String.format("CREATE UNIQUE INDEX idx_path ON %s (%s)", TABLE_ALBUMS, ALBUM_PATH));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ALBUMS);
        db.execSQL("DROP INDEX IF EXISTS idx_path");
        onCreate(db);
    }

    public void excludeAlbum(SQLiteDatabase db, Album album) {

    }
}
