package com.example.attendance;

import android.app.ActionBar;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.icu.util.LocaleData;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.nfc.tech.NfcA;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

public class NavigationActivity extends AppCompatActivity {
    CardView cardView,cardViewreport;
    TextView textView;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        final OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(MyWorker.class).build();
        final String lecturer_id=getIntent().getStringExtra("lecturer_id");
        cardViewreport=findViewById(R.id.carreport);
        cardView=findViewById(R.id.cardcall);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NavigationActivity.this, SubSelectActivity.class);
                intent.putExtra("lecturer_id",lecturer_id);
                startActivity(intent);
            }
        });
        final boolean c =haveNetworkConnection();
        cardViewreport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("clicked","clicked");
                displayNotification("Sync","Syncing");

            }
        });
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
    private void displayNotification(String title, String task) {
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intent = new Intent(this, DateSelectorActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, NavigationActivity.class), 0);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("sync", "sync", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext(), "sync")
                .setContentTitle(title)
                .setContentText(task)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.attendanceimg)
                .setAutoCancel(true);;

        notificationManager.notify(1, notification.build());
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.logout) {

            SharedPreferences sharedpreferences = getSharedPreferences("userid", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.clear();
            editor.apply();
            Intent intent = new Intent(NavigationActivity.this,MainActivity.class);
            startActivity(intent);


        }


        return super.onOptionsItemSelected(item);
    }

}
