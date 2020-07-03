package com.example.carrental.initial.home;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.carrental.R;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

public class ScheduleActivity extends AppCompatActivity {
    String id;
    Spinner pickUp,dropOff;
    CheckBox checkBox;
    TextView pickUpDate,dropOffDate,pickUpTime,dropOffTime;
    Button book;
    String pickUpLocation,dropOffLocation;
    int pickUpDay;
    String picUpMonth;
    String pickWeekDay,dropWeekDay;
    int pickUpHour,pickUpMinut,dropOffHours,dropOffMinut;
    int dropOffUpDay;
    String pickTime,dropTime,pickDate,dropDate;
    String dropOffUpMonth;
    ImageView back;
    TextView monthPick,dropMonth;
    LocalDate dateBefore,dateAfter;
    TextView dayOfWeek,dayOfWeekDrop;
    public static final String[] MONTHS = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        pickUp = findViewById(R.id.spinnerPickUp);
        dropOff = findViewById(R.id.spinnerDropOff);
        checkBox = findViewById(R.id.checkBoxSameLocation);
        pickUpDate = findViewById(R.id.pickUpDateDate);
        dropOffDate = findViewById(R.id.dropOffDateDate);
        pickUpTime = findViewById(R.id.pickUpTime);
        dropOffTime = findViewById(R.id.dropOffTime);
        book = findViewById(R.id.buttonBookSchedule);
        dayOfWeek = findViewById(R.id.pickUpDateDay);
         dayOfWeekDrop = findViewById(R.id.dropOffWeekDay);
        monthPick = findViewById(R.id.pickUpMonth);
         dropMonth = findViewById(R.id.dropOffDateMonth);


        pickUp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pickUpLocation = (String) parent.getItemAtPosition(position);
                Log.i("pickUp",pickUpLocation);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        dropOff.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dropOffLocation = (String) parent.getItemAtPosition(position);
                Log.i("dropOff",dropOffLocation);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    dropOff.setEnabled(false);
                    dropOff.setClickable(false);
                    dropOffLocation = pickUpLocation;
                    Toast.makeText(ScheduleActivity.this, dropOffLocation, Toast.LENGTH_SHORT).show();
                }
                else {
                    dropOff.setClickable(true);
                    dropOff.setEnabled(true);
                }
            }
        });




        final Calendar myCalendar = Calendar.getInstance();
        pickUpDay = myCalendar.get(Calendar.DAY_OF_MONTH);
        dropOffUpDay = myCalendar.get(Calendar.DAY_OF_MONTH);
        pickUpDate.setText(String.valueOf(pickUpDay));
        dropOffDate.setText(String.valueOf(dropOffUpDay));

        picUpMonth = MONTHS[myCalendar.get(Calendar.MONTH)];
        dropOffUpMonth = MONTHS[myCalendar.get(Calendar.MONTH)];
        monthPick.setText(picUpMonth);
        dropMonth.setText(dropOffUpMonth);

        int year  =myCalendar.get(Calendar.YEAR);
        Date d_name = new Date(year,myCalendar.get(Calendar.MONTH),pickUpDay);
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        String dayOfTheWeek = sdf.format(d_name);
        pickWeekDay = dayOfTheWeek.substring(0,3);
        dropWeekDay = pickWeekDay;
        dayOfWeekDrop.setText(pickWeekDay);
        dayOfWeek.setText(dropWeekDay);

        pickUpHour = myCalendar.get(Calendar.HOUR_OF_DAY);
        pickUpMinut = myCalendar.get(Calendar.MINUTE);
        pickTime = pickUpHour+":"+pickUpMinut;
        dropTime = pickTime;
        pickUpTime.setText((pickTime));
        dropOffTime.setText(dropTime);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        pickDate = pickWeekDay+","+pickUpDay+" "+picUpMonth+" "+year;
        dropDate = pickDate;

        dateBefore = LocalDate.parse(myCalendar.get(Calendar.MONTH)+"/"+pickUpDay+"/"+year,formatter);
        dateAfter = dateBefore;





        pickUpDate.setOnClickListener(new View.OnClickListener() {
            DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    pickUpDay = dayOfMonth;
                    picUpMonth = MONTHS[monthOfYear];

                    pickUpDate.setText(String.valueOf(pickUpDay));

                    SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
                    Date d_name = new Date(year,monthOfYear,dayOfMonth-1);
                    String dayOfTheWeek = sdf.format(d_name);
                    pickWeekDay = dayOfTheWeek.substring(0,3);
                    dayOfWeek.setText(pickWeekDay);
                    monthPick.setText(picUpMonth);

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
                    dateBefore = LocalDate.parse(monthOfYear+"/"+dayOfMonth+"/"+year,formatter);

                    pickDate = pickWeekDay+","+pickUpDay+" "+picUpMonth+" "+year;
                    Toast.makeText(ScheduleActivity.this, pickDate, Toast.LENGTH_SHORT).show();

                }
            };
            @Override
            public void onClick(View v) {

                new DatePickerDialog(ScheduleActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        dropOffDate.setOnClickListener(new View.OnClickListener() {
            DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    dropOffUpDay = dayOfMonth;
                    dropOffUpMonth = MONTHS[monthOfYear];

                    dropOffDate.setText(String.valueOf(dayOfMonth));

                    SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
                    Date d_name = new Date(year,monthOfYear,dayOfMonth-1);
                    String dayOfTheWeek = sdf.format(d_name);

                    dropWeekDay = dayOfTheWeek.substring(0,3);
                    dayOfWeekDrop.setText(dropWeekDay);
                    dropMonth.setText(dropOffUpMonth);
                    dropDate = dropWeekDay+","+dropOffUpDay+" "+dropOffUpMonth+" "+year;
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
                    dateAfter = LocalDate.parse(monthOfYear+"/"+dayOfMonth+"/"+year,formatter);



                }
            };
            @Override
            public void onClick(View v) {

                new DatePickerDialog(ScheduleActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        pickUpTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog mTimePicker;
                 mTimePicker = new TimePickerDialog(ScheduleActivity.this, new TimePickerDialog.OnTimeSetListener() {

                     @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                         pickUpHour = selectedHour;
                         pickUpMinut = selectedMinute;

                         pickTime = pickUpHour+":"+pickUpMinut;
                        pickUpTime.setText(pickTime);
                    }
                },pickUpHour,pickUpMinut,true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

        dropOffTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog mTimePicker;
                 mTimePicker = new TimePickerDialog(ScheduleActivity.this, new TimePickerDialog.OnTimeSetListener() {

                     @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                         dropOffHours = selectedHour;
                         dropOffMinut = selectedMinute;

                         dropTime = dropOffHours+":"+dropOffMinut;

                        dropOffTime.setText(dropTime);
                    }
                },dropOffHours,dropOffMinut,true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });



        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long noOfDaysBetween = ChronoUnit.DAYS.between(dateBefore, dateAfter);
                if(!(noOfDaysBetween <=0)){
                    SharedPreferences sharedPreferences =getApplicationContext().getSharedPreferences("hello,sign in", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("pickUpTime",pickTime);
                    editor.putString("dropOffTime",dropTime);
                    editor.putString("pickDate",pickDate);
                    editor.putString("dropDate",dropDate);
                    editor.putString("pickUpLocation",pickUpLocation);
                    editor.putString("dropoffLocation",dropOffLocation);
                    editor.putLong("days",noOfDaysBetween);
                    editor.commit();


                    Intent intent = new Intent(getApplicationContext(),DrivingLicence.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(ScheduleActivity.this, "Please check the dates", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}