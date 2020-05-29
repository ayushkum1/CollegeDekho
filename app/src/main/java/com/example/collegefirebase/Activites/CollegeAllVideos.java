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

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CollegeAllVideos extends AppCompatActivity {
    private static final String TAG = "CollegeGallery";
    public DatabaseReference ref;
    private String collegevideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_college_all_videos);
        ref = FirebaseDatabase.getInstance().getReference("collegedata");
        collegevideo = getIntent().getStringExtra("video");

        ref.child(String.valueOf(collegevideo)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                College clg = dataSnapshot.getValue(College.class);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(CollegeAllVideos.this, "No images", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static String getYoutubeVideoId(String youtubeUrl) {
        String video_id = "";
        if (youtubeUrl != null && youtubeUrl.trim().length() > 0 && youtubeUrl.startsWith("http")) {



            String expression = "^.*?(?:list)=(.*?)(?:&|$)";

            CharSequence input = youtubeUrl;
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(input);
            if (matcher.matches()) {
                String groupIndex1 = matcher.group(1);
                video_id = groupIndex1;
            }
        }
        return video_id;
    }
}



