package com.example.digiwall;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.digiwall.model.Data;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExpenseFragment extends Fragment {

    public ExpenseFragment() {
        // Required empty public constructor
    }

    //Firebase database

    private FirebaseAuth mAuth;
    private DatabaseReference mExpenseDatabase;

    //Recycler view
    private RecyclerView recyclerView;

    //Search View
    private SearchView searchView;

    //TextView Total amount
    private TextView expense_total;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myview=inflater.inflate(R.layout.fragment_expense, container, false);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser mUser = mAuth.getCurrentUser();

        String uid = mUser.getUid();

        mExpenseDatabase = FirebaseDatabase.getInstance().getReference().child("ExpenseData").child(uid);

        recyclerView = myview.findViewById(R.id.recycle_expense);
        searchView=myview.findViewById(R.id.searchViewExpense);

        expense_total=myview.findViewById(R.id.expense_txt_result);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        mExpenseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int totalvalue=0;

                for(DataSnapshot mysnapshot: dataSnapshot.getChildren() ) {

                    Data data = mysnapshot.getValue(Data.class);

                    totalvalue += data.getAmount();

                    String stTotalvalue = String.valueOf(totalvalue);
                    expense_total.setText(stTotalvalue+ " CAD");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        return myview;
    }
}
