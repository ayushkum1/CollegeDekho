package com.example.collegefirebase.Activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.example.collegefirebase.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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


//        galleryView = ScrollGalleryView
//                .from((ScrollGalleryView) findViewById(R.id.scroll_gallery_view))
//                .settings(
//                        GallerySettings
//                                .from(getSupportFragmentManager())
//                                .thumbnailSize(100)
//                                .enableZoom(true)
//                                .build()
//                )
//                .add(image("https://cdn.motor1.com/images/mgl/byLpn/s2/lamborghini-aventador-svj-roadster.jpg"))
//                .add(image("https://upload.wikimedia.org/wikipedia/commons/3/3d/Aventador._%286675860749%29.jpg"))
//                .add(video("http://www.sample-videos.com/video/mp4/720/video123/mp4/240/big_buck_bunny_240p_1mb.mp4", R.drawable.av))
//                .build();


    }
}
