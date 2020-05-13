package com.example.collegefirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class CollegeDetails extends AppCompatActivity {

    private DatabaseReference collegeinforef;
    private TextView collegeabout, collegenamedetail, collegeaddress, collegedepartment, collegeplacements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_college_details);

        collegeabout = findViewById(R.id.college_about);
        collegenamedetail = findViewById(R.id.college_name);
        collegeaddress  = findViewById(R.id.college_address);
        collegedepartment = findViewById(R.id.college_department);
        collegeplacements = findViewById(R.id.college_placements);

        //initializing this app to the firebase
//        FirebaseOptions options = new FirebaseOptions.Builder()
//                .setApplicationId("1:214131280810:android:16b09942d8b19eca4079d3")
//                .setApiKey("AIzaSyDwulvsRr_6ny_gDmVnfFa0D9NAZRKiivE")
//                .setDatabaseUrl("https://collegedata-bc8d9.firebaseio.com/")
//                .setProjectId("collegedata-bc8d9").build();
//        FirebaseApp.initializeApp(getApplicationContext(),options,"UserApp");
//        FirebaseApp app = FirebaseApp.getInstance("UserApp");
       FirebaseDatabase userdatabase = FirebaseDatabase.getInstance();

        collegeinforef = userdatabase.getReference("collegedata");
        //getIntent to get the data from the previous(CollegeAdapter) intent.
        //this will help us to set the data from the database
        String collegename = getIntent().getStringExtra("name");

        collegeinforef.child(collegename).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //creating a college(model class) object to fetch details by calling respective constructors
                College c = dataSnapshot.getValue(College.class);
                assert c != null;
                collegenamedetail.setText(c.getName());
                collegeabout.setText(c.getAbout());
                collegedepartment.setText(c.getDepartments());
                collegeplacements.setText(c.getPlacements());
                collegeaddress.setText(c.getAddress());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("OnDatabase Error", databaseError.getMessage().toString());
                Toast.makeText(CollegeDetails.this, "Sorry", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
