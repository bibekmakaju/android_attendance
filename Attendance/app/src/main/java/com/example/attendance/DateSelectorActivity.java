package com.example.attendance;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateSelectorActivity extends AppCompatActivity {
    TextView title,date;
    DatePickerDialog picker;
    Button confirm;
    TextView dte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_selector);
        final Intent intent = getIntent();
        title=findViewById(R.id.title1);
        date=findViewById(R.id.datepick);
        confirm=findViewById(R.id.gotostudent);
        dte=findViewById(R.id.editdte);

        final String title2 = intent.getStringExtra("title");
        final String courseid = intent.getStringExtra("courseid");
        final String batch = intent.getStringExtra("batch");
        DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker);
        datePicker.setMaxDate(System.currentTimeMillis());
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth() + 1;
        int year = datePicker.getYear();
        String c = year + "/" + (month) + "/" + day;
        dte.setText(c);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {

            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int dayOfMonth) {
                Log.d("Date", "Year=" + year + " Month=" + (month + 1) + " day=" + dayOfMonth);
                String c = year + "/" + (month+1) + "/" + dayOfMonth;
                dte.setText(c);

            }
        });


//        title.setText(title2);
        String date1= new SimpleDateFormat("yyyy/M/dd", Locale.getDefault()).format(new Date());
        date.setText(date1);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();

                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                picker = new DatePickerDialog(DateSelectorActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                String c = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;

                                date.setText(c);
                                String date1 = date.getText().toString();

                            }
                        }, year, month, day);
                picker.getDatePicker().setMaxDate(System.currentTimeMillis());
                picker.show();
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date1 = dte.getText().toString();
                Intent intent=new Intent(DateSelectorActivity.this,Student_attend_activity.class);
                intent.putExtra("title",title2);
                intent.putExtra("courseid",courseid);
                intent.putExtra("batch",batch);
                intent.putExtra("date",date1);
                startActivity(intent);
            }
        });

    }
}
