package com.example.musicplayer;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface SongDao {

    @Query("SELECT * FROM Song")
    List<Song> getAll();

    @Query("SELECT * FROM Song where isFavourited=1")
    List<Song> getAllFavourites();

    @Query("SELECT * FROM Song where id LIKE  :id")
    Song findByName(int id);

    @Query("SELECT COUNT(*) from Song")
    int countUsers();

    @Insert(onConflict = REPLACE)
    void insertAll(ArrayList<Song> song);

    @Insert
    void insertAll(Song song);

    @Delete
    void delete(Song song);


    @Query("UPDATE Song SET isFavourited= :isFavourited WHERE id = :id")
    int update(int isFavourited, int id);


}