package com.mihisa.galleryapp.util.file;

import com.mihisa.galleryapp.data.Media;

/**
 * Created by insight on 06.04.18.
 */

public class DeleteException extends Exception {
    Media media;

    public DeleteException(Media media) {
        super("Cannot delete video");
        this.media = media;
    }
    public DeleteException() {
        this(null);
    }

    public Media getMedia() {
        return media;
    }
}
