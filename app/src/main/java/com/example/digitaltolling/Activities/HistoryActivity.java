package com.example.digitaltolling.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.example.digitaltolling.Models.Payment;
import com.example.digitaltolling.Models.Record;
import com.example.digitaltolling.Models.Toll;
import com.example.digitaltolling.Models.Users;
import com.example.digitaltolling.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.digitaltolling.Activities.MapsActivity.tollList;

public class HistoryActivity extends AppCompatActivity {

    private ListView listView;
    DatabaseReference databaseReference;
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<Record> data=new ArrayList<>();
    static View.OnClickListener myOnClickListener;
    private static ArrayList<Integer> removedItems;
    private CustomAdapter customAdapter;

List<Record> records=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

                recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
                recyclerView.setHasFixedSize(true);
                layoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());

                Log.i("here",records.toString());
                Log.i("here",data.toString());

                adapter = new CustomAdapter(data);
                recyclerView.setAdapter(adapter);
            }






        }

    

