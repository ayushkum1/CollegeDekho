package com.example.collegefirebase.Activites;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.collegefirebase.Common.CurrentUser;
import com.example.collegefirebase.Model.Users;
import com.example.collegefirebase.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.LoginStatusCallback;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.mindrot.jbcrypt.BCrypt;

import java.util.Arrays;

public class SignUp extends AppCompatActivity {

    EditText signupmail,signuppwd;
    Button signupbtn,loginbtn,googlesignin;
    TextView forgotpass;
    private FirebaseAuth auth;
    DatabaseReference ref;
    private static final int RC_SIGN_IN = 1;
    private static final String TAG = "signup";
    private static final String EMAIL = "email";
    private Button facebookloginButton;
    private CallbackManager callbackManager;

    @SuppressLint("WrongViewCast")

    @Override
    public void onStart(){
        //check for already logged in
        super.onStart();
        //checks if the user is of firebase or not. if not firebase then checks for google sign in
        if (auth.getCurrentUser() == null){
            GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
            updateUIgoogle(account);
        }
        // if not google user or is a firebase user, update UI with firebase user
        else {  FirebaseUser user = auth.getCurrentUser();
            updateUI(user);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        //for google sign in
        final GoogleSignInClient mGoogleSignInClient;
        //callback for facebook login
        callbackManager = CallbackManager.Factory.create();
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();

        ref = FirebaseDatabase.getInstance().getReference("UserData");

        signupmail = findViewById(R.id.email_edt_text);
        signuppwd = findViewById(R.id.pass_edt_text);
        auth = FirebaseAuth.getInstance();
        googlesignin = findViewById(R.id.google_signin_button); //google sign in
        signupbtn = findViewById(R.id.signup_btn);
        loginbtn = findViewById(R.id.login_btn);
        forgotpass = findViewById(R.id.forgotpwd);
        facebookloginButton = findViewById(R.id.facebook_login_button); // facebook sign in

        // facebook sign in
        //more details to be taken like profile picture etc from setPermissions. this will be as a list
        ((LoginButton) facebookloginButton).setPermissions(Arrays.asList(EMAIL));
        ((LoginButton) facebookloginButton).registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException error) {
            }
        });

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // facebook login button changes its text to logout and performs logout function.
                //we have created a custom button in options, so dont want it. hence setting its visibility to gone.
                facebookloginButton.setVisibility(View.GONE);
                startActivity(new Intent(SignUp.this, MainActivity.class));
                Toast.makeText(SignUp.this, "success (LoginManager)", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                Toast.makeText(SignUp.this, "cancelled", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(SignUp.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

        //this is to check for express login.
        LoginManager.getInstance().retrieveLoginStatus(this, new LoginStatusCallback() {
            @Override
            public void onCompleted(AccessToken accessToken) {
                // User was previously logged in, can log them in directly here.
                // If this callback is called, a popup notification appears that says
                // "Logged in as <User Name>
            }
            @Override
            public void onFailure() {
                // No access token could be retrieved for the user
            }
            @Override
            public void onError(Exception exception) {
                // An error occurred
            }
        });

        //requesting basic details of user like email and others, currently requesting only  email
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().build();

        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);

        //login button, logins with email and id.
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = signupmail.getText().toString();
                final String pwd = signuppwd.getText().toString();
                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(pwd)) {
                    Toast.makeText(SignUp.this, "Enter full details", Toast.LENGTH_LONG).show();
                    return;
                }
                else{
                    ref.child(email.replace(".", "_")).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Users user = dataSnapshot.getValue(Users.class);
                            //crypting the password and matching it with the crypted password stored in database
                            if(BCrypt.checkpw(pwd,user.getPassword())){
                                CurrentUser.currentUser = user;
                                startActivity(new Intent(SignUp.this, MainActivity.class));
                                Toast.makeText(SignUp.this, "Welcome " +user.getFname(), Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(SignUp.this, "Check your credentials", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
//                    auth.signInWithEmailAndPassword(email,pwd)
//                            .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
//                                @Override
//                                public void onComplete(@NonNull Task<AuthResult> task) {
//                                    if(task.isSuccessful()){
//                                        Toast.makeText(SignUp.this,"success",Toast.LENGTH_LONG).show();
//                                        Intent myintent = new Intent(SignUp.this,MainActivity.class);
//                                        startActivity(myintent);
//                                        finish();
//                                    }
//                                    else{
//                                        Toast.makeText(SignUp.this,"login failed",Toast.LENGTH_LONG).show();
//                                    }
//                                }
//                            });
                }
            }
        });

        //sign up button. signs up with email and password in firebase
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent signupintent = new Intent(SignUp.this,SignUpDetails.class);
                startActivity(signupintent);
            }
        });

        // google sign in buttoon.
        googlesignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signin();
            }
            private void signin() {
                Intent intentsignin = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(intentsignin, RC_SIGN_IN);//onActivityResult
            }

        });

        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUp.this,ResetPassword.class));
            }
        });

    }

    //google signed in object
    // this object will have details of the user
    @Override
    public void onActivityResult(int requestcode, int resultcode, Intent data){
        super.onActivityResult(requestcode,resultcode,data);

        //this is for facebook and signs in the user with facebook
        callbackManager.onActivityResult(requestcode, resultcode, data);

        // this is for google and signs in the user with google
        if(requestcode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask){
        try{
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            updateUIgooglesignedin(account);
        }
        catch(ApiException e){
            Log.w(TAG,"signin failed code="+ e.getStatusCode());
            updateUIgooglesignedin(null); //3rd updateUi function
        }
    }

    //updateui for firebase
    private void updateUI(FirebaseUser user){
        user = auth.getCurrentUser();
        if(user!=null){
            Toast.makeText(SignUp.this,"success",Toast.LENGTH_LONG).show();
            startActivity(new Intent(SignUp.this,MainActivity.class));
        }
        else{
            Toast.makeText(SignUp.this,"Pls login or signup", Toast.LENGTH_LONG).show();
        }
    }

    //2nd update ui function (1st  for google)
    private void updateUIgoogle(@Nullable GoogleSignInAccount account){
        if(account != null){
            Toast.makeText(SignUp.this,"success",Toast.LENGTH_LONG).show();
            startActivity(new Intent(SignUp.this,MainActivity.class));
        }
        else{
            Log.e(TAG, "updateUIgooglesignedin: google sign in failed" );
        }
    }

    //3rd updateUI function
    private void updateUIgooglesignedin(GoogleSignInAccount account) {

        if(account != null){
            Toast.makeText(SignUp.this,"success",Toast.LENGTH_LONG).show();
            startActivity(new Intent(SignUp.this,MainActivity.class));
        }
        else{
            Log.e(TAG, "updateUIgooglesignedin: google sign in failed" );
        }

    }

}

