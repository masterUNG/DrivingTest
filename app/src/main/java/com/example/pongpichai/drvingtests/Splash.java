package com.example.pongpichai.drvingtests;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import java.util.logging.Handler;


public class Splash extends Activity {
    Handler handler;
    Runnable runnable;
    long delay_time;
    long time = 3000L;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splashscreen);

        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) { }

                Intent intent = new Intent(Splash.this, CMenu.class);
                startActivity(intent);
                finish();
            }
        }).start();
    }
}
