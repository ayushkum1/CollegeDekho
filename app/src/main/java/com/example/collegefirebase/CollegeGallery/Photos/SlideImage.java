package com.example.collegefirebase.CollegeGallery.Photos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.example.collegefirebase.Model.College;
import com.example.collegefirebase.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.veinhorn.scrollgalleryview.ScrollGalleryView;
import com.veinhorn.scrollgalleryview.builder.GallerySettings;

import static com.veinhorn.scrollgalleryview.loader.picasso.dsl.DSL.image;
import static com.veinhorn.scrollgalleryview.loader.picasso.dsl.DSL.video;

public class SlideImage extends FragmentActivity {
    ScrollGalleryView galleryView;
    DatabaseReference ref;
    FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_image);
        String college_id = getIntent().getStringExtra("college_id");
        final String type = getIntent().getStringExtra("type");
        db = FirebaseDatabase.getInstance();
        ref = db.getReference("collegedata");

        ref.child(String.valueOf(college_id)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                College college = dataSnapshot.getValue(College.class);
                galleryView = ScrollGalleryView
                .from((ScrollGalleryView) findViewById(R.id.scroll_gallery_view))
                .settings(
                        GallerySettings
                                .from(getSupportFragmentManager())
                                .thumbnailSize(100)
                                .enableZoom(true)
                                .build()
                ).build();
                if(type.equals("infra")) {
                    for (String url : college.getInfra_imageurls()) {
                        galleryView.addMedia(image(url));
                    }
                }

                if(type.equals("lab")) {
                    for (String url : college.getLabs_url_list()) {
                        galleryView.addMedia(image(url));
                    }
                }

                if(type.equals("extra")) {
                    for (String url : college.getExtra_url_list()) {
                        galleryView.addMedia(image(url));
                    }
                }

                if(type.equals("hostel")) {
                    for (String url : college.getHostel_url_list()) {
                        galleryView.addMedia(image(url));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
