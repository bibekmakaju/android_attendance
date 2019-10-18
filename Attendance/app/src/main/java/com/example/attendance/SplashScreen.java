package com.example.attendance;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import static java.lang.Thread.sleep;

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 6000;
    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);
        txt=findViewById(R.id.txtview);
        LinearLayout constraintLayout = findViewById(R.id.layout);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sp = getSharedPreferences("userid" , Context.MODE_PRIVATE);

                String sc  = sp.getString("lecturer_id","null");
                if (sc.equals("loged")){
                    Intent intent = new Intent(SplashScreen.this,NavigationActivity.class);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(SplashScreen.this,MainActivity.class);
                    startActivity(intent);

                }


                finish();
            }
        },2500);

    }
}


