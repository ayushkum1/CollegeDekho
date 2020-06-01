package com.example.collegefirebase.Activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.collegefirebase.Fragments.YoutubeVideoList;
import com.example.collegefirebase.Model.College;
import com.example.collegefirebase.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CompleteVideoList extends AppCompatActivity {

    DatabaseReference ref;
    String playid, collegeid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_video_list);
        ref = FirebaseDatabase.getInstance().getReference("collegedata");
        collegeid = getIntent().getStringExtra("collegeid");

        ref.child(String.valueOf(collegeid)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                College clg = dataSnapshot.getValue(College.class);
                playid = clg.getVideourls();
                String id = getYoutubeVideoId(playid);
                Bundle bun = new Bundle();
                YoutubeVideoList firstfrag = new YoutubeVideoList();
                bun.putString("listid", id);
                firstfrag.setArguments(bun);
                getSupportFragmentManager().beginTransaction().add(R.id.youtube_frag, firstfrag).commit();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //function to extract playlist id
    public static String getYoutubeVideoId(String youtubeUrl) {
        String video_id = "";
        if (youtubeUrl != null && youtubeUrl.trim().length() > 0 && youtubeUrl.startsWith("http")) {

            String expression = "^.*?(?:list)=(.*?)(?:&|$)";

            CharSequence input = youtubeUrl;
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(input);
            if (matcher.matches()) {
                String group1 = matcher.group(1);
                video_id = group1;
            }
        }
        return video_id;
    }
}
