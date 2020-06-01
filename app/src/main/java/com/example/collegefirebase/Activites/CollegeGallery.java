package com.example.collegefirebase.Activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.collegefirebase.Fragments.YoutubeVideoList;
import com.example.collegefirebase.Model.College;
import com.example.collegefirebase.Utils.CollegeImageGrid;
import com.example.collegefirebase.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CollegeGallery extends AppCompatActivity {
    public CollegeImageGrid imagegrid;
    private static final String TAG = "CollegeGallery";
    public GridView grid_image, grid_video;
    public DatabaseReference ref;
    private String collegeid;
    private TextView moreimages, morevideos;
    private String playlistid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_college_gallery);

        ref = FirebaseDatabase.getInstance().getReference("collegedata");
        //this will get the data from previous intent
        collegeid = getIntent().getStringExtra("gallery");
        grid_image = findViewById(R.id.grid_image);

        moreimages = findViewById(R.id.more_images);
        morevideos = findViewById(R.id.more_videos);

        //a list of string will  be passed to  imagegrid object
        ref.child(String.valueOf(collegeid)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               //object of College class to get getImageurls() which has the list of urls
                College clg = dataSnapshot.getValue(College.class);

                //setting the list to imagegrid, passing url from this activity to imageview.
                imagegrid = new CollegeImageGrid(CollegeGallery.this,clg.getImageurls());
                //setting adapter to grid with the list of urls
                grid_image.setAdapter(imagegrid); //check error, getCount is null, crashes application.
                //extracting playlist id
                String playid = getYoutubeVideoId(clg.getVideourls());
                // send  playlist id in the bundle as arguments. we will use keys to extract the value of key.
                Bundle bun = new Bundle();
                YoutubeVideoList firstfrag = new YoutubeVideoList();
                bun.putString("listid", playid); //sending to fragment
                firstfrag.setArguments(bun);
                // this will save the changes and keep the value even if screen is rotated
                getSupportFragmentManager().beginTransaction().add(R.id.youtube_frag, firstfrag).commit();
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
                image_in.putExtra("image",collegeid);
                startActivity(image_in);
            }
        });

        //will take to activity with only playlist video list fragment
        morevideos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // passing the college id,  which will be used to extract playlist id fro videourls.
                Intent in = new Intent(CollegeGallery.this, CompleteVideoList.class);
                in.putExtra("collegeid", collegeid);
                startActivity(in);
            }
        });

    }

    //function to extract playlist id
    public static String getYoutubeVideoId(String youtubeUrl) {
        String video_id = "";
        if (youtubeUrl != null && youtubeUrl.trim().length() > 0 && youtubeUrl.startsWith("http")) {
            // check for "list=", extract the value of list
            String expression = "^.*?(?:list)=(.*?)(?:&|$)";
            CharSequence input = youtubeUrl;
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(input);
            if (matcher.matches()) {
                //we need only list and so we are checking for 1 group that is list=
                String group1 = matcher.group(1);
                video_id = group1;
            }
        }
        return video_id;
    }

}
