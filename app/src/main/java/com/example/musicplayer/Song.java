package com.example.musicplayer;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Song")
public class Song implements Parcelable {
    public static final Creator<Song> CREATOR = new Creator<Song>() {
        @Override
        public Song createFromParcel(Parcel in) {
            return new Song(in);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };
    @PrimaryKey
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "artist")
    private String artist;
    @ColumnInfo(name = "album")
    private String album;
    @ColumnInfo(name = "data")
    private String data;
    @ColumnInfo(name = "path")
    private String path;
    @ColumnInfo(name = "isFavourited")
    private int isFavourited = 0;

    protected Song(Parcel in) {
        id = in.readInt();
        artist = in.readString();
        album = in.readString();
        data = in.readString();
        path = in.readString();
        isFavourited = in.readInt();
    }

    Song(int id, String artist, String album, String data, String path, int isFavourited) {
        this.id = id;
        this.artist = artist;
        this.album = album;
        this.data = data;
        this.path = path;
        this.isFavourited = isFavourited;
    }

    public int getIsFavourited() {
        return isFavourited;
    }

    public void setIsFavourited(int isFavourited) {
        this.isFavourited = isFavourited;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(artist);
        parcel.writeString(album);
        parcel.writeString(data);
        parcel.writeString(path);
        parcel.writeInt(isFavourited);

    }
}