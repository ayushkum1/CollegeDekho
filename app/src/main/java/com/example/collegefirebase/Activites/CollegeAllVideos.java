package com.example.collegefirebase.Activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.Toast;

import com.example.collegefirebase.Model.College;
import com.example.collegefirebase.Utils.CollegeFullImages;
import com.example.collegefirebase.R;
import com.example.collegefirebase.Utils.CollegeFullVideos;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CollegeAllVideos extends AppCompatActivity {
    public CollegeFullVideos videogrid;
    private static final String TAG = "CollegeGallery";
    public GridView grid_video;
    public DatabaseReference ref;
    private String collegevideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_college_all_videos);
        ref = FirebaseDatabase.getInstance().getReference("collegedata");
        collegevideo = getIntent().getStringExtra("video");
        grid_video = findViewById(R.id.grid_video);

        ref.child(String.valueOf(collegevideo)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                College clg = dataSnapshot.getValue(College.class);
                videogrid = new CollegeFullVideos(CollegeAllVideos.this,clg.getVideourls());
                grid_video.setAdapter(videogrid); //check error, getCount is null, crashes application.
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(CollegeAllVideos.this, "No images", Toast.LENGTH_SHORT).show();
            }
        });
    }
}



