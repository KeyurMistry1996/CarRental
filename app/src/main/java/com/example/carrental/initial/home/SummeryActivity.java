package com.example.carrental.initial.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carrental.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class SummeryActivity extends AppCompatActivity {
    ImageView carImage;
    TextView carName,pickupTimeDate,dropOffTimeDate,pickupLocation,dropoffLocation,price,tax,total;
    RatingBar ratingBar;
    Button button;
    long totalPrice;
    long taxTotal;
    long allTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summery);

        carImage = findViewById(R.id.carImageSummery);
        carName = findViewById(R.id.carNameSummery);
        pickupTimeDate = findViewById(R.id.pickupDateTimeSummery);
        pickupLocation = findViewById(R.id.pickupLocationSummery);
        dropOffTimeDate = findViewById(R.id.dropoffDateTimeSummery);
        dropoffLocation = findViewById(R.id.dropoffLocationSummery);
        price = findViewById(R.id.carChargePriceSummery);
        tax = findViewById(R.id.carChargesTaxSummery);
        total = findViewById(R.id.carChargesTotalSummery);
        ratingBar = findViewById(R.id.carSummeryRating);
        button = findViewById(R.id.buttonCheckoutSummery);

        final SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("hello,sign in",MODE_PRIVATE);
        String carId = sharedPreferences.getString("id","");
        final String pickdateTime =sharedPreferences.getString("pickUpTime","")+"-"+sharedPreferences.getString("pickDate","") ;
        final String dropdateTime =sharedPreferences.getString("dropOffTime","")+"-"+sharedPreferences.getString("dropDate","") ;
        final String pickLocation = sharedPreferences.getString("pickUpLocation","");
        final String dropLocation = sharedPreferences.getString("dropoffLocation","");
        final long dayString = sharedPreferences.getLong("days",0);



        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Cars").document(carId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Picasso.with(getApplicationContext())
                        .load(String.valueOf(documentSnapshot.get("url_Of_CarImage")))
                        .into(carImage);
                carName.setText((CharSequence) documentSnapshot.get("brand"));
                totalPrice = (long) documentSnapshot.get("price")*dayString;
                price.setText("$"+String.valueOf(totalPrice));
                Float stars = new Float(String.valueOf(documentSnapshot.get("rating")));
                ratingBar.setRating(stars);
                taxTotal = (long) (totalPrice*0.1);
                tax.setText("$"+String.valueOf(taxTotal));
                allTotal = totalPrice+taxTotal;
                total.setText(String.valueOf("$"+allTotal));
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("totalPrice", String.valueOf(allTotal));
                editor.commit();

            }
        });

        pickupTimeDate.setText(pickdateTime);
        pickupLocation.setText(pickLocation);
        dropOffTimeDate.setText(dropdateTime);
        dropoffLocation.setText(dropLocation);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),CheckoutActivity.class);
                startActivity(intent);
            }
        });


    }
}