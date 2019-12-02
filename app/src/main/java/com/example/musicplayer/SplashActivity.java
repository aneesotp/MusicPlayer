package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class SplashActivity extends AppCompatActivity {
    SharedPrefeManager sharedPreferences;
    AppDataBase appDataBase;
    String access_token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        appDataBase = AppDataBase.getAppDatabase(getApplicationContext());
        sharedPreferences = new SharedPrefeManager(this);
        access_token = sharedPreferences.getAccessToken();
        isStoragePermissionGranted();
        if (access_token != null)
            goToNext();

    }

    private void insertSngs(final ArrayList<Song> songs) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (access_token == null) {
                    appDataBase.userDao().insertAll(songs);
                }
                goToNext();
                sharedPreferences.saveAccessToken("1");

            }
        }, 1000);
    }

    private void goToNext() {

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 4000);
    }

    private ArrayList<Song> readSongs() {
        ReadFilesFromStorage plm = new ReadFilesFromStorage(this);
        // get all songs from sdcard
        return plm.getMp3Songs(this);
    }

    public boolean isStoragePermissionGranted() {
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
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        // If request is cancelled, the result arrays are empty.
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            // permission was granted, yay! Do the
            // contacts-related task you need to do.
            Toast.makeText(getApplicationContext(), "Permission granted", Toast.LENGTH_SHORT).show();
            ArrayList<Song> songsList = readSongs();
            insertSngs(songsList);
        } else {

            // permission denied, boo! Disable the
            // functionality that depends on this permission.
            Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
        }
        // other 'case' lines to check for other
        // permissions this app might request
    }


}
