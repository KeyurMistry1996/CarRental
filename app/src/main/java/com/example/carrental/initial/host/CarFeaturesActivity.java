package com.example.carrental.initial.host;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.carrental.R;
import com.google.android.material.snackbar.Snackbar;

public class CarFeaturesActivity extends AppCompatActivity {
    CheckBox gps_host, bluetooth_host, autometic_host, airbag_host, audio_host;
    EditText description_host, person_host;
    Button next_feature;

    LinearLayout llf;

    public static String description, person_count;
    public static boolean gps, bluetooth, automatic, airbag, audio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_features);

        gps_host = findViewById(R.id.gps_host);
        bluetooth_host = findViewById(R.id.bluetooth_host);
        autometic_host = findViewById(R.id.autometic_host);
        airbag_host = findViewById(R.id.airbag_host);
        audio_host = findViewById(R.id.audio_host);

        llf = findViewById(R.id.llf);

        description_host = findViewById(R.id.description_host);
        person_host = findViewById(R.id.person_number);

        next_feature = findViewById(R.id.next_feature);

        next_feature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                description = description_host.getText().toString().trim();
                person_count = person_host.getText().toString().trim();

                if (description.isEmpty()) {
                    Snackbar snackbar = Snackbar
                            .make(llf, "Plzz Enter Description", Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else if (person_count.isEmpty()) {
                    Snackbar snackbar = Snackbar
                            .make(llf, "Pzz give number ", Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else {
                    gps = gps_host.isChecked();
                    bluetooth = bluetooth_host.isChecked();
                    automatic = autometic_host.isChecked();
                    audio = audio_host.isChecked();
                    airbag = airbag_host.isChecked();
                    startActivity(new Intent(CarFeaturesActivity.this, PriceActivity.class));
                }
            }
        });
    }
}
