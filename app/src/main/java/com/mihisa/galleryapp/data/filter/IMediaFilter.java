package com.mihisa.galleryapp.data.filter;

import com.mihisa.galleryapp.data.Media;

/**
 * Created by insight on 02.04.18.
 */

public interface IMediaFilter {
    boolean accept(Media media);
}
