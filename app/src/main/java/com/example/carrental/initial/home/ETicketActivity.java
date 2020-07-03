package com.example.carrental.initial.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.carrental.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.squareup.picasso.Picasso;


import java.util.HashMap;
import java.util.Map;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class ETicketActivity extends AppCompatActivity {
    ImageView imageView;
    Button button;
    long earning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e_ticket);

        imageView = findViewById(R.id.barcodeImage);
        button = findViewById(R.id.buttonGoHome);


        final SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("hello,sign in", MODE_PRIVATE);
        String carId = sharedPreferences.getString("id", "");
        final String user = sharedPreferences.getString("email", "");
        final String pickdateTime = sharedPreferences.getString("pickUpTime", "") + "-" + sharedPreferences.getString("pickDate", "");
        final String dropdateTime = sharedPreferences.getString("dropOffTime", "") + "-" + sharedPreferences.getString("dropDate", "");
        final String pickLocation = sharedPreferences.getString("pickUpLocation", "");
        final String dropLocation = sharedPreferences.getString("dropoffLocation", "");
        final String totalPrice = sharedPreferences.getString("totalPrice", "");

        final String ticket = "PickUp-" + pickdateTime + "," + pickLocation + "\n" +
                "DropOff-" + dropdateTime + "," + dropLocation + "\n" +
                "Total Price-" + totalPrice;

        if (carId != null || pickdateTime != null || dropdateTime != null || pickLocation != null || dropLocation != null || totalPrice != null) {
            QRGEncoder qrgEncoder = new QRGEncoder(ticket, null, QRGContents.Type.TEXT, 500);
            Bitmap qrBits = qrgEncoder.getBitmap();
            imageView.setImageBitmap(qrBits);

        }

        final Map<String, Object> data = new HashMap<>();
        data.put("status", false);
        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        final Map<String, Object> bookedCar = new HashMap<>();
        bookedCar.put("carId", carId);
        bookedCar.put("pickTime",pickdateTime);
        bookedCar.put("pickLocation",pickLocation);
        bookedCar.put("dropTime",dropdateTime);
        bookedCar.put("dropLocation",dropLocation);
        bookedCar.put("price",totalPrice);
        bookedCar.put("booking",true);
        bookedCar.put("user",user);

        db.collection("bookedCar").document(carId).set(bookedCar);



        db.collection("Cars").document(carId).set(data, SetOptions.merge());
        int price = Integer.parseInt(totalPrice);
        price = (int) (price - (price * 0.10));
        final Map<String, Object> data1 = new HashMap<>();
        data1.put("earning", price);
        final int finalPrice = price;
        db.collection("user").whereEqualTo("email", user).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                String userId = null;
                for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                    userId = documentSnapshot.getId();
                    System.out.println(userId);
                    if (documentSnapshot.get("earning") != null) {
                        earning = (long) documentSnapshot.get("earning");
                        earning += finalPrice;
                        data1.put("earning", earning);
                        db.collection("user").document(userId).set(data1, SetOptions.merge());
                    } else {
                        db.collection("user").document(userId).set(data1, SetOptions.merge());
                    }


                }


            }


        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            }
        });
    }


}