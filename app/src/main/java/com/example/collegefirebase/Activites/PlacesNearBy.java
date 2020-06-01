package com.example.collegefirebase.Activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.collegefirebase.Fragments.PlacesNearByFragment;
import com.example.collegefirebase.Fragments.YoutubeVideoList;
import com.example.collegefirebase.R;

public class PlacesNearBy extends AppCompatActivity {

    String latitude, longitude, type, heading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places_near_by);

        Intent in = getIntent();
        latitude = in.getStringExtra("lat");
        longitude = in.getStringExtra("lng");
        type = in.getStringExtra("type");
        heading = in.getStringExtra("heading");

        Bundle bun = new Bundle();
        PlacesNearByFragment frag = new PlacesNearByFragment();
        bun.putString("lat", latitude);
        bun.putString("lng", longitude);
        bun.putString("type", type);
        bun.putString("heading", heading);
        frag.setArguments(bun);
        getSupportFragmentManager().beginTransaction().add(R.id.nearby_frag, frag).commit();
    }
}
