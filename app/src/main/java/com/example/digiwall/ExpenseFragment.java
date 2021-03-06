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

    private FirebaseRecyclerAdapter adapter;


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
        recyclerView.setLayoutManager(layoutManager);

        mExpenseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                double totalvalue=0.0;

                for(DataSnapshot mysnapshot: dataSnapshot.getChildren() ) {

                    Data data = mysnapshot.getValue(Data.class);

                    totalvalue += data.getAmount();

                    String stTotalvalue= String.format("%.2f", totalvalue);
                    expense_total.setText(stTotalvalue+ " CAD");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        FirebaseRecyclerOptions<Data> options = new FirebaseRecyclerOptions.Builder<Data>()
                .setQuery(mExpenseDatabase,Data.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<Data, MyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final MyViewHolder holder, int position, @NonNull Data model) {

                holder.setType(model.getType());
                holder.setNote(model.getNote());
                holder.setDate(model.getDate());
                holder.setName(model.getName());
                holder.setAmount(model.getAmount());

            }

            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.expense_recycle_data,parent,false);
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

    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.mView = itemView;
        }


        private void setType(String type){

            TextView mType=mView.findViewById(R.id.type_txt_expense);
            mType.setText(type);

        }

        private void setNote(String note){

            TextView mNote=mView.findViewById(R.id.note_txt_expense);
            mNote.setText(note);

        }
        private void setName(String name){

            TextView mNote=mView.findViewById(R.id.name_txt_expense);
            mNote.setText(name);

        }

        private void setDate(String date){

            TextView mDate=mView.findViewById(R.id.date_txt_expense);
            mDate.setText(date);

        }

        private void setAmount(double amount){

            TextView mAmount=mView.findViewById(R.id.amount_txt_expense);
            String stamount=String.valueOf(amount);
            mAmount.setText(stamount);

        }


    }
}
