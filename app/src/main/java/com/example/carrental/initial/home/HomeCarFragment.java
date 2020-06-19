package com.example.carrental.initial.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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


    RecyclerView newArrivalList;
    FirebaseFirestore newArrivalDB;

    FirestoreRecyclerAdapter adapternewArrival;

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
        newArrivalDB = FirebaseFirestore.getInstance();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(MainActivity.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final String email = sharedPreferences.getString(MainActivity.Email_shared, MainActivity.SHARED_PREF_NAME);

        Query query = newArrivalDB.collection("Cars");

        System.out.println(query);
        FirestoreRecyclerOptions<HostPojo> options =new FirestoreRecyclerOptions.Builder<HostPojo>()
                .setLifecycleOwner(this)
                .setQuery(query,HostPojo.class)
                .build();

        adapternewArrival = new FirestoreRecyclerAdapter<HostPojo , newArrivalView>(options){
            @NonNull
            @Override
            public newArrivalView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hostvehiclesitem,parent,false);
                return new newArrivalView(view);
            }
            @Override
            protected void onBindViewHolder(@NonNull newArrivalView holder, int position, @NonNull HostPojo model) {
                Picasso.with(getActivity())
                        .load(model.getUrl_Of_CarImage())
                        .placeholder(R.mipmap.ic_launcher)
                        .into(holder.vehicles_image);
                holder.vehicles_name.setText(model.getBrand());
                holder.vehicles_number.setText(model.getTransmission());
                Log.i("name" ,model.getBrand());
                System.out.println("Name  : " + model.getBrand());
            }
        };
        newArrivalList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL , false);
        newArrivalList.setLayoutManager(linearLayoutManager);
        newArrivalList.setAdapter(adapternewArrival);

        return view;
    }
    private class newArrivalView extends RecyclerView.ViewHolder {

        private ImageView vehicles_image;
        private TextView vehicles_name,vehicles_number;
        private RatingBar vehicles_rate;

        public newArrivalView(@NonNull View itemView) {
            super(itemView);

            vehicles_image = itemView.findViewById(R.id.vehicles_image);
            vehicles_name = itemView.findViewById(R.id.vehicles_name);
            vehicles_number = itemView.findViewById(R.id.vehicles_number);

        }
    }

    @Override
    public void onStart() {
        super.onStart();
        adapternewArrival.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapternewArrival.stopListening();
    }
}