package com.example.carrental.initial.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
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
     String id;



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

        carName = findViewById(R.id.carDetailsName);
        carPrice = findViewById(R.id.carDetailsPrice);
        cardexcription = findViewById(R.id.carDetailsDescription);

        carRating = findViewById(R.id.carDetailsRating);
        book = findViewById(R.id.carDetailsBook);

        final Intent intent = getIntent();
        id = intent.getStringExtra("id");
        final boolean search = intent.getBooleanExtra("Search",false);


        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("hello,sign in",MODE_PRIVATE);
        String carId = sharedPreferences.getString("id","");

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Cars").document(carId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Picasso.with(getApplicationContext())
                        .load(String.valueOf(documentSnapshot.get("url_Of_CarImage")))
                        .into(carImage);
                carName.setText((CharSequence) documentSnapshot.get("brand"));
                carPrice.setText("$"+documentSnapshot.get("price")+"/day");
                Float stars = new Float(String.valueOf(documentSnapshot.get("rating")));
                carRating.setRating(stars);
                cardexcription.setText((CharSequence) documentSnapshot.get("description"));

                if(!documentSnapshot.getBoolean("airbag")){
                    airbag.setAlpha((float) 0.3);
                }
                if(!documentSnapshot.getBoolean("automatic")){
                    airbag.setAlpha((float) 0.3);
                }
                if(!documentSnapshot.getBoolean("gps")){
                    airbag.setAlpha((float) 0.3);
                }
                if(!documentSnapshot.getBoolean("bluetooth")){
                    airbag.setAlpha((float) 0.3);
                }
            }
        });




        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(search){
                    Intent intent = new Intent(getApplicationContext(),DrivingLicence.class);
                    intent.putExtra("id",id);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(getApplicationContext(),ScheduleActivity.class);
                    intent.putExtra("id",id);
                    startActivity(intent);
                }

            }
        });

    }
}