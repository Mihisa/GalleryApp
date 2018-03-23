package com.mihisa.galleryapp.data;

import android.database.Cursor;

import java.sql.SQLException;


/**
 * Created by insight on 16.03.18.
 */

public interface CursorHandler<T> {
    T handle(Cursor cu) throws SQLException;
    static String[] getProjection() { return new String[0]; }
}
