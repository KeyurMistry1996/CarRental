package com.example.carrental.initial.host;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carrental.R;
import com.example.carrental.initial.MainActivity;
import com.example.carrental.initial.home.HomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class checkoutActivity extends AppCompatActivity {

    TextView earn;
    EditText withdrawAmount, bankname, accountnumber, transitnumber;
    Button finalwithdraw;

    FirebaseFirestore withdraw;

    RelativeLayout rr;
    String userrefID;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout2);

        earn = findViewById(R.id.earnt);
        withdrawAmount = findViewById(R.id.withdrawamount);
        bankname = findViewById(R.id.bankname);
        accountnumber = findViewById(R.id.accountnumber);
        transitnumber = findViewById(R.id.Transitnumber);
        finalwithdraw = findViewById(R.id.finalwithdraw);
        rr = findViewById(R.id.rr);

        withdraw = FirebaseFirestore.getInstance();

        SharedPreferences sharedPreferences =  getSharedPreferences(MainActivity.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        email = sharedPreferences.getString(MainActivity.Email_shared, MainActivity.SHARED_PREF_NAME);

        withdraw.collection("user")
                .whereEqualTo("email", email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                userrefID = document.getId();

                            }
                        }
                    }
                });

        Bundle bundle = getIntent().getExtras();

        final Float earntxt = bundle.getFloat("earn");

        earn.setText("$" + earntxt);

        finalwithdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String withdrawamount = withdrawAmount.getText().toString().trim();
                int withdrawamountint = Integer.parseInt(withdrawAmount.getText().toString().trim());
                String banknames = bankname.getText().toString().trim();
                String accountnumbers = accountnumber.getText().toString().trim();
                String transitnumbers = transitnumber.getText().toString().trim();

                if (withdrawamount.isEmpty()) {
                    withdrawAmount.setError("Plzz Enter Amount");
                } else if (banknames.isEmpty()) {
                    bankname.setError("Enter Bank name plz");
                } else if (accountnumbers.isEmpty()) {
                    accountnumber.setError("Enter Account number Plz");
                } else if (transitnumbers.isEmpty()) {
                    transitnumber.setError("Enter Transit number Plz");
                }else if(withdrawamountint > earntxt) {
                    Toast.makeText(checkoutActivity.this, "Enter Small amount the your Earned Amount", Toast.LENGTH_LONG).show();
                }else {
                    Snackbar snackbar1 = Snackbar.make(rr, "Hey your request of withdrawing $" + withdrawamountint + " has been received . within a 5-10" +
                            " days it will be credited into your account", Snackbar.LENGTH_LONG);
                    snackbar1.show();

                    int newearning = (int) (earntxt - withdrawamountint);
                    final DocumentReference documentReference = FirebaseFirestore.getInstance()
                            .collection("user")
                            .document(userrefID);
                    Map<String, Object> changepassword = new HashMap<>();
                    changepassword.put("earning", newearning);
                    documentReference.update(changepassword)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                        }
                    });

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent=new Intent(getApplicationContext(), HomeActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    },5000);

                }

            }
        });


    }
}