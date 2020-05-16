package com.example.collegefirebase.Activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.collegefirebase.Common.CurrentUser;
import com.example.collegefirebase.Model.College;
import com.example.collegefirebase.Utils.CollegeAdapter;
import com.example.collegefirebase.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInOptions gso;
    private FirebaseAuth auth;
    public RecyclerView recyclerView;
    public DatabaseReference collegereference;
    public CollegeAdapter collegeAdapter;
    public List<College> collegelist = new ArrayList<>(); //declaration will be of this type and not only List<>

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(this, CurrentUser.currentUser.getFname(), Toast.LENGTH_SHORT).show();
        //for logout from the options menu
        auth = FirebaseAuth.getInstance();
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        //recycler layout view for cardviews of colleges
        recyclerView = findViewById(R.id.recycler);
        //create a database reference, that will also be our query
        collegereference = FirebaseDatabase.getInstance().getReference("collegedata"); //this is our query, this will take to the node 'colleges' in firebase
        //set recycler layout manager after that initialise an empty adapter
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //for college adapter
        collegeAdapter = new CollegeAdapter(MainActivity.this,collegelist);
        recyclerView.setAdapter(collegeAdapter); //setting empty adapter

        // value event listener for getting data from database reference
        // adding the data to collegelist
        collegereference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    collegelist.add(dataSnapshot1.getValue(College.class));//adding data to list
                }
                recyclerView.setAdapter(collegeAdapter);// set the new adapter with data
                collegeAdapter.notifyDataSetChanged(); // to identify and implement the changes on database

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "unsuccessful", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_options_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        switch (item.getItemId()){

            case R.id.logout:
            {
                if(auth.getCurrentUser() == null){
                    mGoogleSignInClient.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(MainActivity.this, "logged out", Toast.LENGTH_LONG).show();
                            Intent logoutintent = new Intent(MainActivity.this, SignUp.class);
                            startActivity(logoutintent);
                        }
                    }).addOnFailureListener(this, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

                }
                else{
                    auth.signOut();
                }
            }
            break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if(CurrentUser.currentUser != null){
            Intent i = new Intent(Intent.ACTION_MAIN);
            i.addCategory(Intent.CATEGORY_HOME);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        }
    }
}
