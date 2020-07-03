package com.example.carrental.admin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carrental.R;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class User_Info extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TextView title,name,email,number;
        Button button;
        final FirebaseFirestore rootRef;
        ImageView imageView ;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__info);
        title = findViewById(R.id.title);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        number = findViewById(R.id.number);
        imageView = findViewById(R.id.image);
        button = findViewById(R.id.button);
        rootRef = FirebaseFirestore.getInstance();

        final Intent intent = getIntent();
        name.setText(intent.getStringExtra("name"));
        email.setText(intent.getStringExtra("email"));
        number.setText(intent.getStringExtra("number"));

        Picasso.with(getApplicationContext())
                .load(intent.getStringExtra("image"))
                .placeholder(R.drawable.bluetooth)
                .into(imageView);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(User_Info.this)
                        .setTitle("Delete "+intent.getStringExtra("name"))
                        .setMessage("Are you sure you want to delete "+intent.getStringExtra("name")+"?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Continue with delete operation
                                DocumentReference reference = rootRef.collection("user").document(intent.getStringExtra("id"));
                                reference.delete();
                                Toast.makeText(User_Info.this, "Deleted", Toast.LENGTH_SHORT).show();
                                finish();
                                overridePendingTransition(0,R.anim.slide_exite);
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(R.drawable.alert_icon)
                        .show();



            }
        });
    }
}
