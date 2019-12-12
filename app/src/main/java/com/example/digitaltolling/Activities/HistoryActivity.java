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
    DatabaseReference databaseReference,userref;
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<Record> data=new ArrayList<>();
    static View.OnClickListener myOnClickListener;
    private static ArrayList<Integer> removedItems;
    private CustomAdapter customAdapter;
private Users u;
List<Record> records=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
userref=FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
userref.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        u=dataSnapshot.getValue(Users.class);
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
});
                recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
                recyclerView.setHasFixedSize(true);
                layoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
        databaseReference=FirebaseDatabase.getInstance().getReference().child("record");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                data.clear();
                for(DataSnapshot s:dataSnapshot.getChildren())
                {
                    Record r=s.getValue(Record.class);
                    if(r.getUsername().equals(u.getName()))
                    data.add(r);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


                adapter = new CustomAdapter(data);
                recyclerView.setAdapter(adapter);
            }






        }



