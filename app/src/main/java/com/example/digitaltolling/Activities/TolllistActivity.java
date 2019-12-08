package com.example.digitaltolling.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.digitaltolling.Models.Toll;
import com.example.digitaltolling.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class TolllistActivity extends AppCompatActivity {

FirebaseDatabase firebaseDatabase;
DatabaseReference databaseReference;
FirebaseAuth firebaseAuth;
FirebaseUser firebaseUser;
RelativeLayout relativeLayout;
DatabaseReference tollref;
    EditText TollName;
    EditText lat;
    EditText lng;
    EditText Lmv_price;
    EditText Bus_Truck_price;
    EditText Multiaxle_price;
private ListView listView;
    private List<String> tollList=new ArrayList<String>();
    private ArrayAdapter<String> tollArrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tolllist);
        databaseReference =FirebaseDatabase.getInstance().getReference();
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        relativeLayout =findViewById(R.id.adminlayout);
        if(firebaseUser.getEmail().equals("ron@mail.com")){
            Toast.makeText(this, "Welcome Admin", Toast.LENGTH_SHORT).show();
            relativeLayout.setVisibility(View.VISIBLE);
        }
        else
        {
            relativeLayout.setVisibility(View.INVISIBLE);
        }

        TollName=findViewById(R.id.TollName);
        lat=findViewById(R.id.lat);
        lng=findViewById(R.id.lng);
        Lmv_price=findViewById(R.id.Lmv_price);
        Bus_Truck_price=findViewById(R.id.Bus_Truck_price);
        Multiaxle_price=findViewById(R.id.Multiaxle_price);
        tollref=FirebaseDatabase.getInstance().getReference().child("Toll");
        listView=findViewById(R.id.listview);
        tollArrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,tollList);
        listView.setAdapter(tollArrayAdapter);
        tollref.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                final String toll= dataSnapshot.getValue(Toll.class).toString();
                tollList.add(toll);
                tollArrayAdapter.notifyDataSetChanged();
                new CountDownTimer(4000, 1000)
                {

                    public void onTick(long millisUntilFinished) { final Toast toast = Toast.makeText(getApplicationContext(),"" +
                            toll+" has been added successfully and the geofence has been created.Check the map to view it", Toast.LENGTH_SHORT);
                        View view = toast.getView();
                        view.setBackgroundColor(Color.RED);


                        TextView text = (TextView) view.findViewById(android.R.id.message);
                        text.setTextColor(Color.WHITE);
                        text.setGravity(Gravity.CENTER);
                        toast.show();}
                    public void onFinish() { final Toast toast = Toast.makeText(getApplicationContext(),"" +
                            toll+" has been added successfully and the geofence has been created.Check the map to view it" , Toast.LENGTH_SHORT);
                        View view = toast.getView();
                        view.setBackgroundColor(Color.RED);


                        TextView text = (TextView) view.findViewById(android.R.id.message);
                        text.setTextColor(Color.WHITE);
                        text.setGravity(Gravity.CENTER);
                        toast.show();}

                }.start();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





    }
    public void addtoll(View view)
    {
        String tollname=TollName.getText().toString();
        String latitutde=lat.getText().toString();
        String longitude=lng.getText().toString();
        String lmvprice=Lmv_price.getText().toString();
        String bustruckprice=Bus_Truck_price.getText().toString();
        String multiaxleprice=Multiaxle_price.getText().toString();
        Toll toll=new Toll(tollname,latitutde,longitude,lmvprice,bustruckprice,multiaxleprice);
        databaseReference.child("Toll").child(tollname).setValue(toll);
        TollName.setText("");
        lat.setText("");
        lng.setText("");
        Lmv_price.setText("");
        Bus_Truck_price.setText("");
        Multiaxle_price.setText("");

        Toast.makeText(this, "Toll added Successfully", Toast.LENGTH_SHORT).show();
    }
}
