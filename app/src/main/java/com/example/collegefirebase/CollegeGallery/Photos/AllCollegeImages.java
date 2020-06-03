package com.example.collegefirebase.CollegeGallery.Photos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.collegefirebase.CollegeGallery.Photos.Utils.Grid.ExtraPhotos;
import com.example.collegefirebase.CollegeGallery.Photos.Utils.Grid.HostelPhotos;
import com.example.collegefirebase.CollegeGallery.Photos.Utils.Grid.LabsPhotos;
import com.example.collegefirebase.Model.College;
import com.example.collegefirebase.CollegeGallery.Photos.Utils.Grid.CollegeFullImages;
import com.example.collegefirebase.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AllCollegeImages extends AppCompatActivity {
    public CollegeFullImages imagegrid;
    public ExtraPhotos extraimagegrid;
    public HostelPhotos hostelimagegrid;
    public LabsPhotos labsimagegrid;
    private static final String TAG = "com/example/collegefirebase/CollegeGallery";
    public GridView grid_image, labs_grid, extras_grid, hostel_grid;
    public DatabaseReference ref;
    private String collegeimage;
    private TextView tvInfraPhotos, hostel_more, labs_more, extras_more;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_college_images);
        ref = FirebaseDatabase.getInstance().getReference("collegedata");
        collegeimage = getIntent().getStringExtra("image"); // collegeimage stores the college id, need to change the name
        //this is for infra photos
        grid_image = findViewById(R.id.grid_image);
        tvInfraPhotos = findViewById(R.id.tvInf);

        labs_grid = findViewById(R.id.labs_grid_image);
        labs_more = findViewById(R.id.labs_more);

        hostel_grid = findViewById(R.id.hostel_grid_image);
        hostel_more =findViewById(R.id.hostel_more);

        extras_grid = findViewById(R.id.extra_grid_image);
        extras_more = findViewById(R.id.extra_more);

        ref.child(String.valueOf(collegeimage)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                College clg = dataSnapshot.getValue(College.class);
                assert clg != null;
                imagegrid = new CollegeFullImages(AllCollegeImages.this,clg.getInfra_imageurls());
                grid_image.setAdapter(imagegrid); //check error, if getCount is null, crashes application.
                //set grid down here for all the options

                hostelimagegrid = new HostelPhotos(AllCollegeImages.this, clg.getHostel_url_list());
                hostel_grid.setAdapter(hostelimagegrid);

                labsimagegrid = new LabsPhotos(AllCollegeImages.this, clg.getLabs_url_list());
                labs_grid.setAdapter(labsimagegrid);

                extraimagegrid = new ExtraPhotos(AllCollegeImages.this, clg.getExtra_url_list());
                extras_grid.setAdapter(extraimagegrid);
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
                intent.putExtra("type", "infra");
                startActivity(intent);

            }
        });
        //set their more buttons here...

        labs_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllCollegeImages.this, SlideImage.class);
                intent.putExtra("college_id", collegeimage);
                intent.putExtra("type", "lab");
                startActivity(intent);
            }
        });

        hostel_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllCollegeImages.this, SlideImage.class);
                intent.putExtra("college_id", collegeimage);
                intent.putExtra("type", "hostel");
                startActivity(intent);
            }
        });

        extras_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllCollegeImages.this, SlideImage.class);
                intent.putExtra("college_id", collegeimage);
                intent.putExtra("type", "extra");
                startActivity(intent);
            }
        });
    }
}



