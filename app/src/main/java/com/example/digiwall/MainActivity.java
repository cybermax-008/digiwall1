package com.example.digiwall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;


    //Fragments
    private ProfileFragment profileFragment;
    private HomeFragment homeFragment;
    private IncomeFragment incomeFragment;
    private ExpenseFragment expenseFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        //Initialize Toolbar into the screen
        androidx.appcompat.widget.Toolbar toolbar= (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Initialize Firebase authentication
        mAuth=FirebaseAuth.getInstance();


        //Initialize fragments
        profileFragment= new ProfileFragment();
        homeFragment= new HomeFragment();
        incomeFragment=new IncomeFragment();
        expenseFragment=new ExpenseFragment();



        //Intialize and assign variable
        BottomNavigationView bootomNav=findViewById(R.id.bottom_navigation);

        //Set Home Selected
        setFragment(homeFragment);

        //Perform Item Selector listener
        bootomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                MainActivity.this.bottomNaviSwitch(item);
                return true;
            }
        });

    }


    private void left_date_navi(){


    }
    private void right_date_navi(){


    }



    //Common methods to be called in all screens

    public boolean bottomNaviSwitch(@NonNull MenuItem item){

        switch (item.getItemId()){

            case R.id.home:
                setFragment(homeFragment);
                return true;

            case R.id.income:
                setFragment(incomeFragment);
                return true;

            case R.id.expense:
                setFragment(expenseFragment);
                return true;

            case R.id.profile:
                setFragment(profileFragment);
                return true;
        }
        return false;

    }

    private void setFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame,fragment);
        fragmentTransaction.commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.settings,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.settings:
                showToast("Settings");
                break;
            case R.id.btn_logOut:
                showToast(getString(R.string.logoutsuccessful));
                LogOut();

        }
        return super.onOptionsItemSelected(item);
    }

    private void LogOut(){
        mAuth.signOut();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); startActivity(intent);
        startActivity(new Intent(this,LoginActivity.class));

    }

    public void showToast(String toastText) {
        Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show();
    }

}
