package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class PlayerActivity extends AppCompatActivity implements View.OnClickListener {
    ArrayList<Song> songlist;
    TextView tvSong;
    ImageView ivFav;
    ImageButton btnPlay, btnnxt, btnpre;
    AudioPlayer audioPlayer;
    SeekBar seekBar;
    boolean flag = false;
    boolean flag2 = false;
    ImageView songimage;
    int position;
    Song song;
    AppDataBase appDataBase;
    int isFav = 0;
    private Handler mHandler;
    private Runnable mRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        audioPlayer = new AudioPlayer();
        tvSong = findViewById(R.id.tvSongName);
        btnPlay = findViewById(R.id.btnPlay);
        btnnxt = findViewById(R.id.btn_nxt);
        btnpre = findViewById(R.id.btn_pre);
        ivFav = findViewById(R.id.ivFav);
        songimage = findViewById(R.id.musicplayer);


        seekBar = findViewById(R.id.seekbar);
        mHandler = new Handler();
        songlist = getIntent().getExtras().getParcelableArrayList("song");
        position = getIntent().getExtras().getInt("position", 0);
        song = songlist.get(position);
        tvSong.setText(songlist.get(position).getArtist());
        appDataBase = AppDataBase.getAppDatabase(getApplicationContext());
        tvSong.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        tvSong.setSelected(true);
        tvSong.setSingleLine(true);
        btnnxt.setOnClickListener(this);
        ivFav.setOnClickListener(this);
        isFav = song.getIsFavourited();
        setFavIcon();
        btnpre.setOnClickListener(this);
        btnPlay.setOnClickListener(this);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (audioPlayer != null && b) {

                    audioPlayer.seekToPosition(i * 1000);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    private void setFavIcon() {
        if (isFav == 1)
            ivFav.setImageResource(R.drawable.heartlight);
        else
            ivFav.setImageResource(R.drawable.heartblur);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnPlay: {
                if (flag) {
                    audioPlayer.pause(this, song.getData());
                    getAudioStats();

                    btnPlay.setImageDrawable(ContextCompat.getDrawable(PlayerActivity.this, R.drawable.play));
                    flag = true;
                    flag2 = true;
                    initializeSeekBar();

                } else if (flag2) {
                    audioPlayer.resume();
                    btnPlay.setImageDrawable(ContextCompat.getDrawable(PlayerActivity.this, R.drawable.play));
                    flag2 = !flag2;
                } else {
                    audioPlayer.play(this, song.getData());
                    btnPlay.setImageDrawable(ContextCompat.getDrawable(PlayerActivity.this, R.drawable.pause));
                }
                flag = !flag;
                break;
            }

            case R.id.btn_nxt:
                position = position + 1;
                if (position < songlist.size()) {
                    audioPlayer.play(this, songlist.get(position).getData());
                    tvSong.setText(songlist.get(position).getArtist());
                }
                break;
            case R.id.btn_pre:
                position = position - 1;
                if (position >= 0) {
                    audioPlayer.play(this, songlist.get(position).getData());
                    tvSong.setText(songlist.get(position).getArtist());
                } else
                    Toast.makeText(this, "No More Songs", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ivFav:
                insertFav(song);
                break;

        }
        if (position >= 0) {
            song = songlist.get(position);
            isFav = song.getIsFavourited();
            setFavIcon();
        }
    }

    void insertFav(final Song song) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                final int count = appDataBase.userDao().update(song.getIsFavourited() ^ 1, song.getId());
                runOnUiThread(new Runnable() {
                    public void run() {
                        isFav = song.getIsFavourited() ^ 1;
                        if (count > 0 && isFav == 1) {
                            Toast.makeText(getApplicationContext(), "Added to Favourites", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Removed to Favourites", Toast.LENGTH_LONG).show();
                        }
                        setFavIcon();

                    }
                });
            }
        });
        thread.start();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        audioPlayer.destroy();
        mHandler.removeCallbacks(mRunnable);

    }

    protected void getAudioStats() {
        int duration = audioPlayer.getDuration() / 1000; // In milliseconds
        int due = (audioPlayer.getDuration() - audioPlayer.getCurrentPosition()) / 1000;
        int pass = duration - due;

   /*     mPass.setText("" + pass + " seconds");
        mDuration.setText("" + duration + " seconds");
        mDue.setText("" + due + " seconds");*/
    }

    protected void initializeSeekBar() {
        seekBar.setMax(audioPlayer.getDuration() / 1000);

        mRunnable = new Runnable() {
            @Override
            public void run() {
                if (audioPlayer != null) {
                    int mCurrentPosition = audioPlayer.getCurrentPosition() / 1000; // In milliseconds
                    seekBar.setProgress(mCurrentPosition);
                    getAudioStats();
                }
                mHandler.postDelayed(mRunnable, 1000);
            }
        };
        mHandler.postDelayed(mRunnable, 1000);
    }

}
