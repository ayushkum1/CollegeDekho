package com.example.collegefirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CollegeGallery extends AppCompatActivity {
    public CollegeImageGrid imagegrid;
    private static final String TAG = "CollegeGallery";
    public GridView grid;
    public DatabaseReference ref;
    private String collegeimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_college_gallery);
        ref = FirebaseDatabase.getInstance().getReference("collegedata");
        //this will get the data from previous intent
        collegeimage = getIntent().getStringExtra("gallery");
        grid = findViewById(R.id.grid);
        //a list of string will  be passed to  imagegrid object
        ref.child(String.valueOf(collegeimage)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               //object of College class to get getImageurls() which has the list of urls
                College clg = dataSnapshot.getValue(College.class);
                //setting the list to imagegrid.
                imagegrid = new CollegeImageGrid(CollegeGallery.this,clg.getImageurls());
                //setting adapter to grid with the list of urls
                grid.setAdapter(imagegrid);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(CollegeGallery.this, "No images", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
