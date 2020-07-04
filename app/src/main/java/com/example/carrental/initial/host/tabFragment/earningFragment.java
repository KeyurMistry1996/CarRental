package com.example.carrental.initial.host.tabFragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.carrental.R;
import com.example.carrental.initial.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class earningFragment extends Fragment {

    TextView earningtxt,messagetxt;
    Button withdraw;

    FirebaseFirestore earning;

    String email;

    public earningFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_earning, container, false);

        earningtxt=view.findViewById(R.id.earningtxt);
        messagetxt=view.findViewById(R.id.messagetxt);
        withdraw=view.findViewById(R.id.withdraw);

        earning = FirebaseFirestore.getInstance();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(MainActivity.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        email = sharedPreferences.getString(MainActivity.Email_shared, MainActivity.SHARED_PREF_NAME);

        earning.collection("user")
                .whereEqualTo("email", email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Float earn = new Float(String.valueOf(document.get("earning")));
                                if(earn > 0)
                                {
                                    earningtxt.setText("$"+earn);
                                    messagetxt.setText("Conratulation !!! Keep Posting your vehicals and earn more..");
                                    withdraw.setVisibility(View.VISIBLE);
                                }

                            }
                        }
                    }
                });




        return view;
    }
}
