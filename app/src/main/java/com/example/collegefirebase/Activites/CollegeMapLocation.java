package com.example.collegefirebase.Activites;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.collegefirebase.Model.College;
import com.example.collegefirebase.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CollegeMapLocation extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private DatabaseReference c_ref;
    String collegename;
    String collegelocation;
    String latitude,longitude;
    float default_zoom;
    private UiSettings uisetting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_college_map_location);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.college_location_map);
        mapFragment.getMapAsync(this);

        //database reference to retrieve latitude and longitude values
        c_ref = FirebaseDatabase.getInstance().getReference("collegedata");
        collegelocation = getIntent().getStringExtra("location");

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        uisetting = mMap.getUiSettings(); //need this to enable zoom controls and other features

        uisetting.setAllGesturesEnabled(true); //double tap etc
        uisetting.setZoomControlsEnabled(true); //zoom in and out
        uisetting.setCompassEnabled(true);

        //call this function to set the location
        locationEventListener();

    }

    private void locationEventListener(){
        c_ref.child(String.valueOf(collegelocation)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                College c = dataSnapshot.getValue(College.class);
                // to make sure objects are not null
                assert c != null;
                // get value of latitude and longitude from database, use child("location") to access the child of database
                latitude = dataSnapshot.child("location").getValue(College.class).getLatitude();
                longitude = dataSnapshot.child("location").getValue(College.class).getLongitude();
                collegename = dataSnapshot.getValue(College.class).getName();
                //convert the values to double as LatLng takes (double,double)
                double lat = Double.parseDouble(latitude);
                double lng = Double.parseDouble(longitude);
                default_zoom = 16.0f; //this will zoom to the location set
                //set LatLng with converted values
                LatLng college = new LatLng(lat,lng);
                //name of markeras college name
                mMap.addMarker(new MarkerOptions().position(college).title("Marker in " + collegename ));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(college,default_zoom));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(CollegeMapLocation.this, "Unsuccessful", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
