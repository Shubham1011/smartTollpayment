package com.example.digitaltolling.Activities;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.digitaltolling.R;
import com.google.firebase.auth.FirebaseAuth;

public class PhonenumActivity extends AppCompatActivity {


    private Spinner spinner;
    private EditText editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phonenum);

        spinner = findViewById(R.id.spinnerCountries);
        spinner.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,CountryData.countryNames));
        editText = findViewById(R.id.editTextMobile);


        findViewById(R.id.buttonContinue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String code = CountryData.countryAreaCodes[spinner.getSelectedItemPosition()];


                String number = editText.getText().toString().trim();

                if (number.isEmpty() || number.length() < 10){
                    editText.setError("Valid number is Required");
                    editText.requestFocus();
                    return;
                }



                String phoneNumber = "+" + code + number;
                Intent intent = new Intent(PhonenumActivity.this,CodeActivity.class);
                intent.putExtra("phonenumber",phoneNumber);
                startActivity(intent);



            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser()!= null){
            Intent intent = new Intent(PhonenumActivity.this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(intent);
        }
    }
}
