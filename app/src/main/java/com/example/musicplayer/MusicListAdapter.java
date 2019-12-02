package com.example.musicplayer;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.ViewHolderSong> {
    Context context;
    ClickInterface mClickInterface;
    private ArrayList<Song> songsList;

    MusicListAdapter(Context context, ClickInterface clickInterface) {
        this.context = context;
        this.mClickInterface = clickInterface;
    }

    public void setSongs(ArrayList<Song> songs) {
        this.songsList = songs;

    }

    @NonNull
    @Override
    public ViewHolderSong onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item,
                parent, false);
        return new ViewHolderSong(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderSong holder, final int position) {
        holder.tvSongName.setText(songsList.get(position).getArtist());
        holder.llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickInterface.click(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return songsList.size();
    }

    static class ViewHolderSong extends RecyclerView.ViewHolder {
        ImageView ivSong;
        LinearLayout llMain;
        TextView tvSongName;

        ViewHolderSong(@NonNull View itemView) {
            super(itemView);
            ivSong = itemView.findViewById(R.id.ivSong);
            llMain = itemView.findViewById(R.id.llMain);
            tvSongName = itemView.findViewById(R.id.tvSongName);

        }
    }
}