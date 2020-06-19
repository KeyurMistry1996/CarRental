package com.example.carrental.initial.host;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.carrental.R;
import com.example.carrental.initial.MainActivity;
import com.example.carrental.initial.home.HomeActivity;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import static com.example.carrental.initial.host.AboutCarActivity.carBrandtxt;
import static com.example.carrental.initial.host.AboutCarActivity.carModeltxt;
import static com.example.carrental.initial.host.AboutCarActivity.carOdormetertxt;
import static com.example.carrental.initial.host.AboutCarActivity.carTransmissiontxt;
import static com.example.carrental.initial.host.AboutCarActivity.carYeartxt;

public class PriceActivity extends AppCompatActivity {

    EditText price_host;
    Button host;
    String price_txt;

    StorageReference sRef;
    FirebaseFirestore Host_firebaseStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price);

        SharedPreferences sharedPreferences= PriceActivity.this.getSharedPreferences(MainActivity.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final String email=sharedPreferences.getString(MainActivity.Email_shared,MainActivity.SHARED_PREF_NAME);

        Host_firebaseStorage = FirebaseFirestore.getInstance();
        sRef = FirebaseStorage.getInstance().getReference("Host_car_image");

        price_host=findViewById(R.id.price_host);
        host=findViewById(R.id.host);

        host.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                price_txt = price_host.getText().toString().trim();
                if(price_txt.isEmpty())
                {
                    Toast.makeText(PriceActivity.this, "Plz.. Enter Price for the car ", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    final ProgressDialog progressDialog = new ProgressDialog(PriceActivity.this);
                    progressDialog.setTitle("Uploading");
                    progressDialog.show();
                    final StorageReference storageReference = sRef.child(System.currentTimeMillis() + "." + getFileExtension(ImageUploadActivity.filePath));

                    storageReference.putFile(ImageUploadActivity.filePath).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }
                            return storageReference.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful())
                            {
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressDialog.setProgress(0);
                                    }
                                }, 500);
                            }
                            String downloadUri = task.getResult().toString();

                            HostPojo hostPojo = new HostPojo(carBrandtxt,carModeltxt,Integer.valueOf(carYeartxt),carTransmissiontxt,carOdormetertxt,
                                    downloadUri,Integer.valueOf(price_txt),CarFeaturesActivity.description,Integer.valueOf(CarFeaturesActivity.person_count)
                                    ,CarFeaturesActivity.gps,CarFeaturesActivity.audio,CarFeaturesActivity.automatic,CarFeaturesActivity.bluetooth,CarFeaturesActivity.airbag,email
                                    ,5,"New Arrival"
                                    );

                            CollectionReference hostUpload = Host_firebaseStorage.collection("Cars");

                            hostUpload.add(hostPojo).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(PriceActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(PriceActivity.this, HomeActivity.class));
                                }
                            });


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(PriceActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }

            }
        });

    }
    public String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

}
