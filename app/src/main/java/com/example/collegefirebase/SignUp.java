package com.example.collegefirebase;

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

public class SignUp extends AppCompatActivity {

    EditText signupmail,signuppwd;
    Button signupbtn,loginbtn,googlesignin;
    TextView forgotpass;
    private FirebaseAuth auth;
    private static final int RC_SIGN_IN = 1;
    private static final String TAG = "signup";

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

        final GoogleSignInClient mGoogleSignInClient;

        signupmail = findViewById(R.id.email_edt_text);
        signuppwd = findViewById(R.id.pass_edt_text);
        auth = FirebaseAuth.getInstance();
        googlesignin = findViewById(R.id.google_signin_button);
        signupbtn = findViewById(R.id.signup_btn);
        loginbtn = findViewById(R.id.login_btn);
        forgotpass = findViewById(R.id.forgotpwd);

        //requesting basic details of user like email and others, currently requesting only  email
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().build();

        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);

        //login button, logins with email and id.
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = signupmail.getText().toString();
                String pwd = signuppwd.getText().toString();
                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(pwd)) {
                    Toast.makeText(SignUp.this, "Enter full details", Toast.LENGTH_LONG).show();
                }
                else{
                    auth.signInWithEmailAndPassword(email,pwd)
                            .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(SignUp.this,"success",Toast.LENGTH_LONG).show();
                                        Intent myintent = new Intent(SignUp.this,MainActivity.class);
                                        startActivity(myintent);
                                        finish();
                                    }
                                    else{
                                        Toast.makeText(SignUp.this,"login failed",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
        });

        //sign up button. signs up with email and password in firebase
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = signupmail.getText().toString();
                String password = signuppwd.getText().toString();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(),"Please enter your E-mail address",Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(getApplicationContext(),"Please enter your password",Toast.LENGTH_LONG).show();
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
                    auth.createUserWithEmailAndPassword(email,password)
                            .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        startActivity(new Intent(SignUp.this, MainActivity.class));
                                        finish();
                                    }
                                    else{
                                        //to catch error
                                        Log.e("signup", "onComplete: " +task.getException().getMessage().toString() );
                                        Toast.makeText(SignUp.this, "ERROR",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                }
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
        // signs in the user with google
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

