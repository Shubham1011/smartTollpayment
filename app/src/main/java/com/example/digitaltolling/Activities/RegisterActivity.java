package com.example.digitaltolling.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.digitaltolling.Models.Users;
import com.example.digitaltolling.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class RegisterActivity extends AppCompatActivity {

   ImageView imageUserPhoto;
   static int PReqCode = 1;
   static int REQUESCODE = 1;
   Uri pickedImagUri;





   private EditText name,userEmail,nidNo,userPassword,userPassword2;
   private ProgressBar loadingProgress;
   private Button regBtn;
   private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = (EditText)findViewById(R.id.nameId);
        userEmail = (EditText)findViewById(R.id.emailId);
        nidNo = (EditText)findViewById(R.id.nidId);
        userPassword = (EditText)findViewById(R.id.passId);
        userPassword2 =(EditText)findViewById(R.id.confirmPassId);
        loadingProgress = (ProgressBar)findViewById(R.id.progressBar2);
        regBtn = (Button)findViewById(R.id.registerButton);
        loadingProgress.setVisibility(View.INVISIBLE);


        mAuth = FirebaseAuth.getInstance();


        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                regBtn.setVisibility(View.INVISIBLE);
                loadingProgress.setVisibility(View.VISIBLE);



                final String username = name.getText().toString();
                final String email = userEmail.getText().toString();
                final String nid = nidNo.getText().toString();
                final String password = userPassword.getText().toString();
                final String password2 = userPassword2.getText().toString();
                final String balance="0";



                if ( username.isEmpty() || email.isEmpty() || nid.isEmpty() || password.isEmpty() || !password.equals(password2)) {


                    //we need to display an error message

                    showMessage("Please verify all fields");
                    regBtn.setVisibility(View.VISIBLE);
                    loadingProgress.setVisibility(View.INVISIBLE);


                }
                else {
                    //everything is okk

                    CreateUserAccount(username,email,nid,password,balance);


                }

            }
        });




        imageUserPhoto = (ImageView)findViewById(R.id.regUserPhoto);

        imageUserPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(Build.VERSION.SDK_INT >=22){

                    checkAndRequestForPermission();
                }
                else
                {
                    openGallery();
                }


            }
        });

    }

    private void CreateUserAccount(final String username, final String email, final String nid, String password, final String balance) {

            mAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                               //user account created successfully
                                showMessage("Account created");
                                //update profile picture and name
                                //updateUserInfo( username ,pickedImagUri,mAuth.getCurrentUser());
                                createUser(username,email,nid,balance);
                            }
                            else
                            {
                                //account creation failed
                                showMessage("account creation failed" + task.getException().getMessage());
                                regBtn.setVisibility(View.VISIBLE);
                                loadingProgress.setVisibility(View.INVISIBLE);
                                Log.e("Failed",task.getException().getMessage());

                            }
                        }
                    });


    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void updateUserInfo(Uri pickedImagUri, final FirebaseUser currentUser) {

         //update photo to firebase and url
        final String userUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Log.wtf("UserID",userUid);
        StorageReference mStorage = FirebaseStorage.getInstance().getReference().child(userUid).child("users_photo");
        final StorageReference imageFilePath = mStorage.child(pickedImagUri.getLastPathSegment());
        //final StorageReference imageFilePath = mStorage.child(Objects.requireNonNull(pickedImageUri.getLastPathSegment()));
        imageFilePath.putFile(pickedImagUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                //image uploaded successfully
                //now we can get our image url
                imageFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        //uri contain image uri
                        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                                .setDisplayName("image")
                                .setPhotoUri(uri)
                                .build();
                                currentUser.updateProfile(profileUpdate)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                if (task.isSuccessful()){
                                                    showMessage("Register Complete");
                                                    updateNow();
                                                }else {
                                                    Log.d("failed",task.getException().getMessage());

                                                }

                                            }
                                        });
                    }
                });


            }
        });



    }

    private void updateNow() {
        Intent vehicleregActivity = new Intent(getApplicationContext(),VehicleregActivity.class);
        startActivity(vehicleregActivity);
        finish();
    }

    private void updateUI() {
        updateUserInfo(pickedImagUri,mAuth.getCurrentUser());
    }


    private void showMessage(String message) {

        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
    }

    private void openGallery() {

        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,REQUESCODE);



    }

    private void checkAndRequestForPermission() {

        if (ContextCompat.checkSelfPermission(RegisterActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)

          != PackageManager.PERMISSION_GRANTED){

            if(ActivityCompat.shouldShowRequestPermissionRationale(RegisterActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)){

                Toast.makeText(RegisterActivity.this,"Please accept for required permissin",Toast.LENGTH_SHORT).show();
            }


            else
            {
                ActivityCompat.requestPermissions(RegisterActivity.this,

                                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                PReqCode);
            }
        }
        else
            openGallery();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUESCODE && data != null) {
            //set image to image view
            pickedImagUri = data.getData();

            //Log.d("WhatIs",""+pickedImagUri);
            //Toast.makeText(this, "Found" + pickedImagUri, Toast.LENGTH_SHORT).show();
            imageUserPhoto.setImageURI(pickedImagUri);

//        Glide.with(this)
//                .load(pickedImagUri)
//                .into(imageUserPhoto);
            //Log.wtf("userID",mAuth.getCurrentUser().getUid());
        }

        //updateUserInfo(pickedImagUri,mAuth.getCurrentUser());

      /*  if (requestCode == RESULT_OK && requestCode == REQUESCODE && data != null){




        }*/
    }

    private void createUser(String name,String email,String nid,String balance){
        DatabaseReference mReferance= FirebaseDatabase.getInstance().getReference("Users");
        Users model=new Users(
                name,
                email,
                nid,balance

        );
        mReferance.child(mAuth.getCurrentUser().getUid()).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(getApplicationContext(),"successfuly",Toast.LENGTH_LONG).show();
            }
        });

        loadingProgress.setVisibility(View.INVISIBLE);
        updateUI();

    }
}
