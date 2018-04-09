package com.mihisa.galleryapp.data.filter;

import java.io.File;
import java.io.FileFilter;

/**
 * Created by insight on 02.04.18.
 */

public class FoldersFileFilter implements FileFilter {
    @Override
    public boolean accept(File pathname) {
        return pathname.isDirectory();
    }
}
