package com.example.carrental.initial.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.carrental.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DrivingLicence extends AppCompatActivity {
    Button next;
    TextInputEditText country, province, lisenceNumber, firstName, lastName, birthDate;
    TextInputLayout countryLayout, provinceLayout, lisenceNumberLayout, firstNameLayout, lastNameLayout, birthDateLayout;
    CheckBox checkBox;

    String namepattern = "[a-zA-Z]+";
    String Licenepattern = "[A-Z0-9]+";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driving_licence);

        next = findViewById(R.id.nextbuttonLicense);

        country = findViewById(R.id.countryLicense);
        province = findViewById(R.id.provinceLicense);
        lisenceNumber = findViewById(R.id.numberLicense);
        firstName = findViewById(R.id.firstnameLicense);
        lastName = findViewById(R.id.lastnameLicense);
        birthDate = findViewById(R.id.birthdateLicense);
        checkBox = findViewById(R.id.checkBoxSaveLicense);

        countryLayout = findViewById(R.id.countryInputLayout);
        provinceLayout = findViewById(R.id.provinceInputLayout);
        lisenceNumberLayout = findViewById(R.id.numberInputLayout);
        firstNameLayout = findViewById(R.id.firstnameInputLayout);
        lastNameLayout = findViewById(R.id.lastnameInputLayout);
        birthDateLayout = findViewById(R.id.birthdateInputLayout);

        country.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (country.getText().toString().matches(namepattern) && s.length() > 0) {
                    countryLayout.setError(null);
                } else {
                    countryLayout.setError("Invalid Country Name");
                }

            }
        });

        province.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (province.getText().toString().matches(namepattern) && s.length() > 0) {
                    provinceLayout.setError(null);
                } else {
                    provinceLayout.setError("Invalid province name");
                }
            }
        });

        lisenceNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (lisenceNumber.getText().toString().matches(Licenepattern) && s.length() > 0) {
                    lisenceNumberLayout.setError(null);
                } else {
                    lisenceNumberLayout.setError("Invalid Licence");
                }
            }
        });

        firstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (firstName.getText().toString().matches(namepattern) && s.length() > 0) {
                    firstNameLayout.setError(null);
                } else {
                    firstNameLayout.setError("Invalid name");
                }
            }
        });

        lastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (lastName.getText().toString().matches(namepattern) && s.length() > 0) {
                    lastNameLayout.setError(null);
                } else {
                    lastNameLayout.setError("Invalid last name");
                }
            }
        });

        birthDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });




        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("hello,sign in",MODE_PRIVATE);

        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();


            }

            private void updateLabel() {
                String myFormat = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                birthDate.setText(sdf.format(myCalendar.getTime()));
            }

        };

        birthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               DatePickerDialog datePickerDialog = new DatePickerDialog(DrivingLicence.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
               datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
               datePickerDialog.show();

            }
        });


        final String user = sharedPreferences.getString("email", "");




        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(country.getText().toString())){
                    countryLayout.setError("Enter Country Name Plz..");
                    countryLayout.requestFocus();
                }
                else if(TextUtils.isEmpty(province.getText().toString())){
                    provinceLayout.setError("Enter Province Name Plz..");
                    provinceLayout.requestFocus();
                }
                else if(TextUtils.isEmpty(lisenceNumber.getText().toString())){
                    lisenceNumberLayout.setError("Enter Lisence Number Plz..");
                    lisenceNumberLayout.requestFocus();
                }
                else if(TextUtils.isEmpty(firstName.getText().toString())){
                    firstName.setError("Enter First Name Plz..");
                    firstName.requestFocus();
                }
                else if(TextUtils.isEmpty(lastName.getText().toString())){
                    lastName.setError("Enter Last Name Plz..");
                    lastName.requestFocus();
                }
                else if(TextUtils.isEmpty(birthDate.getText().toString())){
                    birthDate.setError("Enter Birthdate Plz..");
                    birthDate.requestFocus();
                }
                else {
                    if (checkBox.isChecked()) {
                        final Map<String, Object> data = new HashMap<>();
                        data.put("country", country.getText().toString().trim());
                        data.put("province", province.getText().toString().trim());
                        data.put("licenseNumber", lisenceNumber.getText().toString().trim());
                        data.put("firstname", firstName.getText().toString().trim());
                        data.put("lastname", lastName.getText().toString().trim());
                        data.put("birthdate", birthDate.getText().toString().trim());
                        data.put("email", user);
                        final FirebaseFirestore db = FirebaseFirestore.getInstance();
                        db.collection("license").document().set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(DrivingLicence.this, "License Details Saved", Toast.LENGTH_LONG).show();

                            }
                        });

                    }
                    Intent intent1 = new Intent(getApplicationContext(),SummeryActivity.class);
                    startActivity(intent1);

                }




            }
        });

    }


    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("hello,sign in",MODE_PRIVATE);
        final String user = sharedPreferences.getString("email", "");
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("license").whereEqualTo("email",user).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.getResult() != null){
                    for(DocumentSnapshot snapshot:task.getResult()){
                        country.setText(String.valueOf(snapshot.get("country")));
                        province.setText(String.valueOf(snapshot.get("province")));
                        lisenceNumber.setText(String.valueOf(snapshot.get("licenseNumber")));
                        firstName.setText(String.valueOf(snapshot.get("firstname")));
                        lastName.setText(String.valueOf(snapshot.get("lastname")));
                        birthDate.setText(String.valueOf(snapshot.get("birthdate")));

                    }
                }


            }
        });

    }


}