package com.example.carrental.initial.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carrental.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

public class CarsDetails extends AppCompatActivity {

    ImageView carImage,people,autometic,airbag,gps,bluetooth,back;
    TextView carName,carPrice,cardexcription;
    RatingBar carRating;
    Button book;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cars_details);

        carImage = findViewById(R.id.carDetailsImage);
        people = findViewById(R.id.carDetailsPerson);
        autometic = findViewById(R.id.carDetailsAuto);
        airbag = findViewById(R.id.carDetailsAirbag);
        gps = findViewById(R.id.carDetailsGPS);
        bluetooth = findViewById(R.id.carDetailsBluetooth);
        back = findViewById(R.id.backarrowCarDetails);

        carName = findViewById(R.id.carDetailsName);
        carPrice = findViewById(R.id.carDetailsPrice);
        cardexcription = findViewById(R.id.carDetailsDescription);

        carRating = findViewById(R.id.carDetailsRating);
        book = findViewById(R.id.carDetailsBook);

        final Intent intent = getIntent();
        final String id = intent.getStringExtra("id");


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Cars")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String string = document.getId();
                                if(id.equals(string)){
                                    Picasso.with(getApplicationContext())
                                            .load(String.valueOf(document.get("url_Of_CarImage")))
                                            .into(carImage);
                                    carName.setText((CharSequence) document.get("brand"));
                                    carPrice.setText("$"+document.get("price")+"/day");
                                    Float stars = new Float(String.valueOf(document.get("rating")));
                                    carRating.setRating(stars);
                                    cardexcription.setText((CharSequence) document.get("description"));

                                    if(!document.getBoolean("airbag")){
                                        airbag.setAlpha((float) 0.3);
                                    }
                                    if(!document.getBoolean("automatic")){
                                        airbag.setAlpha((float) 0.3);
                                    }
                                    if(!document.getBoolean("gps")){
                                        airbag.setAlpha((float) 0.3);
                                    }
                                    if(!document.getBoolean("bluetooth")){
                                        airbag.setAlpha((float) 0.3);
                                    }

                                }
                            }
                        } else {
                            Log.w("Error", "Error getting documents.", task.getException());
                        }
                    }
                });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String activity = intent.getStringExtra("Activity");
                if(activity.equals("SeeAllCars")){
                    startActivity(new Intent(getApplicationContext(),SeeAllCarsActivity.class));
                    finish();
                }
                else {
                    startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                    finish();
                }
            }
        });

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ScheduleActivity.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });

    }
}