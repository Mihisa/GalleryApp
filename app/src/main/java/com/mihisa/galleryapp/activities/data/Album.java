package com.mihisa.galleryapp.activities.data;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.MediaStore;

import com.mihisa.galleryapp.activities.data.filter.FilterMode;
import com.mihisa.galleryapp.activities.data.sort.SortingMode;
import com.mihisa.galleryapp.activities.data.sort.SortingOrder;
import com.mihisa.galleryapp.activities.util.StringUtils;

import java.io.File;
import java.util.ArrayList;


/**
 * Created by insight on 16.03.18.
 */

public class Album implements CursorHandler, Parcelable {

    public static final long ALL_MEDIA_ALBUM_ID = 8000;
    private String name, path;
    private long id = -1, dateModified;
    private int count = -1;

    private boolean selected = false;
    public AlbumSettings settings = null;
    private Media lastMedia = null;

    public Album(String path, String name) {
        this.path = path;
        this.name = name;
    }

    public Album(String name, long id) {
        this.name = name;
        this.id = id;
    }

    public Album(String path, String name, long id, int count, long dateModified) {
        this(path, name);
        this.count = count;
        this.id = id;
        this.dateModified = dateModified;
    }

    public Album(String path, String name, int count, long dateModified) {
        this(path, name, -1, count, dateModified);
    }

    public Album(Cursor cur) {
        this(StringUtils.getBucketPathByImagePath(cur.getString(3)),
                cur.getString(1),
                cur.getLong(0),
                cur.getInt(2),
                cur.getLong(4));

        //TODO Media
        setLastMedia(new Media(cur.getString(3)));
    }

    public static String[] getProjection() {
        return new String[]{
                MediaStore.Files.FileColumns.PARENT,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                "count(*)",
                MediaStore.Images.Media.DATA,
                "max(" + MediaStore.Images.Media.DATE_MODIFIED + ")"
        };
    }



    @Override
    public Object handle(Cursor cu) throws SQLException {
        return new Album(cu);
    }

    @Deprecated
    public Album(Context context, String path, long id, String name, int count) {
        this(path, name, id, count, 0);
        settings = AlbumSettings.getDefaults();
    }

    public static Album getEmptyAlbum() {
        Album album = new Album(null, null);
        album.settings = AlbumSettings.getDefaults();
        return album;
    }

    public static Album getAllMediaAlbum() {
        Album album = new Album("All Media", ALL_MEDIA_ALBUM_ID);
        album.settings = AlbumSettings.getDefaults();
        return album;
    }

    static Album withPath(String path) {
        Album emptyAlbum = getEmptyAlbum();
        emptyAlbum.path = path;
        return emptyAlbum;
    }

    public Album withSettings(AlbumSettings settings) {
        this.settings = settings;
        return this;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public int getCount() {
        return count;
    }

    public Long getDateModified() {
        return dateModified;
    }

    public Media getCover() {
        if (hasCover())
            return new Media(settings.coverPath);
        if (lastMedia != null)
            return lastMedia;
        return new Media();
    }

    public void setLastMedia(Media lastMedia) {
        this.lastMedia = lastMedia;
    }

    public void setCover(String path) { settings.coverPath = path; }
    public long getId() { return this.id; }
    public boolean isHidden() { return new File(path, ".nomedia").exists(); }
    public boolean isPinned(){ return settings.pinned; }
    public boolean hasCover() {
        return settings.coverPath != null;
    }
    public FilterMode filterMode() {
        return settings != null ? settings.filterMode : FilterMode.ALL;
    }
    public void setFilterMode(FilterMode newMode) {
        settings.filterMode = newMode;
    }
    public boolean isSelected() {
        return selected;
    }

    @Override
    public String toString() {
        return "Album{" +
                "name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", id=" + id +
                ", count=" + count +
                '}';
    }

    public ArrayList<String> getParentsFolders() {
        ArrayList<String> result = new ArrayList<>();

        File f = new File(getPath());
        while(f != null && f.canRead()) {
            result.add(f.getPath());
            f = f.getParentFile();
        }
        return result;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean setSelected(boolean selected) {
        if (this.selected == selected)
            return false;
        this.selected = selected;
        return true;
    }

    public boolean toggleSelected() {
        selected = !selected;
        return selected;
    }

    public void removeCoverAlbum() {
        settings.coverPath = null;
    }

    public void setSortingMode(SortingMode column) {
        settings.sortingMode = column.getValue();
    }

    public void setSortingOrder(SortingOrder sortingOrder) {
        settings.sortingOrder = sortingOrder.getValue();
    }

    public boolean togglePinAlbum() {
        settings.pinned = !settings.pinned;
        return settings.pinned;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Album) {
            return path.equals(((Album) obj).getPath());
        }
        return super.equals(obj);
    }

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.path);
        dest.writeLong(this.id);
        dest.writeLong(this.dateModified);
        dest.writeInt(this.count);
        dest.writeByte(this.selected ? (byte) 1 : (byte) 0);
        dest.writeSerializable(this.settings);
        dest.writeParcelable(this.lastMedia, flags);
    }

    protected Album(Parcel in) {
        this.name = in.readString();
        this.path = in.readString();
        this.id = in.readLong();
        this.dateModified = in.readLong();
        this.count = in.readInt();
        this.selected = in.readByte() != 0;
        this.settings = (AlbumSettings) in.readSerializable();
        this.lastMedia = in.readParcelable(Media.class.getClassLoader());
    }



    public static final Parcelable.Creator<Album> CREATOR = new Parcelable.Creator<Album>() {
        @Override
        public Album createFromParcel(Parcel in) { return new Album(in); }

        @Override
        public Album[] newArray(int size) { return new Album[size]; }
    };
}