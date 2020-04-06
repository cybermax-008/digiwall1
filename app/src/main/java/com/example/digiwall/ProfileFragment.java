package com.example.digiwall;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    int year,month,day,week;
    public static final String[] MONTHS = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myview= inflater.inflate(R.layout.fragment_profile, container, false);

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

        return myview;
    }

    public void showToast(String toastText) {
        Toast.makeText(getContext(), toastText, Toast.LENGTH_SHORT).show();
        //add commit 1
        //add commit 2
    }
}

