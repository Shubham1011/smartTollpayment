package com.example.digitaltolling.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission.CAMERA;

public class ScannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {


    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView scannerView;
    DatabaseReference databaseReference;
    String ID;
    String priceValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

            if (checkPermission()){

                Toast.makeText(ScannerActivity.this,"Permission is granted",Toast.LENGTH_LONG).show();

            }
            else
            {
                requestPermission();
            }
        }

        final String userUid = FirebaseAuth.getInstance().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("vehicles").child(userUid);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snap: dataSnapshot.getChildren()){
                    databaseReference = FirebaseDatabase.getInstance().getReference("vehicles").child(userUid).child(snap.getKey()).child("id");
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            ID =(String)dataSnapshot.getValue();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


  private boolean checkPermission(){

        return (ContextCompat.checkSelfPermission(ScannerActivity.this, CAMERA) == PackageManager.PERMISSION_GRANTED);
  }

    private void requestPermission(){

        ActivityCompat.requestPermissions(this,new String[]{CAMERA},REQUEST_CAMERA);
    }




    public void onRequestPermissionsResult(int reqeustCode ,String permission[] , int grantResults[]){

        switch (reqeustCode){

            case REQUEST_CAMERA :
                if (grantResults.length > 0 ){

                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted){
                        Toast.makeText(ScannerActivity.this, "Permission Granted",Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(ScannerActivity.this, "Permission Granted",Toast.LENGTH_LONG).show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

                            if (shouldShowRequestPermissionRationale(CAMERA)){


                            }
                        }
                    }
                }
                break;
        }
    }

    @Override
    public void onResume(){

        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (checkPermission()){

                if (scannerView == null){

                    scannerView = new ZXingScannerView(this);
                    setContentView(scannerView);
                }
                scannerView.setResultHandler(this);
                scannerView.startCamera();

            }
            else {
                requestPermission();
            }
        }
    }

    @Override

    public void onDestroy(){

        super.onDestroy();
        scannerView.stopCamera();
    }

    public void displayAlertMessage(String message, DialogInterface.OnCancelListener listener){

        new AlertDialog.Builder(ScannerActivity.this).setMessage(message)
                .setPositiveButton("PAY", (DialogInterface.OnClickListener) listener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();


    }

    @Override
    public void handleResult(final Result result) {

        final String scanResult = result.getText();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Your Bill");
        builder.setPositiveButton("Pay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


                Intent paymentActivity = new Intent(getApplicationContext(),PaymentActivity.class);
                startActivity(paymentActivity);


            }
        });
        builder.setNeutralButton("Back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                startActivity(new Intent(getApplicationContext(),Home.class));
                finish();

            }
        });

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(scanResult));
        String bill[] = scanResult.split(",");
        String id = ID;
        String value[] = new String[10];
        for(int j=0;j<bill.length;j++){

            if (bill[j].contains(id+":")){

                value = bill[j].split(":");
                break;
            }
        }


        builder.setMessage(String.valueOf(value[1]+" Taka"));
        AlertDialog alert = builder.create();
        alert.show();




    }
}
