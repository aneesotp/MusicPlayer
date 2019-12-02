package com.example.musicplayer;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

public class ReadFilesFromStorage {

    private Context mContext;
    private ArrayList<Song> songsList;

    public ReadFilesFromStorage(Context context) {
        mContext = context;
    }


    public ArrayList<Song> getMp3Songs(Context ctx) {
        songsList = new ArrayList<>();
        Uri allSongsUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";
        Cursor cursor = ctx.getContentResolver().query(allSongsUri, null, null, null, selection);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    Song song = new Song(cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media._ID)),
                            cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME)),
                            cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)),
                            cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)),
                            cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.BOOKMARK)), 0);
                    songsList.add(song);
//                    album_name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
//                    int album_id = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
//                    int artist_id = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST_ID));
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return songsList;
    }

    class FileExtensionFilter implements FilenameFilter {
        public boolean accept(File dir, String name) {
            return (name.endsWith(".mp3") || name.endsWith(".MP3"));
        }
    }
}