package com.example.digiwall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {

    private static final String TAG = "SignupActivity";


    // Firebase
    private FirebaseAuth mAuth;

    //Progress Dialog
    private ProgressDialog mDialog;

    //intitializing the variables in registration page
    private EditText mEmail;
    private EditText mPass;
    private Button btnReg;
    private TextView msignIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //assigninig registration variables given by the user.
        mEmail=findViewById(R.id.email_reg);
        mPass=findViewById(R.id.password_reg);
        btnReg=findViewById(R.id.sign_up_button);
        msignIn=findViewById(R.id.sign_in_button);

        mAuth=FirebaseAuth.getInstance();

        mDialog=new ProgressDialog(this);

        // Register Button
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignupActivity.this.registration();
            }
        });


        // Already have an account? Login
        msignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignupActivity.this.finish();
            }
        });


    }

    //Registration

    private void registration(){

        String email=mEmail.getText().toString().trim();
        String pass=mPass.getText().toString().trim();

        //Check if valid email is provided
        if(TextUtils.isEmpty(email)){
            mEmail.setError("Valid Email Required!");
            return;
        }
        if(TextUtils.isEmpty(pass)){
            mPass.setError("Valid Password Required!");
            return;
        }

        if(pass.length() < 6){
            showToast("Password too short, enter minimum 6 characters");
            return;
        }

        mDialog.setMessage("Processing..");
        mDialog.show();

        mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                mDialog.dismiss();
                                showToast("Resitration Successful! Check your Email for Verification link!");
                                //SignupActivity.this.startActivity(new Intent(SignupActivity.this, MainActivity.class));
                                SignupActivity.this.finish();
                            }
                            else{
                                showToast("Registration Failed! "+task.getException());
                            }
                        }
                    });
                }
                else{
                    mDialog.dismiss();
                    SignupActivity.this.showToast("Authentication failed. " + task.getException());

                }
            }
        });
    }

    public void showToast(String toastText) {
        Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show();
    }

}
