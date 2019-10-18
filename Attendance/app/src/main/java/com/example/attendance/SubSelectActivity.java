package com.example.attendance;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.ContactsContract;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;


import com.android.volley.RequestQueue;

import com.android.volley.toolbox.Volley;


import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

public class SubSelectActivity extends AppCompatActivity implements  SubjectAdapter.OnItemClickListener{

    private RecyclerView recyclerView;
    private SubjectAdapter studentAdapter;
    private ArrayList<SubjectModel> arrayList;
    private RequestQueue requestQueue;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_select);


        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        arrayList=new ArrayList<>();
        requestQueue= Volley.newRequestQueue(this);
        String lecturer_id=getIntent().getStringExtra("lecturer_id");
        dbHelper=new DBHelper(getApplicationContext());
        parseJSON(lecturer_id);
        boolean c=haveNetworkConnection();
        if (c==true){
//            dbHelper.registerUser();
//            Toast.makeText(SubSelectActivity.this,"sucessfully update remote db",Toast.LENGTH_SHORT).show();
        }



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


    private void parseJSON( String lecturer_id) {

        DBHelper dbHelper=new DBHelper(getApplicationContext());
        arrayList=dbHelper.getAllSubject();


        studentAdapter = new SubjectAdapter(SubSelectActivity.this, arrayList);
        recyclerView.setAdapter(studentAdapter);
        studentAdapter.setOnItemClickListner(SubSelectActivity.this);
    }


    @Override
    public void onItemClick(int position) {


        Intent intent=new Intent(this,DateSelectorActivity.class);
        SubjectModel onItemClick=arrayList.get(position);
        intent.putExtra("title",onItemClick.getTitle());
        intent.putExtra("courseid",onItemClick.getCourse_id());
        intent.putExtra("batch",onItemClick.getBatch_ad());
//        addNotification();
        startActivity(intent);
    }



}
