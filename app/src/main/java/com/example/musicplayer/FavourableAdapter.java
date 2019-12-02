package com.example.musicplayer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FavourableAdapter extends RecyclerView.Adapter<FavourableAdapter.ViewHolderSong> {

    List<Song> songsList;
    Context context;
    ClickInterface mClickInterface;

    public FavourableAdapter(List<Song> songsList, Context context, ClickInterface mClickInterface) {
        this.songsList = songsList;
        this.context = context;
        this.mClickInterface = mClickInterface;
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


        holder.tvSongName.setOnClickListener(new View.OnClickListener() {
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

    public class ViewHolderSong extends RecyclerView.ViewHolder {
        ImageView ivSong;
        TextView tvSongName;

        public ViewHolderSong(@NonNull View itemView) {
            super(itemView);
            ivSong = itemView.findViewById(R.id.ivSong);
            tvSongName = itemView.findViewById(R.id.tvSongName);

        }
    }
}
