package com.example.digitaltolling.Activities;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.digitaltolling.Fragments.HomeFragment;
import com.example.digitaltolling.R;

public class HomeActivity extends AppCompatActivity {

    public static FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        fragmentManager = getSupportFragmentManager();



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
