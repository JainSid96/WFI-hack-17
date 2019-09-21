package com.example.dcube.foodo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.WindowManager;

public class Splash_Activity extends AppCompatActivity {

    public int Time_DelayForRequest = 1000;
    public int splashScreentime = 3 * Time_DelayForRequest;
    Thread background;

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_);

        background = new Thread() {
            public void run() {

                try {
                    // Thread will sleep for 5 seconds
                    sleep(splashScreentime);

                    Intent myIntent = new Intent(Splash_Activity.this,Login_Activity.class);
                    startActivity(myIntent);

                    finish();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        background.start();
    }
}
