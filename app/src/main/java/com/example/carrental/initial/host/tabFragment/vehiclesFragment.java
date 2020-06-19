package com.example.carrental.initial.host.tabFragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.carrental.R;
import com.example.carrental.initial.MainActivity;
import com.example.carrental.initial.host.HostPojo;
import com.example.carrental.initial.host.PriceActivity;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class vehiclesFragment extends Fragment {
    RecyclerView host_car_list;
    FirebaseFirestore carHostdb;

    private FirestoreRecyclerAdapter adapterHostCar;

    public vehiclesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vehicles, container, false);

        host_car_list=view.findViewById(R.id.host_car_list);
        carHostdb = FirebaseFirestore.getInstance();

        SharedPreferences sharedPreferences= getActivity().getSharedPreferences(MainActivity.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final String email=sharedPreferences.getString(MainActivity.Email_shared,MainActivity.SHARED_PREF_NAME);


        Query query = carHostdb.collection("Cars").whereEqualTo("user_email",email);

        FirestoreRecyclerOptions<HostPojo> options =new FirestoreRecyclerOptions.Builder<HostPojo>()
                .setQuery(query,HostPojo.class)
                .build();

        adapterHostCar = new FirestoreRecyclerAdapter<HostPojo, HostviewHolder>(options) {
            @NonNull
            @Override
            public HostviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hostvehiclesitem,parent,false);
                return new HostviewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull HostviewHolder holder, int position, @NonNull HostPojo model) {
                Picasso.with(getActivity())
                        .load(model.getUrl_Of_CarImage())
                        .placeholder(R.mipmap.ic_launcher)
                        .into(holder.vehicles_image);
                holder.vehicles_name.setText(model.getBrand());
                holder.vehicles_number.setText(model.getTransmission());
//                holder.vehicles_rate.setRating(model.getRating());
            }
        };
        host_car_list.setHasFixedSize(true);
        host_car_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        host_car_list.setAdapter(adapterHostCar);

        return view;
    }

    private class HostviewHolder extends RecyclerView.ViewHolder {

        private ImageView vehicles_image;
        private TextView vehicles_name,vehicles_number;
        private RatingBar vehicles_rate;

        public HostviewHolder(@NonNull View itemView) {
            super(itemView);

            vehicles_image = itemView.findViewById(R.id.vehicles_image);
            vehicles_name = itemView.findViewById(R.id.vehicles_name);
            vehicles_number = itemView.findViewById(R.id.vehicles_number);

        }
    }

    @Override
    public void onStart() {
        super.onStart();
        adapterHostCar.startListening();
    }
}
