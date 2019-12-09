package com.example.digitaltolling.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.digitaltolling.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VehicleregActivity extends AppCompatActivity {



  ImageView imageVehiclePhoto;
    static int PReqCode = 1;
    static int REQUESCODE = 1;
    Uri pickedImageUri;

   private EditText vehicleName,vehiclePlateNum,vehicleColor;
   private Spinner spinnerType;
   private Button vehicleRegBtn;
   DatabaseReference databaseReference;
private String downloadurl;


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

    public void getimage(View view){
        Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show();
        Intent cameraintent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(cameraintent.resolveActivity(getPackageManager())!=null)
        {
            startActivityForResult(cameraintent,REQUESCODE);

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUESCODE && resultCode==RESULT_OK) {
            imageVehiclePhoto=findViewById(R.id.regVehiclePhoto);
            Bundle extras = data.getExtras();
            Bitmap image = (Bitmap) extras.get("data");
            imageVehiclePhoto.setImageBitmap(image);
            Toast.makeText(this, image.toString(), Toast.LENGTH_SHORT).show();
            final String userUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            Log.wtf("UserID",userUid);
            final StorageReference mStorage = FirebaseStorage.getInstance().getReference().child(userUid).child("uservehicleimage");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] d = baos.toByteArray();
            UploadTask uploadTask=mStorage.putBytes(d);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    mStorage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            downloadurl=uri.toString();
                            Toast.makeText(VehicleregActivity.this, downloadurl, Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            });
        }
    }

    private void saveData() {


           String vehName = vehicleName.getText().toString().trim();
           String plateNum = vehiclePlateNum.getText().toString().trim();
           String color =  vehicleColor.getText().toString().trim();


        String id = VehicleType.vehicleId[spinnerType.getSelectedItemPosition()];
        final  String userUid = FirebaseAuth.getInstance().getUid();


        Vehicle vehicle = new Vehicle(vehName,plateNum,color,id,downloadurl);
        databaseReference.child(userUid).setValue(vehicle);
        Toast.makeText(getApplicationContext(),"Vehicle Registered Successfully",Toast.LENGTH_LONG).show();
        updateUI();





    }




    private void updateUI() {

        Intent homeActivity = new Intent(getApplicationContext(),Home.class);
        startActivity(homeActivity);
        finish();


    }
}
