package com.example.collegefirebase.Activites;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
    String latitude,longitude, lat, lng;
    float default_zoom;
    Button hotel, restaurant, hospital, bank;
    private UiSettings uisetting;

    public void setLat(String lat){
        this.lat = lat;
    }

    public String getLat(){
        return lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_college_map_location);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.college_location_map);
        mapFragment.getMapAsync(this);

        hospital = findViewById(R.id.hospital);
        hotel = findViewById(R.id.hotel);
        restaurant = findViewById(R.id.restaurant);
        bank = findViewById(R.id.bank);

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
                // for sending data to next activity
                setLat(latitude);
                setLng(longitude);

                collegename = dataSnapshot.getValue(College.class).getName();
                //convert the values to double as LatLng takes (double,double)
                double lt = Double.parseDouble(latitude);
                double ln = Double.parseDouble(longitude);

                default_zoom = 16.0f; //this will zoom to the location set
                //set LatLng with converted values
                LatLng college = new LatLng(lt,ln);
                //name of markeras college name
                mMap.addMarker(new MarkerOptions().position(college).title("Marker in " + collegename ));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(college,default_zoom));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(CollegeMapLocation.this, "Unsuccessful", Toast.LENGTH_SHORT).show();
            }
        });

        hospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lt = getLat();
                String ln = getLng();
                String heading = "Hospitals Near By This College";
                Intent in = new Intent(CollegeMapLocation.this, PlacesNearBy.class);
                in.putExtra("lat", lt);
                in.putExtra("lng", ln);
                in.putExtra("type", "hospital");
                in.putExtra("heading", heading);
                startActivity(in);
            }
        });

        hotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lt = getLat();
                String ln = getLng();
                String heading = "Hotels Near By This College";
                Intent in = new Intent(CollegeMapLocation.this, PlacesNearBy.class);
                in.putExtra("lat", lt);
                in.putExtra("lng", ln);
                in.putExtra("type", "hotel");
                in.putExtra("heading", heading);
                startActivity(in);
            }
        });

        restaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lt = getLat();
                String ln = getLng();
                String heading = "Restaurants Near By This College";
                Intent in = new Intent(CollegeMapLocation.this, PlacesNearBy.class);
                in.putExtra("lat", lt);
                in.putExtra("lng", ln);
                in.putExtra("type", "restaurant");
                in.putExtra("heading", heading);
                startActivity(in);
            }
        });

        bank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lt = getLat();
                String ln = getLng();
                String heading = "Banks Near By This College";
                Intent in = new Intent(CollegeMapLocation.this, PlacesNearBy.class);
                in.putExtra("lat", lt);
                in.putExtra("lng", ln);
                in.putExtra("type", "bank");
                in.putExtra("heading", heading);
                startActivity(in);
            }
        });
    }
}
