package com.mihisa.galleryapp.data.filter;

import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.Pattern;

/**
 * Created by insight on 15.03.18.
 */

public class ImageFileFilter implements FilenameFilter {

    private Pattern pattern;

    public ImageFileFilter(boolean includeVideo) {
        pattern = includeVideo
                ? Pattern.compile(".(jpg|png|gif|jpe|jpeg|bmp|webp|mp4|mkv|webm|avi)$", Pattern.CASE_INSENSITIVE)
                : Pattern.compile(".(jpg|png|gif|jpe|jpeg|bmp|webp)$", Pattern.CASE_INSENSITIVE);
    }

    @Override
    public boolean accept(File dir, String name) {
        return new File(dir, name).isFile() && pattern.matcher(name).find();
    }
}
