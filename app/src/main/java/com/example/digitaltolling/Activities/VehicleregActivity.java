package com.example.digitaltolling.Activities;

import android.content.Intent;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.digitaltolling.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class VehicleregActivity extends AppCompatActivity {



  ImageView imageVehiclePhoto;
    static int PReqCode = 1;
    static int REQUESCODE = 1;
    Uri pickedImageUri;

   private EditText vehicleName,vehiclePlateNum,vehicleColor;
   private Spinner spinnerType;
   private Button vehicleRegBtn;
   DatabaseReference databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehiclereg);

        databaseReference = FirebaseDatabase.getInstance().getReference("vehicles");
        vehicleName = (EditText) findViewById(R.id.vehicleNameId);
        vehiclePlateNum = (EditText) findViewById(R.id.plateNoId);
        vehicleColor = (EditText) findViewById(R.id.colorId);
        spinnerType = (Spinner) findViewById(R.id.typeSpinner);
        spinnerType.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,VehicleType.vehicleTypes));
        vehicleRegBtn = (Button) findViewById(R.id.vehicleRegisterButton);


       vehicleRegBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

             saveData();
           }
       });






    }

    private void saveData() {


           String vehName = vehicleName.getText().toString().trim();
           String plateNum = vehiclePlateNum.getText().toString().trim();
           String color =  vehicleColor.getText().toString().trim();


        String id = VehicleType.vehicleId[spinnerType.getSelectedItemPosition()];
        final  String userUid = FirebaseAuth.getInstance().getUid();
        String key = databaseReference.push().getKey();

        Vehicle vehicle = new Vehicle(vehName,plateNum,color,id);
        databaseReference.child(userUid).child(key).setValue(vehicle);
        Toast.makeText(getApplicationContext(),"Vehicle Registered Successfully",Toast.LENGTH_LONG).show();
        updateUI();





    }

    private void updateUI() {

        Intent homeActivity = new Intent(getApplicationContext(),Home.class);
        startActivity(homeActivity);
        finish();


    }
}
