package com.example.musicplayer;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefeManager {
    SharedPreferences pref;


    public SharedPrefeManager(Context ctx) {
        pref = ctx.getSharedPreferences("MyPref", 0); // 0 - for private mode

    }

    public void saveAccessToken(String token) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("access_token", token); // Storing string
        editor.commit();
    }

    public String getAccessToken() {

        return pref.getString("access_token", null);
    }
}
