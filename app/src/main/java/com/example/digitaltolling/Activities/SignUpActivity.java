package com.example.digitaltolling.Activities;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.digitaltolling.R;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {


    private Button signupWithMailBtn,signupWithPhnBrtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);



        signupWithMailBtn = findViewById(R.id.btnMail);
        signupWithPhnBrtn = findViewById(R.id.btnPhone);

        signupWithMailBtn.setOnClickListener(this);
        signupWithPhnBrtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {


        if (view.getId() ==R.id.btnMail)
        {
            Intent registerActivity = new Intent(getApplicationContext(),RegisterActivity.class);
            startActivity(registerActivity);
        }
        else if (view.getId() ==R.id.btnPhone)
        {
            Intent registerActivity = new Intent(getApplicationContext(),PhonenumActivity.class);
            startActivity(registerActivity);
        }



    }
}
