package com.example.carrental.initial.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carrental.R;
import com.example.carrental.initial.MainActivity;
import com.example.carrental.initial.host.HostPojo;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeCarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeCarFragment extends Fragment {


    RecyclerView newArrivalList,popularList;
    FirebaseFirestore carsDB;
    FirestoreRecyclerAdapter<CarsPojo, NewArrivalView> adapternewArrival;
    FirestoreRecyclerAdapter<CarsPojo,PopularView> adapterPopular;
    TextView see_all_new,see_all_popular;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeCarFragment() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeCarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeCarFragment newInstance(String param1, String param2) {
        HomeCarFragment fragment = new HomeCarFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            adapternewArrival.startListening();

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home_car, container, false);

        newArrivalList = view.findViewById(R.id.newArrivalList);
        newArrivalList.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

        popularList = view.findViewById(R.id.popularList);
        popularList.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));

        see_all_new = view.findViewById(R.id.see_all_new);
        see_all_popular = view.findViewById(R.id.see_all_popular);

        carsDB = FirebaseFirestore.getInstance();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(MainActivity.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final String email = sharedPreferences.getString(MainActivity.Email_shared, MainActivity.SHARED_PREF_NAME);


        final Query query = carsDB.collection("Cars").whereEqualTo("popularity","New Arrival");
        final Query queryPopular = carsDB.collection("Cars").whereEqualTo("popularity","New Arrival");

        see_all_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),SeeAllCarsActivity.class);
                intent.putExtra("Title","New Arrival");
                intent.putExtra("Query", "new");
                startActivity(intent);
            }
        });

        see_all_popular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),SeeAllCarsActivity.class);
                intent.putExtra("Title","Most Popular");
                intent.putExtra("Query", "popular");
                startActivity(intent);
            }
        });


        FirestoreRecyclerOptions<CarsPojo> options = new FirestoreRecyclerOptions.Builder<CarsPojo>()
                .setQuery(query, CarsPojo.class)
                .build();

        FirestoreRecyclerOptions<CarsPojo> optionsPopular = new FirestoreRecyclerOptions.Builder<CarsPojo>()
                .setQuery(queryPopular, CarsPojo.class)
                .build();

        adapternewArrival = new FirestoreRecyclerAdapter<CarsPojo, NewArrivalView>(options){
            @Override
            protected void onBindViewHolder(@NonNull NewArrivalView holder, int position, @NonNull CarsPojo model) {
                final String id = getSnapshots().getSnapshot(position).getId();
                Picasso.with(getActivity())
                        .load(model.getUrl_Of_CarImage())
                        .into(holder.vehicles_image);
                holder.vehicles_name.setText(model.getBrand());
                holder.vehicles_number.setText(model.getTransmission());
                holder.vehicle_new_rating.setRating(model.getRating());
                holder.vehicle_new_cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(),CarsDetails.class);
                        intent.putExtra("id",id);
                        intent.putExtra("Activity","HomeCarFragment");
                        startActivity(intent);
                    }
                });

            }

            @NonNull
            @Override
            public NewArrivalView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_new,parent,false);
                return new NewArrivalView(view);
            }


        };

        adapterPopular = new FirestoreRecyclerAdapter<CarsPojo, PopularView>(optionsPopular) {
            @Override
            protected void onBindViewHolder(@NonNull PopularView holder, int position, @NonNull CarsPojo model) {
                final String id = getSnapshots().getSnapshot(position).getId();
                Picasso.with(getActivity())
                        .load(model.getUrl_Of_CarImage())
                        .into(holder.vehicles_image);
                holder.vehicles_name.setText(model.getBrand());
                holder.vehicles_number.setText(model.getTransmission());
                holder.vehicle_popular_rating.setRating(model.getRating());
                holder.vehicle_popular_cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(),CarsDetails.class);
                        intent.putExtra("id",id);
                        intent.putExtra("Activity","HomeCarFragment");
                        startActivity(intent);
                    }
                });

            }

            @NonNull
            @Override
            public PopularView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_popular,parent,false);
                return new PopularView(view);
            }
        };

        newArrivalList.setAdapter(adapternewArrival);
        popularList.setAdapter(adapterPopular);
        return view;
    }
    private class NewArrivalView extends RecyclerView.ViewHolder {

        private ImageView vehicles_image;
        private TextView vehicles_name,vehicles_number;
        private CardView vehicle_new_cardView;
        private RatingBar vehicle_new_rating;


        public NewArrivalView(@NonNull View itemView) {
            super(itemView);

            vehicles_image = itemView.findViewById(R.id.new_vehicles_image);
            vehicles_name = itemView.findViewById(R.id.new_vehicles_name);
            vehicles_number = itemView.findViewById(R.id.new_vehicles_number);
            vehicle_new_cardView = itemView.findViewById(R.id.new_cars_card);
            vehicle_new_rating = itemView.findViewById(R.id.new_vehicles_rate);

        }
    }


    private class PopularView extends RecyclerView.ViewHolder{
        private ImageView vehicles_image;
        private TextView vehicles_name,vehicles_number;
        private CardView vehicle_popular_cardView;
        private RatingBar vehicle_popular_rating;

        public PopularView(@NonNull View itemView) {
            super(itemView);
            vehicles_image = itemView.findViewById(R.id.new_vehicles_image);
            vehicles_name = itemView.findViewById(R.id.new_vehicles_name);
            vehicles_number = itemView.findViewById(R.id.new_vehicles_number);
            vehicle_popular_cardView = itemView.findViewById(R.id.popular_cars_card);
            vehicle_popular_rating = itemView.findViewById(R.id.popular_vehicles_rate);

        }
    }




    @Override
    public void onStart() {
        super.onStart();
        adapternewArrival.startListening();
        adapterPopular.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();

        if (adapternewArrival != null) {
            adapternewArrival.stopListening();
        }
        if(adapterPopular != null){
            adapterPopular.stopListening();
        }

    }
}