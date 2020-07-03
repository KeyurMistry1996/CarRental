package com.example.carrental.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.carrental.R;
import com.example.carrental.initial.home.CarsDetails;
import com.example.carrental.initial.home.CarsPojo;
import com.example.carrental.initial.home.HomeCarFragment;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

public class ManageCarListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FirebaseFirestore carsDB;
    private FirestoreRecyclerAdapter<CarsPojo,CarView> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_car_list);
        recyclerView = findViewById(R.id.manageCarRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        carsDB = FirebaseFirestore.getInstance();
        final Query query = carsDB.collection("Cars");

        FirestoreRecyclerOptions<CarsPojo> options = new FirestoreRecyclerOptions.Builder<CarsPojo>()
                .setQuery(query, CarsPojo.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<CarsPojo, CarView>(options) {
            @SuppressLint("ResourceAsColor")
            @Override
            protected void onBindViewHolder(@NonNull CarView holder, int position, @NonNull CarsPojo model) {
                final String id = getSnapshots().getSnapshot(position).getId();
                Picasso.with(getApplicationContext())
                        .load(model.getUrl_Of_CarImage())
                        .into(holder.vehicles_image);
                holder.vehicles_name.setText(model.getBrand());
                holder.vehicle_popular_rating.setRating(model.getRating());
holder.vehicles_number.setText(model.getTransmission());

               holder.vehicle_popular_cardView.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       Intent intent = new Intent(getApplicationContext(),CarInfoActivity.class);
                       intent.putExtra("id",id);
                       startActivity(intent);

                   }
               });



            }

            @NonNull
            @Override
            public CarView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.see_all_list, parent, false);
                return new CarView(view);
            }
        };

        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();

    }
}