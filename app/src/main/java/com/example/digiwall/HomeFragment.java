package com.example.digiwall;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.TestLooperManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.digiwall.model.Data;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    int year,month,day,week;
    public static final String[] MONTHS = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    private IncomeFragment incomeFragment;
    private ExpenseFragment expenseFragment;



    public HomeFragment() {

        // Required empty public constructor
    }

    //Floating button
    private FloatingActionButton fab_main;
    private FloatingActionButton fab_expense;
    private FloatingActionButton fab_income;

    //Floating text
    private TextView fabTxt_expense;
    private TextView fabTxt_income;


    private View myview;

    //boolean
    private boolean isOpen =false;

    //Animation
    private Animation FadeOpen,FadeClose;

    //Firebase
    private FirebaseAuth mAuth;
    private DatabaseReference mIncomeDatabase;
    private DatabaseReference mExpenseDatabase;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myview= inflater.inflate(R.layout.fragment_home, container, false);

        mAuth=FirebaseAuth.getInstance();
        FirebaseUser mUser=mAuth.getCurrentUser();
        String uid=mUser.getUid();

        //Intialize Income and expense button
        final Button incomeButton=myview.findViewById(R.id.btn_income);
        final Button expenseButton=myview.findViewById(R.id.btn_expense);

        mIncomeDatabase= FirebaseDatabase.getInstance().getReference().child("IncomeData").child(uid);
        mExpenseDatabase= FirebaseDatabase.getInstance().getReference().child("ExpenseData").child(uid);

        mIncomeDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int totalvalue=0;

                for(DataSnapshot mysnapshot: dataSnapshot.getChildren() ){

                    Data data =mysnapshot.getValue(Data.class);

                    totalvalue+=data.getAmount();

                    String stTotalvalue= String.valueOf(totalvalue);
                    incomeButton.setText("Income\n"+stTotalvalue+" CAD");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mExpenseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                int totalvalue=0;

                for(DataSnapshot mysnapshot: dataSnapshot.getChildren() ){

                    Data data =mysnapshot.getValue(Data.class);

                    totalvalue+=data.getAmount();

                    String stTotalvalue= String.valueOf(totalvalue);
                    expenseButton.setText("Expense\n"+stTotalvalue+" CAD");

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //Connect floating button to layout
        fab_main=myview.findViewById(R.id.btn_float);
        fab_expense=myview.findViewById(R.id.float_expense);
        fab_income=myview.findViewById(R.id.float_income);

        //Connect to floating text
        fabTxt_income=myview.findViewById(R.id.txt_income);
        fabTxt_expense=myview.findViewById(R.id.txt_expense);

        //Animation connect
        FadeClose= AnimationUtils.loadAnimation(getActivity(),R.anim.fade_close);
        FadeOpen=AnimationUtils.loadAnimation(getActivity(),R.anim.fade_open);

        incomeFragment=new IncomeFragment();
        expenseFragment=new ExpenseFragment();





        //Perform filter date
        datePicker();
        //Check status of floating button
        float_button();


        return myview;
    }


    private void addData(){

        //Fab Button income
        fab_income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incomeDataInsert();

            }
        });

        //Fab Button Expense
        fab_expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expenseDataInsert();
            }
        });
    }

    public void incomeDataInsert(){

        AlertDialog.Builder mydialog = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater=LayoutInflater.from(getActivity());

        View myview=inflater.inflate(R.layout.insert_data,null);
        mydialog.setView(myview);

        final AlertDialog dialog=mydialog.create();
        dialog.setCancelable(false);

        final EditText editAmount=myview.findViewById(R.id.amount_edit);
        final EditText editType = myview.findViewById(R.id.type_edit);
        final EditText editNote = myview.findViewById(R.id.note_edit);

        Button btnSave=myview.findViewById(R.id.btn_save);
        Button btnCancel= myview.findViewById(R.id.btn_cancel);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String type=editType.getText().toString().trim();
                String amount=editAmount.getText().toString().trim();
                String note=editNote.getText().toString().trim();

                if(TextUtils.isEmpty(type)){

                    editType.setError("Required Field..");
                    return;
                }

                if(TextUtils.isEmpty(amount)){

                    editAmount.setError("Required Field..");
                    return;
                }

                int OurAmt=Integer.parseInt(amount);

                if(TextUtils.isEmpty(note)){

                    editNote.setError("Required Field..");
                    return;
                }
                String id=mIncomeDatabase.push().getKey();
                String mDate= DateFormat.getDateInstance().format(new Date());

                Data data=new Data(OurAmt,type,note,id,mDate);

                mIncomeDatabase.child(id).setValue(data);
                showToast("Data ADDED");
                ftAnimation();
                dialog.dismiss();


            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ftAnimation();
                dialog.dismiss();

            }
        });

        dialog.show();

    }

    public void expenseDataInsert(){

        AlertDialog.Builder mydialog=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=LayoutInflater.from(getActivity());

        View myview=inflater.inflate(R.layout.insert_data,null);
        mydialog.setView(myview);

        final AlertDialog dialog=mydialog.create();
        dialog.setCancelable(false);

        final EditText amount=myview.findViewById(R.id.amount_edit);
        final EditText type=myview.findViewById(R.id.type_edit);
        final EditText note=myview.findViewById(R.id.note_edit);

        Button btnSave=myview.findViewById(R.id.btn_save);
        Button btnCancel=myview.findViewById(R.id.btn_cancel);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tmAmount = amount.getText().toString().trim();
                String tmType = type.getText().toString().trim();
                String tmNote = note.getText().toString().trim();

                if(TextUtils.isEmpty(tmType)){

                    type.setError("Required Field..");
                    return;
                }

                if(TextUtils.isEmpty(tmAmount)){

                    amount.setError("Required Field..");
                    return;
                }

                int inAmt=Integer.parseInt(tmAmount);

                if(TextUtils.isEmpty(tmNote)){

                    note.setError("Required Field..");
                    return;
                }
                String id=mExpenseDatabase.push().getKey();
                String mDate= DateFormat.getDateInstance().format(new Date());

                Data data=new Data(inAmt,tmType,tmNote,id,mDate);

                mExpenseDatabase.child(id).setValue(data);
                showToast("Data ADDED");

                ftAnimation();
                dialog.dismiss();


            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ftAnimation();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void float_button(){

        fab_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData();
                if(isOpen){

                    fab_income.startAnimation(FadeClose);
                    fab_expense.startAnimation(FadeClose);
                    fab_expense.setClickable(false);
                    fab_income.setClickable(false);

                    fabTxt_income.startAnimation(FadeClose);
                    fabTxt_expense.startAnimation(FadeClose);
                    fabTxt_income.setClickable(false);
                    fabTxt_expense.setClickable(false);

                    isOpen=false;
                }
                else{

                    fab_expense.startAnimation(FadeOpen);
                    fab_income.startAnimation(FadeOpen);
                    fab_income.setClickable(true);
                    fab_expense.setClickable(true);

                    fabTxt_income.startAnimation(FadeOpen);
                    fabTxt_expense.startAnimation(FadeOpen);
                    fabTxt_income.setClickable(true);
                    fabTxt_expense.setClickable(true);

                    isOpen=true;
                }
            }
        });
    }

    private void ftAnimation(){

        if(isOpen){

            fab_income.startAnimation(FadeClose);
            fab_expense.startAnimation(FadeClose);
            fab_expense.setClickable(false);
            fab_income.setClickable(false);

            fabTxt_income.startAnimation(FadeClose);
            fabTxt_expense.startAnimation(FadeClose);
            fabTxt_income.setClickable(false);
            fabTxt_expense.setClickable(false);

            isOpen=false;
        }
        else{

            fab_expense.startAnimation(FadeOpen);
            fab_income.startAnimation(FadeOpen);
            fab_income.setClickable(true);
            fab_expense.setClickable(true);

            fabTxt_income.startAnimation(FadeOpen);
            fabTxt_expense.startAnimation(FadeOpen);
            fabTxt_income.setClickable(true);
            fabTxt_expense.setClickable(true);

            isOpen=true;
        }
    }

    private void datePicker(){
        //Get Current day, month,year
        final Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);
        week = cal.get(Calendar.WEEK_OF_MONTH);

        //Set the current date in the text view
        final TextView currentDate = myview.findViewById(R.id.tvDate);

        currentDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu datePopUp = new PopupMenu(getContext(),currentDate);
                datePopUp.getMenuInflater().inflate(R.menu.date_picker,datePopUp.getMenu());

                datePopUp.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        String date;
                        switch (item.getItemId()){
                            case R.id.byDate:
                                showToast(""+item.getTitle());
                                DatePickerDialog dialog = new DatePickerDialog(getContext(),
                                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                                        mDateSetListener,
                                        year,month,day);
                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                dialog.show();
                                mDateSetListener = new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                        month = month;
                                        String date = day+" "+MONTHS[month]+" "+year;
                                        currentDate.setText(date);
                                    }
                                };
                                return true;
                            case R.id.daily:
                                showToast(""+item.getTitle());
                                date = day+" "+MONTHS[month]+" "+year;
                                currentDate.setText(date);
                                return true;
                            case R.id.monthly:
                                showToast(""+item.getTitle());
                                date = MONTHS[month];
                                currentDate.setText(date);
                                return true;
                            case R.id.weekly:
                                showToast(""+item.getTitle());
                                return true;
                            case R.id.yearly:
                                showToast(""+item.getTitle());
                                date =""+year;
                                currentDate.setText(date);
                                return true;

                        }
                        return false;
                    }
                });
                datePopUp.show();
            }
        });

    }

    public void showToast(String toastText) {
        Toast.makeText(getContext(), toastText, Toast.LENGTH_SHORT).show();
    }
}
