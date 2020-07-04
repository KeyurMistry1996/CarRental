package com.example.carrental.initial.profile;

import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carrental.R;
import com.example.carrental.initial.MainActivity;
import com.example.carrental.initial.UserPojo;
import com.example.carrental.initial.home.HomeActivity;
import com.example.carrental.initial.host.ImageUploadActivity;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 234;

    private Uri filePath;

    TextView viewprofile, logout, changepassword,contactus,about;
    Button profilepicSave;

    CircularImageView setPhoto;
    ImageView selectPhoto;

    String downloadprofile;
    String userrefID;
    EditText newpassword, retype;

    StorageReference userprofile;
    private FirebaseFirestore profile;

    String email;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);


        setPhoto = view.findViewById(R.id.set_profile);
        selectPhoto = view.findViewById(R.id.select_profile);
        profilepicSave = view.findViewById(R.id.profilepicSave);
        viewprofile = view.findViewById(R.id.viewprofile);
        logout = view.findViewById(R.id.logout);
        changepassword = view.findViewById(R.id.changepassword);
        contactus = view.findViewById(R.id.contactus);
        about = view.findViewById(R.id.about_us);

        profile = FirebaseFirestore.getInstance();


        userprofile = FirebaseStorage.getInstance().getReference("User profile");

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(MainActivity.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        email = sharedPreferences.getString(MainActivity.Email_shared, MainActivity.SHARED_PREF_NAME);

        profile.collection("user")
                .whereEqualTo("email", email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                userrefID = document.getId();
                                Picasso.with(getActivity())
                                        .load(String.valueOf(document.get("profileurl")))
                                        .placeholder(R.drawable.ic_person_black_24dp)
                                        .into(setPhoto);
                            }
                        }
                    }
                });

        contactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                emailIntent.setType("message/rfc822");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"projectcarrental82@gmail.com"});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Contact Us");
                startActivity(emailIntent);
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
startActivity(new Intent(getActivity(),AboutusActivity.class));
            }
        });

        changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater layoutInflater = getLayoutInflater();
                View dialogView = layoutInflater.inflate(R.layout.changepasswordlayout, null);
                newpassword = dialogView.findViewById(R.id.newpassword);
                retype = dialogView.findViewById(R.id.retype);
                builder.setTitle("Change Your Password");
                builder.setView(dialogView);
                builder.setPositiveButton("Change Password", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String newp = newpassword.getText().toString().trim();
                        String rep = retype.getText().toString().trim();

                        if (newp.isEmpty()) {
                            newpassword.setError("Plzz Enter Passowrd");
                        }
                        if (rep.isEmpty()) {
                            retype.setError("Plzz Retype new Password");
                        } else {

                            if (newp.equals(rep)) {
                                final DocumentReference documentReference = FirebaseFirestore.getInstance()
                                        .collection("user")
                                        .document(userrefID);
                                Map<String, Object> changepassword = new HashMap<>();
                                changepassword.put("password", newp);
                                documentReference.update(changepassword)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(getActivity(), "Password changed", Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getActivity(), "Failed to change password", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            } else {

                                Toast.makeText(getActivity(), "Password Didnt Match", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert1 = builder.create();
                alert1.show();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setTitle("Log Out");
                builder.setMessage("Are you sure you want to LogOut?");
                builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(MainActivity.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.commit();

                        Intent intentH = new Intent(getActivity(), MainActivity.class);
                        startActivity(intentH);
                        getActivity().finish();

                        getActivity().finish();

                    }
                });

                builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });


        viewprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(), viewProfileActivity.class));

            }
        });

        setPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profilepicSave.setVisibility(View.VISIBLE);
                opengalary();
            }
        });

        profilepicSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profilepicSave.setVisibility(View.GONE);
                final StorageReference storageReference = userprofile.child(System.currentTimeMillis() + ".jpg");

                storageReference.putFile(filePath).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
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
                        if (task.isSuccessful()) {
                            downloadprofile = task.getResult().toString();
                            final DocumentReference documentReference = FirebaseFirestore.getInstance()
                                    .collection("user")
                                    .document(userrefID);
//                            UserPojo updateProfile = new UserPojo(downloadprofile);
                            Map<String, Object> updateProfile = new HashMap<>();
                            updateProfile.put("profileurl", downloadprofile);
                            documentReference.update(updateProfile)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(getActivity(), "Profile Pic Updated", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getActivity(), "Profile Pic Update Fail", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    }
                });
            }
        });

        return view;
    }

    private void opengalary() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                setPhoto.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getFileExtension(Uri uri) {
        ContentResolver cR = Objects.requireNonNull(this.getActivity()).getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
}
