package com.example.attendance;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class Student_attend_activity extends AppCompatActivity {

    TextView datepickertxt,titleplaceholder,teachername;

    DatePickerDialog picker;
    private StudentAdapter newstudentAdapter, madapter;
    RecyclerView studentrecycleview;
    private ArrayList<StudentModel> arrayList;
    DBHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_attend_activity);

        titleplaceholder=findViewById(R.id.coursetitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        teachername=findViewById(R.id.teachername);
        studentrecycleview = findViewById(R.id.studentrecycleview);
        studentrecycleview.setHasFixedSize(true);
        studentrecycleview.setLayoutManager(new LinearLayoutManager(this));



        SharedPreferences sp = getSharedPreferences("userid" , Context.MODE_PRIVATE);

        String sc  = sp.getString("fullname","null");
        if (!sc.equals("null")){
            teachername.setText(sc);
        }

        datepickertxt = findViewById(R.id.datepicker);


        dbHelper=new DBHelper(this);
        Intent intent = getIntent();

        String title = intent.getStringExtra("title");
        final String courseid = intent.getStringExtra("courseid");
        final String batch = intent.getStringExtra("batch");
        final String date = intent.getStringExtra("date");
        titleplaceholder.setText("Subject: "+title);
//        String date = new SimpleDateFormat("yyyy/M/dd", Locale.getDefault()).format(new Date());
        datepickertxt.setText(date);
        setdate(courseid, batch, date);



        datepickertxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();

                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                picker = new DatePickerDialog(Student_attend_activity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override

                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                String c = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;

                                datepickertxt.setText(c);
                                String date = datepickertxt.getText().toString();
                                setdate(courseid, batch, date);
                            }
                        }, year, month, day);
                picker.getDatePicker().setMaxDate(System.currentTimeMillis());
                picker.show();
            }
        });


    }

    void setdate(String courseid, String batch, String date) {
        Log.d("date", date);
        DBHelper dbHelper = new DBHelper(getApplicationContext());
        arrayList = dbHelper.getSubStudent(courseid, batch, date);

        newstudentAdapter = new StudentAdapter(Student_attend_activity.this, arrayList);
        studentrecycleview.setAdapter(newstudentAdapter);
    }
    public boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;
        boolean haveInternet=false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI")) {
                if (ni.isConnected()) {
                    haveConnectedWifi = true;

                }
            }
            if (ni.getTypeName().equalsIgnoreCase("MOBILE")) {
                if (ni.isConnected()) {
                    haveConnectedMobile = true;
                }
            }
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sync, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.sync) {

            dbHelper.registerUser();

        }


        return super.onOptionsItemSelected(item);
    }
}