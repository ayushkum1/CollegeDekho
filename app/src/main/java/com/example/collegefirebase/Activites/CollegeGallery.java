package com.example.collegefirebase.Activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.collegefirebase.Model.College;
import com.example.collegefirebase.Utils.CollegeImageGrid;
import com.example.collegefirebase.R;
import com.example.collegefirebase.Utils.VideoGrid;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CollegeGallery extends AppCompatActivity {
    public CollegeImageGrid imagegrid;
    public VideoGrid videogrid;
    private static final String TAG = "CollegeGallery";
    public GridView grid_image, grid_video;
    public DatabaseReference ref;
    private String collegeimage;
    private TextView moreimages, morevideos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_college_gallery);
        ref = FirebaseDatabase.getInstance().getReference("collegedata");
        //this will get the data from previous intent
        collegeimage = getIntent().getStringExtra("gallery");
        grid_image = findViewById(R.id.grid_image);
        grid_video = findViewById(R.id.grid_video); //for grid view of videos

        moreimages = findViewById(R.id.more_images);
        morevideos = findViewById(R.id.more_videos);

        //a list of string will  be passed to  imagegrid object
        ref.child(String.valueOf(collegeimage)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               //object of College class to get getImageurls() which has the list of urls
                College clg = dataSnapshot.getValue(College.class);
                //setting the list to imagegrid, passing url from this activity to imageview.
                imagegrid = new CollegeImageGrid(CollegeGallery.this,clg.getImageurls());
                //setting adapter to grid with the list of urls
                grid_image.setAdapter(imagegrid); //check error, getCount is null, crashes application.
                //for video,currently taking same image urls, checking layout
                videogrid = new VideoGrid(CollegeGallery.this,clg.getVideourls());
                grid_video.setAdapter(videogrid);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(CollegeGallery.this, "No images", Toast.LENGTH_SHORT).show();
            }
        });

        moreimages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent image_in = new Intent(CollegeGallery.this,AllCollegeImages.class);
                image_in.putExtra("image",collegeimage);
                startActivity(image_in);
            }
        });

        morevideos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent video_in = new Intent(CollegeGallery.this,CollegeAllVideos.class);
                video_in.putExtra("video",collegeimage);
                startActivity(video_in);
            }
        });
    }

}
