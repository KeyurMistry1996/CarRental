package com.example.carrental.initial.history;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class HistoryCarDetails extends AppCompatActivity {
    ImageView imageView;
    TextView name,pickDate,pickLocation,dropDate,dropLocation,price;
    RatingBar ratingBar;
    Button cancelled;
    FirebaseFirestore carsDB;
    String id;
    boolean status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_car_details);
        imageView = findViewById(R.id.carImageHistory);
        name = findViewById(R.id.carNameHistory);
        pickDate = findViewById(R.id.pickupDateTimeHistory);
        pickLocation = findViewById(R.id.pickupLocationHistory);
        dropDate = findViewById(R.id.dropoffDateTimeHistory);
        dropLocation = findViewById(R.id.dropoffLocationHistory);
        price = findViewById(R.id.carChargesTotalHistory);
        ratingBar = findViewById(R.id.carHistoryRating);
        cancelled = findViewById(R.id.buttonCancelled);

        Intent intent = getIntent();
        id = intent.getStringExtra("carId");
        String pickTimeString = intent.getStringExtra("pickUpTime");
        String pickLocationString = intent.getStringExtra("pickUpLocation");
        String dropTimeString = intent.getStringExtra("dropTime");
        String dropLocationString = intent.getStringExtra("dropLocation");
        String priceString = intent.getStringExtra("price");
        status = intent.getBooleanExtra("buttonstatus",true);


        if(status){
            cancelled.setVisibility(View.VISIBLE);
        }
        else {
            cancelled.setVisibility(View.GONE);
        }

        pickDate.setText(pickTimeString);
        pickLocation.setText(pickLocationString);
        dropDate.setText(dropTimeString);
        dropLocation.setText(dropLocationString);
        price.setText("$"+priceString);

        carsDB = FirebaseFirestore.getInstance();
        carsDB.collection("Cars").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String url = (String) documentSnapshot.get("url_Of_CarImage");
                Picasso.with(getApplicationContext())
                        .load(url)
                        .into(imageView);
                name.setText((CharSequence) documentSnapshot.get("brand"));
                Long rating = (Long) documentSnapshot.get("rating");
                ratingBar.setRating(rating);

            }
        });

        cancelled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentReference reference = FirebaseFirestore.getInstance().collection("Cars").document(id);
                Map<String,Object> data = new HashMap<>();
                data.put("status",true);
                reference.update(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(HistoryCarDetails.this, "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });

                DocumentReference reference1 = FirebaseFirestore.getInstance().collection("bookedCar").document(id);
                Map<String,Object> data1 = new HashMap<>();
                data1.put("booking",false);
                reference1.update(data1).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        cancelled.setVisibility(View.GONE);
                    }
                });
            }
        });

    }
}