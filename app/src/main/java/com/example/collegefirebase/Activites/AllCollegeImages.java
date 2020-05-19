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
    private TextView tvInfraPhotos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_college_images);
        ref = FirebaseDatabase.getInstance().getReference("collegedata");
        collegeimage = getIntent().getStringExtra("image"); // collegeimage stores the college id, need to change the name
        grid_image = findViewById(R.id.grid_image);
        tvInfraPhotos = findViewById(R.id.tvInf);

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
        tvInfraPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllCollegeImages.this, SlideImage.class);
                intent.putExtra("college_id", collegeimage);
                startActivity(intent);

            }
        });
    }
}



