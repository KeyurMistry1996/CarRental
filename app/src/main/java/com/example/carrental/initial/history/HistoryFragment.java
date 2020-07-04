package com.example.carrental.initial.history;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    FirestoreRecyclerAdapter adapterHistory;

    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        historyList = view.findViewById(R.id.historyRecycleView);

        carsDB = FirebaseFirestore.getInstance();

        SharedPreferences sharedPreferences= getActivity().getSharedPreferences(MainActivity.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String email = sharedPreferences.getString(MainActivity.Email_shared, MainActivity.SHARED_PREF_NAME);



        Query query = carsDB.collection("Cars").whereEqualTo("user",email);

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
            protected void onBindViewHolder(@NonNull final HistoryrecyclerView holder, int position, @NonNull HistoryPojo model) {

                holder.vehicles_name.setText(model.getDropLocation());

                String carId = model.getCarId();
                Toast.makeText(getActivity(), carId, Toast.LENGTH_SHORT).show();
                System.out.println(carId);
                carsDB.collection("Cars")
                        .document(carId)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if(task.getResult() != null)
                                {
                                    DocumentSnapshot documentSnapshot = task.getResult();
                                    Picasso.with(getActivity())
                                          .load((Uri) documentSnapshot.get("url_Of_CarImage"))
                                          .into(holder.vehicles_image);
                                }
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
        historyList.setLayoutManager(new LinearLayoutManager(getActivity()));
        historyList.setAdapter(adapterHistory);
        return view;
    }

    public class HistoryrecyclerView extends RecyclerView.ViewHolder {
        public ImageView vehicles_image;
        public TextView vehicles_name,vehicles_status;
        public CardView vehicle_new_cardView;
        public RatingBar vehicle_new_rating;
        public HistoryrecyclerView(View view) {
            super(view);
            vehicles_image = view.findViewById(R.id.history_vehicles_image);
            vehicles_name = view.findViewById(R.id.history_vehicles_name);
            vehicles_status = view.findViewById(R.id.history_vehicles_status);
            vehicle_new_cardView = view.findViewById(R.id.history_cars_card);
            vehicle_new_rating = view.findViewById(R.id.history_vehicles_rate);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        adapterHistory.startListening();

    }
}




