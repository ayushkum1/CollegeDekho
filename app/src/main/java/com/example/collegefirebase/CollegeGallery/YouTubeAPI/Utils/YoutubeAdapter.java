package com.example.collegefirebase.CollegeGallery.YouTubeAPI.Utils;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegefirebase.CollegeGallery.YouTubeAPI.YoutubeVideoPlayer;
import com.example.collegefirebase.CollegeGallery.YouTubeAPI.Model.YoutubeVideoModel;
import com.example.collegefirebase.R;
import com.example.collegefirebase.CollegeGallery.YouTubeAPI.Interfaces.ItemClickListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class YoutubeAdapter extends RecyclerView.Adapter<YoutubeVideoViewHolder> {
    Context context;
    List<YoutubeVideoModel> vidList = new ArrayList<>();

    public YoutubeAdapter(Context context, List<YoutubeVideoModel> vidList) {
        this.context = context;
        this.vidList = vidList;
    }

    @NonNull
    @Override
    public YoutubeVideoViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_youtube_video_view,parent, false);
        return new YoutubeVideoViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull YoutubeVideoViewHolder holder, int position) {
        Picasso.with(context).load(vidList.get(position).getUrl()).into(holder.imgThumbnail);
        holder.desc.setText(vidList.get(position).getDesc());
        holder.textVideoTitle.setText(vidList.get(position).getTitle());
        holder.setItemClickListner(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(context, YoutubeVideoPlayer.class);
                intent.putExtra("videoId",vidList.get(position).getVideoId()); // pass the videoid to play the particular video
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(vidList != null){
            return vidList.size();
    }
        else{
            return 0;
        }
    }
}