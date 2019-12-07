package com.example.digitaltolling.Activities;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.digitaltolling.R;

public class TolllistActivity extends AppCompatActivity {

    private TextView mayorhanif,japan,bangabandhu,meghna,mohashkhail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tolllist);

        mayorhanif = findViewById(R.id.hanifId);
        japan = findViewById(R.id.jbId);
         bangabandhu = findViewById(R.id.bbId);
         meghna = findViewById(R.id.meghnaId);
         mohashkhail = findViewById(R.id.mohasId);


         mayorhanif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent hanifActivity = new Intent(getApplicationContext(),HanifActivity.class);
                startActivity(hanifActivity);
            }
        });

        japan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent japanActivity = new Intent(getApplicationContext(),JapanActivity.class);
                startActivity(japanActivity);
            }
        });

        bangabandhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent registerActivity = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(registerActivity);
            }
        });

        meghna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent registerActivity = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(registerActivity);
            }
        });

        mohashkhail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent registerActivity = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(registerActivity);
            }
        });

    }
}
