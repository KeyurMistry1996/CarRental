package com.example.carrental.initial.search;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.carrental.R;
import com.example.carrental.initial.home.CarsDetails;
import com.example.carrental.initial.home.CarsPojo;
import com.example.carrental.initial.home.SeeAllView;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

public class SeeAllCarSearch extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseFirestore rootRef;
    private FirestoreRecyclerAdapter<CarsPojo, SeeAllView> adapterSeeAll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_all_car_search);

        recyclerView = findViewById(R.id.seeAllSearchRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));

        rootRef = FirebaseFirestore.getInstance();

        Query query = rootRef.collection("Cars").whereEqualTo("status",true);
        FirestoreRecyclerOptions<CarsPojo> options = new FirestoreRecyclerOptions.Builder<CarsPojo>()
                .setQuery(query, CarsPojo.class)
                .build();

        adapterSeeAll = new FirestoreRecyclerAdapter<CarsPojo,SeeAllView >(options) {
            @Override
            protected void onBindViewHolder(@NonNull final SeeAllView holder, int position, @NonNull final CarsPojo model) {
                final String id = getSnapshots().getSnapshot(position).getId();
                Picasso.with(getApplicationContext())
                        .load(model.getUrl_Of_CarImage())
                        .into(holder.see_all_vehicles_image);
                holder.see_all_vehicles_name.setText(model.getBrand());
                holder.see_all_vehicles_number.setText(model.getTransmission());
                holder.see_all_rating.setRating(model.getRating());
                holder.see_all_vehicle_new_cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences sharedPreferences =getApplicationContext().getSharedPreferences("hello,sign in", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("id",id);
                        editor.commit();
                        Intent intent = new Intent(getApplicationContext(), CarsDetails.class);
                        intent.putExtra("id",id);
                        intent.putExtra("Search",true);
                        startActivity(intent);


                    }
                });



            }

            @NonNull
            @Override
            public SeeAllView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.see_all_list,parent,false);
                return new SeeAllView(view);
            }


        };
        recyclerView.setAdapter(adapterSeeAll);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapterSeeAll.startListening();

    }

    @Override
    public void onStop() {
        super.onStop();

        if (adapterSeeAll != null) {
            adapterSeeAll.stopListening();
        }


    }
}