package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ClickInterface {
    public ArrayList<Song> songsList = new ArrayList<>();
    Button favr;
    AppDataBase appDataBase;
    MusicListAdapter musicListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appDataBase = AppDataBase.getAppDatabase(getApplicationContext());
        favr = findViewById(R.id.favr);
     /*   if (isStoragePermissionGranted()) {
            ReadFilesFromStorage plm = new ReadFilesFromStorage(this);
            // get all songs from sdcard
            this.songsList = plm.getMp3Songs(this);

        }*/

        /* insertFav();*/
        RecyclerView recyclerView = findViewById(R.id.rvSongs);
        musicListAdapter = new MusicListAdapter(this, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(musicListAdapter);

        favr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FavouratesActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        songsList = getting();
        musicListAdapter.setSongs(songsList);

    }
/* public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("", "Permission is granted");
                return true;
            } else {

                Log.v("", "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v("", "Permission is granted");
            return true;
        }
    }*/


    /* void insertFav() {
         Thread thread = new Thread(new Runnable() {
             @Override
             public void run() {
                 appDataBase.userDao().insertAll(songsList);
             }
         });
         thread.start();


     }*/
    @Override
    public void click(int position) {
        Song song = songsList.get(position);
        Intent intent = new Intent(this, PlayerActivity.class);
        intent.putParcelableArrayListExtra("song", songsList);
        intent.putExtra("position", position);

        startActivity(intent);
    }

    ArrayList<Song> getting() {
        return (ArrayList<Song>) appDataBase.userDao().getAll();

    }
}

