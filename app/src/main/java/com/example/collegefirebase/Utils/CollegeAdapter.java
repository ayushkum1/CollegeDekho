package com.example.collegefirebase.Utils;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegefirebase.Activites.CollegeDetails;
import com.example.collegefirebase.CollegeGallery.CollegeGallery;
import com.example.collegefirebase.MapsAPI.CollegeMapLocation;
import com.example.collegefirebase.Model.College;
import com.example.collegefirebase.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CollegeAdapter extends RecyclerView.Adapter<CollegeAdapter.CollegeViewHolder> {

    private Context c;
    private List<College> collegelist;

    //default constructor
  public CollegeAdapter(Context c, List<College> collegelist) {
        this.c = c;
        this.collegelist = collegelist;
    }

    @NonNull
    @Override
    //this will inflate the collegecardview xml file when called
    // college adapter object in main activity is used to call this function
    public CollegeAdapter.CollegeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CollegeViewHolder(LayoutInflater.from(c).inflate(R.layout.collegecardview,parent,false));
    }

    //this will set the data value to our textviews using holder
    @Override
    public void onBindViewHolder(@NonNull final CollegeAdapter.CollegeViewHolder holder, final int position) {
        holder.collegename.setText(collegelist.get(position).getName());
        holder.rating.setText(collegelist.get(position).getRating());
        //need to check if image urls is there or not, if empty, it will display the given url image.
        if(collegelist.get(position).getImage().isEmpty()){
            Picasso.with(c).load("https://w7.pngwing.com/pngs/174/558/png-transparent-black-sad-emoji-illustration-face-sadness-smiley-computer-icons-sad-child-people-emoticon.png")
                    .into(holder.collegeimage);
        }
        else{
            Picasso.with(c).load(collegelist.get(position).getImage()).into(holder.collegeimage);
        }
        //onclick listeners for buttons in the cardview
        holder.collegedetails_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent college_detail_intent = new Intent(c, CollegeDetails.class);
                //taking data from this intent to next using putExtra. this will send details based on childs position
                college_detail_intent.putExtra("name",collegelist.get(position).getId());
                c.startActivity(college_detail_intent);
            }
        });
        holder.collegegallery_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent college_gallery_intent = new Intent(c, CollegeGallery.class);
                college_gallery_intent.putExtra("gallery",collegelist.get(position).getId());
                c.startActivity(college_gallery_intent);
            }
        });
        holder.collegelocation_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent college_location_intent = new Intent(c, CollegeMapLocation.class);
                college_location_intent.putExtra("location",collegelist.get(position).getId());
                c.startActivity(college_location_intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return collegelist.size();
    }

    //view holder class
    class CollegeViewHolder extends RecyclerView.ViewHolder {
        TextView collegename,rating;
        ImageView collegeimage;
        Button collegedetails_btn,collegegallery_btn, collegelocation_btn;

        CollegeViewHolder(@NonNull View itemView) {
            super(itemView);
            collegename = itemView.findViewById(R.id.collegename);
            rating = itemView.findViewById(R.id.rating);
            collegeimage = itemView.findViewById(R.id.collegeimage);
            collegedetails_btn = itemView.findViewById(R.id.college_details);
            collegegallery_btn = itemView.findViewById(R.id.college_gallery);
            collegelocation_btn = itemView.findViewById(R.id.college_location);
        }
    }
}
