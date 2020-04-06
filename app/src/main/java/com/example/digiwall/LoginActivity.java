package com.example.digiwall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "AndroidClarified";
    private SignInButton googleSignInButton;

    //intitializing the variables in registration page
    private EditText mEmail;
    private EditText mPass;
    private Button btnLogin;
    private TextView mForgotPassword;
    private TextView mSignupHere;

    private ProgressDialog mDialog;

    // Firebase
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //assigninig Login variables given by the user.
        mEmail = findViewById(R.id.email_login);
        mPass = findViewById(R.id.password_login);
        btnLogin = findViewById(R.id.btn_login);
        mForgotPassword = findViewById(R.id.forgot_password);
        mSignupHere = findViewById(R.id.btn_noaccount);
        //Assigning google signIn btn
        googleSignInButton = findViewById(R.id.sign_in_button);

        // Start Firebase Instance
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();

        if(mUser != null && mUser.isEmailVerified()){

            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();


        mDialog = new ProgressDialog(this);


        //Login Button
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.this.loginDetails();
            }
        });


        //Don't have an account? Register
        mSignupHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignupActivity.class));
            }
        });

        // Forgot Password?
        mForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ResetActivity.class));
            }
        });

    }

    //Login
    private void loginDetails() {

        String email = mEmail.getText().toString().trim();
        final String pass = mPass.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            mEmail.setError("Valid Email Required!");

            return;
        }

        if (TextUtils.isEmpty(pass)) {
            mPass.setError("Valid Password Required!");

            return;
        }

        mDialog.setMessage("Processing..");
        mDialog.show();

        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    if(mAuth.getCurrentUser().isEmailVerified()){
                        mDialog.dismiss();
                        showToast("Login Successful!");
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        LoginActivity.this.finish();
                    }
                    else{
                        mDialog.dismiss();
                        showToast("Email not verified yet!");
                    }

                } else {
                    mDialog.dismiss();
                    if (pass.length() < 6) {
                        showToast(getString(R.string.minimum_password));
                    } else {
                        showToast(getString(R.string.auth_failed));
                    }

                }
            }
        });


    }



    public void showToast(String toastText) {
        Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show();
    }
}
