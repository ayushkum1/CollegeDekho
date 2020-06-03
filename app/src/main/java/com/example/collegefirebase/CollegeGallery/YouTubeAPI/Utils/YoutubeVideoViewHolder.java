package com.example.collegefirebase.CollegeGallery.YouTubeAPI.Utils;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegefirebase.R;
import com.example.collegefirebase.CollegeGallery.YouTubeAPI.Interfaces.ItemClickListener;

public class YoutubeVideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView textVideoTitle, desc;
    ImageView imgThumbnail;
    ItemClickListener itemClickListener;
    public YoutubeVideoViewHolder(@NonNull View itemView) {
        super(itemView);
        textVideoTitle = itemView.findViewById(R.id.videoTitle);
        desc = itemView.findViewById(R.id.description);
        imgThumbnail = itemView.findViewById(R.id.imgThumbnail);
        itemView.setOnClickListener(this);
    }
    // custom click listener
    public void setItemClickListner(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }
    // provided by android itself
    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition());
    }
}
