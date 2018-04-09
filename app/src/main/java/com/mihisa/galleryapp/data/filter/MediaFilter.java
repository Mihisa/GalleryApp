package com.mihisa.galleryapp.data.filter;

/**
 * Created by insight on 02.04.18.
 */

import com.mihisa.galleryapp.data.Media;

public class MediaFilter {
    public static IMediaFilter getFilter(FilterMode mode) {
        switch (mode) {
            case ALL: default:
                return media -> true;
            case GIF:
                return Media::isGif;
            case VIDEO:
                return Media::isVideo;
            case IMAGES: return Media::isImage;
        }
    }
}
