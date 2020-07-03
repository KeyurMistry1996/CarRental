package com.example.carrental.initial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carrental.R;
import com.example.carrental.admin.AdminDashboard;
import com.example.carrental.initial.home.HomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity {

    TextInputLayout emaillayout, passwordlayout, namelayout, emailsignlayout, numberlayout, passwordsignlayout, adminemaillayout, adminpasswordlayout;
    EditText loginemail, loginpassword, namesign, emailsign, numbersign, passwordsign, adminemail, adminpassword;
    Button btnuser, btnadmin, loginbtn, signinbtn, loginadminbtn;
    LinearLayout userloginlayout, usersigninlayout, selectuser, adminLayout;
    TextView createone, logintxtview;
    private FirebaseAuth mAuth;

    String namepattern = "[a-zA-Z]+";
    String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    String txtloginemail, txtloginpassword, txtsignname, txtsignemail, txtsignnumber, txtsignpassword, txtadminemail, txtadminpassword;

    private FirebaseFirestore db;

    public static boolean loggedIn = false;
    public static final String SHARED_PREF_NAME = "hello,sign in";
    public static final String loginyes_no = "userlogin";
    public static final String User_shared = "username";
    public static final String Email_shared = "email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();


        //Textview Ids

        createone = findViewById(R.id.createone);
        logintxtview = findViewById(R.id.logintxtview);

        //Button Ids

        btnadmin = findViewById(R.id.adminbtn);
        btnuser = findViewById(R.id.userbtn);
        loginbtn = findViewById(R.id.loginbtn);
        signinbtn = findViewById(R.id.signinbtn);
        loginadminbtn = findViewById(R.id.loginadminbtn);

        //Linear Layout Ids

        userloginlayout = findViewById(R.id.userloginlayout);
        usersigninlayout = findViewById(R.id.usersigninlayout);
        selectuser = findViewById(R.id.selectuser);
        adminLayout = findViewById(R.id.adminLayout);

        //TextInputLayout Ids

        emaillayout = findViewById(R.id.emaillayout);
        passwordlayout = findViewById(R.id.passwordlayout);
        namelayout = findViewById(R.id.nameLayout);
        emailsignlayout = findViewById(R.id.emailSignLayout);
        numberlayout = findViewById(R.id.numberLayout);
        passwordsignlayout = findViewById(R.id.passwordSignLayout);
        adminemaillayout = findViewById(R.id.adminemaillayout);
        adminpasswordlayout = findViewById(R.id.adminpasswordlayout);


        //EditText Ids

        //Login

        loginemail = findViewById(R.id.loginemail);
        loginpassword = findViewById(R.id.loginpassword);

        //SignIn

        emailsign = findViewById(R.id.emailSign);
        namesign = findViewById(R.id.nameSign);
        numbersign = findViewById(R.id.numberSign);
        passwordsign = findViewById(R.id.passwordSign);

        //Admin

        adminemail = findViewById(R.id.adminemail);
        adminpassword = findViewById(R.id.adminpassword);


        loginemail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                emaillayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        loginpassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                passwordlayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        emailsign.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                emailsignlayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (emailsign.getText().toString().matches(emailpattern) && s.length() > 0) {
                    emailsignlayout.setError(null);
                } else {
                    emailsignlayout.setError("Invalid Email");
                }

            }
        });

        numbersign.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                namelayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        passwordsign.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                passwordlayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        namesign.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                namelayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (namesign.getText().toString().matches(namepattern) && s.length() > 0) {
                    namelayout.setError(null);
                } else {
                    namelayout.setError("Invalid Name");
                }

            }
        });
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtloginemail = loginemail.getText().toString().trim();
                txtloginpassword = loginpassword.getText().toString().trim();

                if (txtloginemail.isEmpty()) {
                    emaillayout.setError("Enter Email Plz..");
                    emaillayout.requestFocus();
                } else if (txtloginpassword.isEmpty()) {
                    passwordlayout.setError("Enter Password Plz..");
                } else {
                    loginuser();
                }
            }
        });
        signinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtsignname = namesign.getText().toString().trim();
                txtsignemail = emailsign.getText().toString().trim();
                txtsignnumber = numbersign.getText().toString().trim();
                txtsignpassword = passwordsign.getText().toString().trim();

                if (txtsignname.isEmpty()) {
                    namelayout.setError("Enter Name");
                    namelayout.requestFocus();
                } else if (txtsignemail.isEmpty()) {
                    emailsignlayout.setError("Enter email");
                    emailsignlayout.requestFocus();
                } else if (txtsignnumber.isEmpty()) {
                    numberlayout.setError("Enter Number");
                    numberlayout.requestFocus();
                } else if (txtsignpassword.isEmpty()) {
                    passwordsignlayout.setError("Enter Passoword");
                    passwordsignlayout.requestFocus();
                } else if (!(txtsignnumber.length() == 10)) {
                    numberlayout.setError("Enter valid number");
                } else {
                    Doseuserexist();
                }
            }
        });
        loginadminbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtadminemail = adminemail.getText().toString().trim();
                txtadminpassword = adminpassword.getText().toString().trim();

                if (txtadminemail.isEmpty()) {
                    adminemaillayout.setError("Enter Email Plz..");
                    adminemaillayout.requestFocus();
                } else if (txtadminpassword.isEmpty()) {
                    adminpasswordlayout.setError("Enter Password plz..");
                    adminpasswordlayout.requestFocus();
                } else {
                    loginadmin();
                }
            }
        });


        btnadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectuser.setVisibility(View.GONE);
                adminLayout.setVisibility(View.VISIBLE);
            }
        });

        btnuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectuser.setVisibility(View.GONE);
                userloginlayout.setVisibility(View.VISIBLE);
            }
        });

        createone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userloginlayout.setVisibility(View.GONE);
                usersigninlayout.setVisibility(View.VISIBLE);
            }
        });

        logintxtview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usersigninlayout.setVisibility(View.GONE);
                userloginlayout.setVisibility(View.VISIBLE);
            }
        });
    }

    public void loginadmin() {
        mAuth.signInWithEmailAndPassword(txtadminemail, txtadminpassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(MainActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), AdminDashboard.class));

                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    public void loginuser() {
        db.collection("user")
                .whereEqualTo("email", txtloginemail)
                .whereEqualTo("password", txtloginpassword)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            Toast.makeText(MainActivity.this, "Login Success", Toast.LENGTH_LONG).show();
                            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();


                            editor.putBoolean(loginyes_no, true);
                            editor.putString(Email_shared, loginemail.getText().toString());

                            editor.commit();
                            startActivity(new Intent(MainActivity.this,HomeActivity.class));
                        } else {
                            Toast.makeText(MainActivity.this, "Email or Password Not correct", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void Doseuserexist() {
        db.collection("user")
                .whereEqualTo("email", txtsignemail)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            Toast.makeText(MainActivity.this, "User Already Exist Plz try to login", Toast.LENGTH_LONG).show();
                        } else {
                            Adduser();
                        }
                    }
                });
    }

    public void Adduser() {
        CollectionReference dbuser = db.collection("user");

        UserPojo userPojo = new UserPojo(txtsignname, txtsignemail, txtsignpassword, txtsignnumber);
        dbuser.add(userPojo).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();


                editor.putBoolean(loginyes_no, true);
                editor.putString(Email_shared, emailsign.getText().toString());

                editor.commit();
                startActivity(new Intent(MainActivity.this,HomeActivity.class));

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Error Adding User", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        loggedIn = sharedPreferences.getBoolean(loginyes_no, false);
        if (loggedIn) {
            Intent i = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(i);
            MainActivity.this.finish();
        }
    }
}