package com.example.digiwall;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.digiwall.model.Data;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
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
public class IncomeFragment extends Fragment {

    public IncomeFragment() {
        // Required empty public constructor
    }

    //Firebase database

    private FirebaseAuth mAuth;
    private DatabaseReference mIncomeDatabase;

    //Recycler view
    private RecyclerView recyclerView;

    //Search View
    private SearchView searchView;

    //TextView Total amount
    private TextView income_total;

    private FirebaseRecyclerAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myview = inflater.inflate(R.layout.fragment_income, container, false);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser mUser = mAuth.getCurrentUser();

        String uid = mUser.getUid();

        mIncomeDatabase = FirebaseDatabase.getInstance().getReference().child("IncomeData").child(uid);

        recyclerView = myview.findViewById(R.id.recycle_income);
        searchView=myview.findViewById(R.id.searchViewIncome);

        income_total=myview.findViewById(R.id.income_txt_result);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        mIncomeDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                double totalvalue=0.0;

                for(DataSnapshot mysnapshot: dataSnapshot.getChildren() ){

                    Data data =mysnapshot.getValue(Data.class);

                    totalvalue+=data.getAmount();

                    String stTotalvalue= String.format("%.2f", totalvalue);
                    income_total.setText(stTotalvalue+" CAD");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        FirebaseRecyclerOptions<Data> options = new FirebaseRecyclerOptions.Builder<Data>()
                .setQuery(mIncomeDatabase,Data.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<Data, MyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull Data model) {

                holder.setType(model.getType());
                holder.setNote(model.getNote());
                holder.setDate(model.getDate());
                holder.setName(model.getName());
                holder.setAmount(model.getAmount());
            }

            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.income_recycle_data,parent,false);
                MyViewHolder viewHolder=new MyViewHolder(view);
                return viewHolder;

            }
        };
        recyclerView.setAdapter(adapter);

        return myview;
    }


    @Override
    public void onStart() {
        super.onStart();

        adapter.startListening();

        if(searchView != null){

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {

                    search(newText);
                    return true;
                }
            });
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    private void search(String str){


    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.mView = itemView;
        }


        private void setType(String type){

            TextView mType=mView.findViewById(R.id.type_txt_income);
            mType.setText(type);

        }

        private void setNote(String note){

            TextView mNote=mView.findViewById(R.id.note_txt_income);
            mNote.setText(note);

        }

        private void setName(String name){

            TextView mNote=mView.findViewById(R.id.name_txt_income);
            mNote.setText(name);

        }

        private void setDate(String date){

            TextView mDate=mView.findViewById(R.id.date_txt_income);
            mDate.setText(date);

        }

        private void setAmount(double amount){

            TextView mAmount=mView.findViewById(R.id.amount_txt_income);
            String stamount=String.valueOf(amount);
            mAmount.setText(stamount);
            //add commit 4
            //add commit 5



        }


    }


}

