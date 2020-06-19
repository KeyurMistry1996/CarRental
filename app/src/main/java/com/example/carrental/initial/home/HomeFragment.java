package com.example.carrental.initial.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.carrental.R;
import com.example.carrental.initial.MainActivity;
import com.example.carrental.initial.host.HostPojo;
import com.example.carrental.initial.host.tabFragment.vehiclesFragment;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private HomeCarFragment homeCarFragment;
    ImageView nodatafound;
    private FirebaseFirestore carcheck;

    FrameLayout framcar;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        carcheck = FirebaseFirestore.getInstance();
        nodatafound = view.findViewById(R.id.imagenodatafound);
        framcar = view.findViewById(R.id.frameCar);

        homeCarFragment = new HomeCarFragment();

        carcheck.collection("Cars").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            nodatafound.setVisibility(View.GONE);
                            setfragment(homeCarFragment);
                        }
                        else {
                            nodatafound.setVisibility(View.VISIBLE);
                        }
                    }
                });

        return view;

    }
    private void setfragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameCar, fragment);
        fragmentTransaction.commit();

    }


}
