package com.example.carrental.admin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carrental.R;
import com.example.carrental.initial.MainActivity;
import com.example.carrental.initial.home.ScheduleActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class CarInfoActivity extends AppCompatActivity {
    ImageView carImage,people,autometic,airbag,gps,bluetooth,back;
    TextView carName,carPrice,cardexcription;
    RatingBar carRating;
    Button delete;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_info);

        carImage = findViewById(R.id.carImage);
        people = findViewById(R.id.carPerson);
        autometic = findViewById(R.id.carAuto);
        airbag = findViewById(R.id.carAirbag);
        gps = findViewById(R.id.carGPS);
        bluetooth = findViewById(R.id.carBluetooth);

        carName = findViewById(R.id.carName);
        carPrice = findViewById(R.id.carPrice);
        cardexcription = findViewById(R.id.carDescription);

        carRating = findViewById(R.id.carRating);
        delete = findViewById(R.id.carDelete);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Cars").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
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

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(CarInfoActivity.this)
                        .setTitle("Delete")
                        .setMessage("Are you sure you want to delete?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Continue with delete operation
                                db.collection("Cars").document(id).delete();
                                Intent intent = new Intent(getApplicationContext(), ManageCarListActivity.class);
                                intent.putExtra("id",id);
                                startActivity(intent);

                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(R.drawable.alert_icon)
                        .show();


            }
        });


    }
}