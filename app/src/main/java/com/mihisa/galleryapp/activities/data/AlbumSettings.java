package com.mihisa.galleryapp.activities.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.mihisa.galleryapp.activities.data.filter.FilterMode;
import com.mihisa.galleryapp.activities.data.sort.SortingMode;

import java.io.Serializable;

/**
 * Created by insight on 19.03.18.
 */

public class AlbumSettings implements Serializable, Parcelable {

    String coverPath;
    int sortingMode, sortingOrder;
    boolean pinned;
    FilterMode filterMode = FilterMode.ALL;

    public static AlbumSettings getDefaults() {
        return new AlbumSettings(null, SortingMode.DATE.getValue(), 1, 0);
    }

    public AlbumSettings(String coverPath, int sortingMode, int sortingOrder, int pinned) {
        this.coverPath = coverPath;
        this.sortingMode = sortingMode;
        this.sortingOrder = sortingOrder;
        this.pinned = pinned == 1;
    }

    public SortingMode getSortingMode() {
        return SortingMode.fromValue(sortingMode);
    }

    public SortingOrder getSortingOrder() {
        return SortingOrder.fromValue(sortingOrder);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.coverPath);
        dest.writeInt(this.sortingMode);
        dest.writeInt(this.sortingOrder);
        dest.writeByte(this.pinned ? (byte)1 : (byte)0);
        dest.writeInt(this.filterMode == null ? -1 : this.filterMode.ordinal());
    }

    protected AlbumSettings(Parcel in) {
        this.coverPath = in.readString();
        this.sortingMode = in.readInt();
        this.sortingOrder = in.readInt();
        this.pinned = in.readByte() != 0;

    }

    public static final Parcelable.Creator<AlbumSettings> CREATOR = new Parcelable.Creator<AlbumSettings>() {
        @Override
        public AlbumSettings createFromParcel(Parcel source) {
            return new AlbumSettings(source);
        }

        @Override
        public AlbumSettings[] newArray(int size) {
            return new AlbumSettings[size];
        }
    };
}
