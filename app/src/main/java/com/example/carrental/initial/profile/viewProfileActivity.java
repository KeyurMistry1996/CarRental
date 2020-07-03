package com.example.carrental.initial.profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carrental.R;
import com.example.carrental.initial.MainActivity;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class viewProfileActivity extends AppCompatActivity {

    ImageView name_edit,email_edit,number_edit;
    RelativeLayout rrname,rremail,rrnumber;

    Button changename,changeemail,changenumber,saveProfile;
    TextView username,useremail,usernumber;
    EditText etchangename,etchangeemail,etchangenumber;
    TextInputLayout change_name,change_email,change_number;

    FirebaseFirestore viewprofile;

    String userrefID;

    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        //Textviiew
        username = findViewById(R.id.username123);
        useremail = findViewById(R.id.useremail123);
        usernumber = findViewById(R.id.user_number);

        //Edittext
        etchangename = findViewById(R.id.etchangename);
        etchangeemail = findViewById(R.id.etchangeemail);
        etchangenumber = findViewById(R.id.etchangenumber);

        //textInputLayout
        change_name = findViewById(R.id.change_name);
        change_email = findViewById(R.id.change_email);
        change_number = findViewById(R.id.change_number);


        viewprofile = FirebaseFirestore.getInstance();


        SharedPreferences sharedPreferences= getSharedPreferences(MainActivity.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        email=sharedPreferences.getString(MainActivity.Email_shared,MainActivity.SHARED_PREF_NAME);

        viewprofile.collection("user")
                .whereEqualTo("email",email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                userrefID = document.getId();
                                String name = (String) document.get("name");
                                username.setText(name);
                                String email = (String) document.get("email");
                                useremail.setText(email);
                                String number = (String) document.get("number");
                                usernumber.setText(number);

                            }
                        }
                    }
                });

        // Edit Image Button
        name_edit = findViewById(R.id.name_edit);
        email_edit = findViewById(R.id.email_edit);
        number_edit = findViewById(R.id.number_edit);

        // Relative Layout
        rrname = findViewById(R.id.rrname);
        rremail = findViewById(R.id.rremail);
        rrnumber = findViewById(R.id.rrnumber);

        //change Button
        changename = findViewById(R.id.changename);
        changeemail=findViewById(R.id.changeemail);
        changenumber=findViewById(R.id.changenumber);

        //Save button
        saveProfile=findViewById(R.id.saveProfile);

        saveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DocumentReference documentReference = FirebaseFirestore.getInstance()
                        .collection("user")
                        .document(userrefID);

                Map<String , Object> updateProfile = new HashMap<>();

                updateProfile.put("name" ,username.getText().toString());
                updateProfile.put("email" ,useremail.getText().toString());
                updateProfile.put("number" ,usernumber.getText().toString());

                documentReference.update(updateProfile)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(viewProfileActivity.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(viewProfileActivity.this, "Profile Update Fail", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        changename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username.setText(etchangename.getText().toString());
                rrname.setVisibility(View.GONE);
            }
        });

        changeemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                useremail.setText(etchangeemail.getText().toString());
                rremail.setVisibility(View.GONE);
            }
        });

        changenumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(etchangenumber.getText().toString().isEmpty())
                {

                }
                usernumber.setText(etchangenumber.getText().toString());
                rrnumber.setVisibility(View.GONE);
            }
        });

        name_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etchangename.setText(username.getText().toString());
                rrname.setVisibility(View.VISIBLE);

            }
        });
        email_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etchangeemail.setText(useremail.getText().toString());
                rremail.setVisibility(View.VISIBLE);
            }
        });
        number_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etchangenumber.setText(usernumber.getText().toString());
                rrnumber.setVisibility(View.VISIBLE);
            }
        });
    }
}