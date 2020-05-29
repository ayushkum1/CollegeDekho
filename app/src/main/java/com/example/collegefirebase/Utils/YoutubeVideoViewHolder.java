package com.example.collegefirebase.Utils;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegefirebase.R;
import com.example.collegefirebase.interfaces.ItemClickListener;

public class YoutubeVideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView textVideoTitle;
    ImageView imgThumbnail;
    ItemClickListener itemClickListener;
    public YoutubeVideoViewHolder(@NonNull View itemView) {
        super(itemView);
        textVideoTitle = itemView.findViewById(R.id.videoTitle);
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
