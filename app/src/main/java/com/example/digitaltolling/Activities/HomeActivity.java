package com.example.digitaltolling.Activities;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.digitaltolling.Fragments.HomeFragment;
import com.example.digitaltolling.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.paytm.pgsdk.easypay.manager.PaytmAssist.getContext;

public class HomeActivity extends AppCompatActivity {

    public static FragmentManager fragmentManager;
DatabaseReference vehicleref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        fragmentManager = getSupportFragmentManager();
        Toast.makeText(this, "called", Toast.LENGTH_SHORT).show();




        if(findViewById(R.id.container) !=null)

        {
            if(savedInstanceState!=null){
                return;
            }


            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            getSupportFragmentManager().beginTransaction().replace(R.id.container,new HomeFragment()).commit();
        }


    }
}
