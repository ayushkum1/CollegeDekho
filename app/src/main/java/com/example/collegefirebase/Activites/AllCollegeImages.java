package com.example.collegefirebase.Activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.Toast;

import com.example.collegefirebase.Model.College;
import com.example.collegefirebase.Utils.CollegeFullImages;
import com.example.collegefirebase.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AllCollegeImages extends AppCompatActivity {
    public CollegeFullImages imagegrid;
    private static final String TAG = "CollegeGallery";
    public GridView grid_image;
    public DatabaseReference ref;
    private String collegeimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_college_images);
        ref = FirebaseDatabase.getInstance().getReference("collegedata");
        collegeimage = getIntent().getStringExtra("image");
        grid_image = findViewById(R.id.grid_image);

        ref.child(String.valueOf(collegeimage)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                College clg = dataSnapshot.getValue(College.class);
                imagegrid = new CollegeFullImages(AllCollegeImages.this,clg.getImageurls());
                grid_image.setAdapter(imagegrid); //check error, getCount is null, crashes application.
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(AllCollegeImages.this, "No images", Toast.LENGTH_SHORT).show();
            }
        });
    }
}



