package com.example.saurabh.bmicalci_saurabh;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity1 extends AppCompatActivity {
    ImageView im1 ;
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash1);

        int orientation= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        setRequestedOrientation(orientation);
        im1= (ImageView) findViewById(R.id.im1);
        animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.custom);
        im1.startAnimation(animation);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Intent i =new Intent(SplashActivity1.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        }).start();
    }
}
