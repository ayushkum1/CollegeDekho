package com.example.collegefirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.UUID;

public class SignUpDetails extends AppCompatActivity {

    DatabaseReference ref;
    private FirebaseAuth auth;
    EditText userfirstname, userlastname, useremail, userpwd, usercnfmpwd, userphoneno;
    Button signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ref = FirebaseDatabase.getInstance().getReference("UserData");
        auth = FirebaseAuth.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_details);

        userfirstname = findViewById(R.id.user_first_name);
        userlastname = findViewById(R.id.user_last_name);
        useremail = findViewById(R.id.user_email);
        userpwd = findViewById(R.id.user_password);
        usercnfmpwd = findViewById(R.id.user_confirm_pass);
        userphoneno = findViewById(R.id.user_phone_number);
        signup = findViewById(R.id.user_signup);


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUserInformation();
            }
        });
    }

    private void addUserInformation(){
        String userid, fname, lname, email, password, cpwd, phone;

        userid = UUID.randomUUID().toString();
        fname = userfirstname.getText().toString().trim();
        lname = userlastname.getText().toString().trim();
        email = useremail.getText().toString().trim();
        password = userpwd.getText().toString().trim();
        cpwd = usercnfmpwd.getText().toString().trim();
        phone = userphoneno.getText().toString().trim();

        if(TextUtils.isEmpty(fname)){
            Toast.makeText(getApplicationContext(), "Pls Enter First Name", Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(lname)){
            Toast.makeText(getApplicationContext(), "Pls Enter last Name", Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(email)){
            Toast.makeText(getApplicationContext(), "Enter email", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(getApplicationContext(), "Pls enter the password", Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(cpwd)){
            Toast.makeText(getApplicationContext(), "Pls confirm the password", Toast.LENGTH_LONG).show();
            return;
        }

        if(!password.equals(cpwd)){
            Toast.makeText(getApplicationContext(), "password do not match", Toast.LENGTH_SHORT).show();
        }

        if(TextUtils.isEmpty(phone)){
            Toast.makeText(getApplicationContext(), "Phone number is empty", Toast.LENGTH_LONG).show();
            return;
        }

        if(password.length() == 0){
            Toast.makeText(getApplicationContext(),"Please enter your password",Toast.LENGTH_LONG).show();
            return;
        }

        if(password.length()<8){
            Toast.makeText(getApplicationContext(),"Please enter passwords having more than 8 characters",Toast.LENGTH_LONG).show();
            return;
        }

        else{
            Users user = new Users(userid, fname, lname, email, password, phone);

            ref.child(userid).setValue(user);
            Toast.makeText(this, "Details added", Toast.LENGTH_SHORT).show();

            auth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(SignUpDetails.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                startActivity(new Intent(SignUpDetails.this, MainActivity.class));
                                finish();
                            }
                            else{
                                //to catch error
                                Log.e("signup", "onComplete: " +task.getException().getMessage().toString() );
                                Toast.makeText(SignUpDetails.this, "ERROR",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }



//        if(TextUtils.isEmpty(fname)){
//            if(TextUtils.isEmpty(lname)){
//                if(TextUtils.isEmpty(email)){
//                    if(TextUtils.isEmpty(password)){
//                            if(TextUtils.isEmpty(cpwd)){
//                                if(password == cpwd){
//                                    Users user = new Users(userid, fname, lname, email, password, phone);
//
//                                    ref.child(userid).setValue(user);
//                                    Toast.makeText(this, "Details added", Toast.LENGTH_SHORT).show();
//
//                                    auth.createUserWithEmailAndPassword(email,password)
//                                        .addOnCompleteListener(SignUpDetails.this, new OnCompleteListener<AuthResult>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<AuthResult> task) {
//                                            if(task.isSuccessful()){
//                                                startActivity(new Intent(SignUpDetails.this, MainActivity.class));
//                                                finish();
//                                            }
//                                            else{
//                                                //to catch error
//                                                Log.e("signup", "onComplete: " +task.getException().getMessage().toString() );
//                                                Toast.makeText(SignUpDetails.this, "ERROR",Toast.LENGTH_LONG).show();
//                                            }
//                                        }
//                                    });
//                                }
//                                else{
//                                    Toast.makeText(this, "password do not match", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                            else{
//                                Toast.makeText(this, "enter password", Toast.LENGTH_SHORT).show();
//                            }
//                    }
//                    else{
//                        Toast.makeText(this, "enter password", Toast.LENGTH_SHORT).show();
//
//                    }
//                }
//                else{
//                    Toast.makeText(this, "email is empty", Toast.LENGTH_SHORT).show();
//                }
//            }
//            else{
//                Toast.makeText(this, "name is empty", Toast.LENGTH_SHORT).show();
//            }
//        }
//        else {
//            Toast.makeText(this, "name is empty", Toast.LENGTH_SHORT).show();
//        }

    }
}
