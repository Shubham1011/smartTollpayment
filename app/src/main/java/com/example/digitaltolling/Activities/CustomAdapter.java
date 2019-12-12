package com.example.digitaltolling.Activities;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.digitaltolling.Models.Record;
import com.example.digitaltolling.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private ArrayList<Record> dataSet;

private static  DatabaseReference databaseReference;
    public static class MyViewHolder extends RecyclerView.ViewHolder {



        TextView textViewName;
        TextView textViewVersion;
        TextView tollname;
        ImageView imageViewIcon;
        TextView time;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewName = (TextView) itemView.findViewById(R.id.vehiclename);
            this.textViewVersion = (TextView) itemView.findViewById(R.id.cost);
            this.tollname=(TextView) itemView.findViewById(R.id.tollname);
            this.time=(TextView) itemView.findViewById(R.id.time);
            this.imageViewIcon=itemView.findViewById(R.id.imageView3);

        }
    }

    public CustomAdapter(final  ArrayList<Record> data) {

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
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition)  {

        TextView textViewName = holder.textViewName;
        TextView textViewVersion = holder.textViewVersion;
        TextView tollname=holder.tollname;
        TextView time=holder.time;
        final ImageView imageView = holder.imageViewIcon;

        textViewName.setText(dataSet.get(listPosition).getVehiclename());
        textViewVersion.setText(dataSet.get(listPosition).getCost());
        tollname.setText(dataSet.get(listPosition).getTollname());
        time.setText(dataSet.get(listPosition).getDate());
        final long ONE_MEGABYTE = 1024 * 1024;
        StorageReference storageReference= FirebaseStorage.getInstance().getReference().child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("uservehicleimage");
        storageReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap myBitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                imageView.setImageBitmap(myBitmap);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}

