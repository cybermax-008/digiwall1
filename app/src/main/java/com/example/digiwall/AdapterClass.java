package com.example.digiwall;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digiwall.model.Data;

import java.util.ArrayList;
import java.util.Date;

public class AdapterClass extends RecyclerView.Adapter<AdapterClass.MyViewHolder> {

    ArrayList<Data> list;
    public AdapterClass(ArrayList<Data> list)
    {
        this.list=list;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.income_recycle_data,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.mType.setText(list.get(position).getType());
        holder.mNote.setText(list.get(position).getNote());
        holder.mDate.setText(list.get(position).getDate());
        holder.mAmount.setText(list.get(position).getAmount());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView mType,mNote,mDate,mAmount;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            mType=itemView.findViewById(R.id.type_txt_income);
            mNote=itemView.findViewById(R.id.note_txt_income);
            mDate=itemView.findViewById(R.id.date_txt_income);
            mAmount=itemView.findViewById(R.id.amount_txt_income);
        }
    }
}
