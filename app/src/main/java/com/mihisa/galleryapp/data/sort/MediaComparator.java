package com.mihisa.galleryapp.data.sort;

import com.mihisa.galleryapp.data.AlbumSettings;
import com.mihisa.galleryapp.data.Media;
import com.mihisa.galleryapp.util.NumericComparator;

import java.util.Comparator;

/**
 * Created by insight on 23.03.18.
 */

public class MediaComparator {

    public static Comparator<Media> getComparator(AlbumSettings settings) {
        return getComparator(settings.getSortingMode(), settings.getSortingOrder());
    }

    public static Comparator<Media> getComparator(SortingMode sortingMode, SortingOrder order) {
        return order == SortingOrder.ASCENDING
                ? getComparator(sortingMode) : getComparator(sortingMode);
    }

    public static Comparator<Media> getComparator(SortingMode sortingMode) {
        switch (sortingMode) {
            case DATE: default: return getDataComparator();
            case NAME: return getNameComparator();
            case SIZE: return getSizeComparator();
            case TYPE: return getTypeComparator();
            case NUMERIC: return getNumericComparator();
        }
    }

    private static Comparator<Media> reverse (Comparator<Media> comparator) {
        return ((o1, o2) -> comparator.compare(o2, o1));
    }

    private static Comparator<Media> getDataComparator() {
        return (f1, f2) -> f1.getDateModified().compareTo(f2.getDateModified());
    }

    private static Comparator<Media> getNameComparator() {
        return (f1, f2) -> f1.getPath().compareTo(f2.getPath());
    }

    private static Comparator<Media> getSizeComparator() {
        return (f1, f2) -> Long.compare(f1.getSize(), f2.getSize());
    }

    private static Comparator<Media> getTypeComparator() {
        return (f1, f2) -> f1.getMimeType().compareTo(f2.getMimeType());
    }

    private static Comparator<Media> getNumericComparator() {
        //TODO NumericComparator
        return (f1, f2) -> NumericComparator.filevercmp(f1.getPath(), f2.getPath());
    }

}
