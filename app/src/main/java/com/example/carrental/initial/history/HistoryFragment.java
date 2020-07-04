package com.example.carrental.initial.history;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carrental.R;
import com.example.carrental.initial.MainActivity;
import com.example.carrental.initial.home.CarsDetails;
import com.example.carrental.initial.home.CarsPojo;
import com.example.carrental.initial.home.HomeCarFragment;
import com.example.carrental.initial.host.HostPojo;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.Collection;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment {
    RecyclerView historyList;
    FirebaseFirestore carsDB;
    FirestoreRecyclerAdapter<HistoryPojo, HistoryrecyclerView> adapterHistory;

    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        historyList = view.findViewById(R.id.historyRecycleView);
        historyList.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));

        carsDB = FirebaseFirestore.getInstance();

        SharedPreferences sharedPreferences= getActivity().getSharedPreferences(MainActivity.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String email = sharedPreferences.getString(MainActivity.Email_shared, MainActivity.SHARED_PREF_NAME);



        Query query = carsDB.collection("bookedCar").whereEqualTo("user",email);

        FirestoreRecyclerOptions<HistoryPojo> options =new FirestoreRecyclerOptions.Builder<HistoryPojo>()
                .setQuery(query,HistoryPojo.class)
                .build();


        adapterHistory = new FirestoreRecyclerAdapter<HistoryPojo, HistoryrecyclerView>(options) {
            @NonNull
            @Override
            public HistoryrecyclerView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_list, parent, false);
                return new HistoryrecyclerView(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull final HistoryrecyclerView holder, int position, @NonNull final HistoryPojo model) {

                holder.vehicles_name.setText(model.getDropLocation());

                final String carId = model.getCarId();
                carsDB.collection("Cars")
                        .document(carId)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if(task.getResult() != null)
                                {
                                    DocumentSnapshot documentSnapshot = task.getResult();
                                    String image = (String) documentSnapshot.get("url_Of_CarImage");
                                    Picasso.with(getActivity())
                                          .load(image)
                                          .into(holder.vehicles_image);
                                    holder.vehicles_name.setText((CharSequence)documentSnapshot.get("brand"));
                                    Long rating = (Long) documentSnapshot.get("rating");
                                holder.vehicle_new_rating.setRating(rating);
                                if(model.isBooking()){
                                    holder.vehicles_status.setText("Confirmed");
                                }
                                else {
                                   holder.vehicles_status.setText("Cancelled");
                                   holder.vehicles_status.setTextColor(Color.RED);
                               }
                                }
                            }
                        });

                holder.vehicle_new_cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(),HistoryCarDetails.class);
                        intent.putExtra("carId",carId);
                        intent.putExtra("pickUpTime",model.getPickTime());
                        intent.putExtra("pickUpLocation",model.getPickLocation());
                        intent.putExtra("dropTime",model.getDropTime());
                        intent.putExtra("dropLocation",model.getDropLocation());
                        intent.putExtra("price",model.getPrice());
                        intent.putExtra("buttonStatus",model.isBooking());
                        startActivity(intent);
                    }
                });


            }
        };

//        FirestoreRecyclerOptions<HistoryPojo> options = new FirestoreRecyclerOptions.Builder<HistoryPojo>()
//                .setQuery(query, HistoryPojo.class)
//                .build();
//
//        adapterHistory = new FirestoreRecyclerAdapter<HistoryPojo, HistoryrecyclerView>(options) {
//            @NonNull
//            @Override
//            public HistoryrecyclerView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_list, parent, false);
//                return new HistoryrecyclerView(view);
//            }
//
//            @Override
//            protected void onBindViewHolder(@NonNull final HistoryrecyclerView holder, int position, @NonNull final HistoryPojo model) {
//                final String id = getSnapshots().getSnapshot(position).getId();
//                String carId = model.getCarId();
//                final FirebaseFirestore db = FirebaseFirestore.getInstance();
//                db.collection("Cars").document(carId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                        if(task.getResult() != null){
//                            DocumentSnapshot snapshot = task.getResult();
//
//                                Picasso.with(getActivity())
//                                        .load((Uri) snapshot.get("url_Of_CarImage"))
//                                        .into(holder.vehicles_image);
//                                holder.vehicles_name.setText((CharSequence) snapshot.get("brand"));
//                                holder.vehicle_new_rating.setRating((Float) snapshot.get("rating"));
//                                if(model.isBooking()){
//                                    holder.vehicles_status.setText("Confiremd");
//                                }
//                                else {
//                                    holder.vehicles_status.setText("Cancelled");
//                                }
//
//
//                        }
//
//                    }
//                });
//
//
//
//            }
//
//        };

        historyList.setAdapter(adapterHistory);
        adapterHistory.startListening();
        adapterHistory.notifyDataSetChanged();

        return view;
    }

    private class HistoryrecyclerView extends RecyclerView.ViewHolder {
        public ImageView vehicles_image;
        public TextView vehicles_name,vehicles_status;
        public CardView vehicle_new_cardView;
        public RatingBar vehicle_new_rating;
        public HistoryrecyclerView(@NonNull View itemView) {
            super(itemView);
            vehicles_image = itemView.findViewById(R.id.history_vehicles_image);
            vehicles_name = itemView.findViewById(R.id.history_vehicles_name);
            vehicles_status = itemView.findViewById(R.id.history_vehicles_status);
            vehicle_new_cardView = itemView.findViewById(R.id.history_cars_card);
            vehicle_new_rating = itemView.findViewById(R.id.history_vehicles_rate);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        adapterHistory.startListening();

    }
}




