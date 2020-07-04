package com.example.carrental.initial.host;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.carrental.R;
import com.example.carrental.initial.MainActivity;
import com.example.carrental.initial.host.tabFragment.earningFragment;
import com.example.carrental.initial.host.tabFragment.vehiclesFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

/**
 * A simple {@link Fragment} subclass.
 */
public class HostFragment extends Fragment {
    Button host;
    RelativeLayout showhost;
    LinearLayout starthost;
    TabLayout host_tab;
    private FirebaseFirestore usercheck;
    Button gettoaboutcar;

    private vehiclesFragment vehiclesFragment;
    private earningFragment earningFragment;

    FrameLayout tabfram;

    public HostFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_host, container, false);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(MainActivity.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final String email = sharedPreferences.getString(MainActivity.Email_shared, MainActivity.SHARED_PREF_NAME);

        usercheck = FirebaseFirestore.getInstance();

        vehiclesFragment = new vehiclesFragment();
        earningFragment = new earningFragment();

        setfragment(vehiclesFragment);


        gettoaboutcar = v.findViewById(R.id.gettoaboutcar);
        host = v.findViewById(R.id.hoststart);
        showhost = v.findViewById(R.id.show_host);
        starthost = v.findViewById(R.id.host_start);
        host_tab = v.findViewById(R.id.host_tab);

        tabfram = v.findViewById(R.id.fram_host);


        usercheck.collection("Cars")
                .whereEqualTo("user_email", email)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    showhost.setVisibility(View.VISIBLE);
                    starthost.setVisibility(View.GONE);
                } else {
                    starthost.setVisibility(View.VISIBLE);
                    showhost.setVisibility(View.GONE);
                }
            }
        });
        host_tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                switch (position) {
                    case 0:
                        gettoaboutcar.setVisibility(View.VISIBLE);
                        setfragment(vehiclesFragment);
                        break;
                    case 1:
                        gettoaboutcar.setVisibility(View.GONE);
                        setfragment(earningFragment);
                        break;

                    default:
                        setfragment(vehiclesFragment);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        gettoaboutcar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AboutCarActivity.class));
            }
        });

        host.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AboutCarActivity.class));
            }
        });


        return v;
    }

    private void setfragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fram_host, fragment);
        fragmentTransaction.commit();

    }
}
