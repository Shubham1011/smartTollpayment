package com.example.digitaltolling.Activities;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.digitaltolling.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SigninActivity extends AppCompatActivity implements View.OnClickListener {

    private Button signWithMailBtn,signWithPhnBtn;
    FirebaseAuth mAuth;
    private TextView register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        mAuth = FirebaseAuth.getInstance();
         signWithMailBtn = findViewById(R.id.btnMail);
           signWithPhnBtn = findViewById(R.id.btnPhone);
//        register = findViewById(R.id.newUserText);

           signWithPhnBtn.setOnClickListener(this);
           signWithMailBtn.setOnClickListener(this);


   /**     register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent registerActivity = new Intent(getApplicationContext(),SignUpActivity.class);
                startActivity(registerActivity);


            }
        }); **/
    }

    @Override
    public void onClick(View view) {

        if (view.getId() ==R.id.btnMail)
        {
            Intent registerActivity = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(registerActivity);
        }
        else if (view.getId() ==R.id.btnPhone)
        {
            Intent registerActivity = new Intent(getApplicationContext(),PhonenumActivity.class);
            startActivity(registerActivity);
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null){
            updateUI();
        }
    }

    private void updateUI() {
        startActivity(new Intent(getApplicationContext(),Home.class));
        finish();

    }
}
