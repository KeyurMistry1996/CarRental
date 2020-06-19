package com.example.carrental.initial.host;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.carrental.R;
import com.google.android.material.textfield.TextInputLayout;

public class AboutCarActivity extends AppCompatActivity {

    EditText carbrand, carmodel, caryear, cartransmission, carodormeter;
    TextInputLayout brandlayout, modellayout, yearlayout, transmissionlayout, odormeterlayout;
    Button next;

    public static String carBrandtxt, carModeltxt, carYeartxt, carTransmissiontxt, carOdormetertxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_car);

        //EditText Ids

        carbrand = findViewById(R.id.brandName);
        carmodel = findViewById(R.id.modelname);
        caryear = findViewById(R.id.yearName);
        cartransmission = findViewById(R.id.transmissionName);
        carodormeter = findViewById(R.id.odometerName);

        //TextinputLayout Ids

        brandlayout = findViewById(R.id.brandLayout);
        modellayout = findViewById(R.id.modelLayout);
        yearlayout = findViewById(R.id.yearLayout);
        transmissionlayout = findViewById(R.id.transmissionLayout);
        odormeterlayout = findViewById(R.id.odometerLayout);

        //Button Id
        next = findViewById(R.id.next);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carBrandtxt = carbrand.getText().toString().trim();
                carModeltxt = carmodel.getText().toString().trim();
                carYeartxt = caryear.getText().toString().trim();
                carTransmissiontxt = cartransmission.getText().toString().trim();
                carOdormetertxt = carodormeter.getText().toString().trim();

                if (carBrandtxt.isEmpty()) {
                    brandlayout.setError("Enter Brand name plz");
                    brandlayout.requestFocus();
                } else if (carModeltxt.isEmpty()) {
                    modellayout.setError("Enter model year plz");
                    modellayout.requestFocus();
                } else if (carYeartxt.isEmpty()) {
                    yearlayout.setError("Enter Year plz");
                    yearlayout.requestFocus();
                } else if (carTransmissiontxt.isEmpty()) {
                    transmissionlayout.setError("Enter Transmission Plz");
                    transmissionlayout.requestFocus();
                }else if(carOdormetertxt.isEmpty()){
                    odormeterlayout.setError("Enter Odormeter plx");
                    odormeterlayout.requestFocus();
                }
                else {
                    startActivity(new Intent(AboutCarActivity.this,ImageUploadActivity.class));
                }
            }
        });

    }
}
