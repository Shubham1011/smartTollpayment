package com.example.digitaltolling.Activities;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.digitaltolling.Models.Payment;
import com.example.digitaltolling.Models.Record;
import com.example.digitaltolling.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private ArrayList<Record> dataSet;

private static  DatabaseReference databaseReference;
    public static class MyViewHolder extends RecyclerView.ViewHolder {



        TextView textViewName;
        TextView textViewVersion;
        ImageView imageViewIcon;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewName = (TextView) itemView.findViewById(R.id.textView3);
            this.textViewVersion = (TextView) itemView.findViewById(R.id.textView5);

        }
    }

    public CustomAdapter(final  ArrayList<Record> data) {
        databaseReference=FirebaseDatabase.getInstance().getReference().child("record");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
           data.clear();
           for(DataSnapshot s:dataSnapshot.getChildren())
           {
               Record r=s.getValue(Record.class);
               data.add(r);
           }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        this.dataSet=data;
    }

            @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.items, parent, false);

        view.setOnClickListener(HistoryActivity.myOnClickListener);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView textViewName = holder.textViewName;
        TextView textViewVersion = holder.textViewVersion;
        ImageView imageView = holder.imageViewIcon;

        textViewName.setText(dataSet.get(listPosition).getTollname());
        textViewVersion.setText(dataSet.get(listPosition).getUsername());

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}

