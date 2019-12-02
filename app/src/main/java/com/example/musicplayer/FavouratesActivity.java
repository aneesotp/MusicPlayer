package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class FavouratesActivity extends AppCompatActivity implements ClickInterface {
    public List<Song> songsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourates);


        RecyclerView recyclerView = findViewById(R.id.rvItems);
        songsList = getting();


        if (songsList != null && songsList.size() > 0) {
            FavourableAdapter favourableAdapter = new FavourableAdapter(songsList, this, this);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(favourableAdapter);
        } else
            Toast.makeText(getApplicationContext(), "No Songs", Toast.LENGTH_SHORT).show();

    }


    @Override
    public void click(int position) {

        Song song = songsList.get(position);
        Intent intent = new Intent(this, PlayerActivity.class);
        intent.putParcelableArrayListExtra("song", (ArrayList<? extends Parcelable>) songsList);
        intent.putExtra("position", position);
        startActivity(intent);
    }


    List<Song> getting() {
        AppDataBase appDataBase = AppDataBase.getAppDatabase(getApplicationContext());
        return appDataBase.userDao().getAllFavourites();

    }
}
