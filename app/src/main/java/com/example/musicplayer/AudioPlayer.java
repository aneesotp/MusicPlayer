package com.example.musicplayer;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

public class AudioPlayer {
    int length;
    private MediaPlayer mMediaPlayer;

    void resume() {
        mMediaPlayer.seekTo(length);
        mMediaPlayer.start();
    }

    void pause(Context context, String name) {
        mMediaPlayer.pause();
        length = mMediaPlayer.getCurrentPosition();
    }


    void stop() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
        }
    }

    // mothod for raw folder (R.raw.fileName)
    public void play(Context context, int rid) {
        stop();

        mMediaPlayer = MediaPlayer.create(context, rid);
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                stop();
            }
        });

        mMediaPlayer.start();
    }


    void play(Context context, String name) {
        stop();
        //mMediaPlayer = MediaPlayer.create(c, rid);
        mMediaPlayer = MediaPlayer.create(context, Uri.parse(name));
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                stop();
            }
        });
        mMediaPlayer.start();
    }

    void destroy() {
        stop();
    }

    int getDuration() {
        return mMediaPlayer.getDuration();
    }

    int getCurrentPosition() {
        return mMediaPlayer.getCurrentPosition();
    }

    void seekToPosition(int position) {
        mMediaPlayer.seekTo(position);

    }
}