package com.example.donationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    private ImageView image;
    private TextView cpRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getSupportActionBar().hide();

        Animation simple = AnimationUtils.loadAnimation(this , R.anim.sample_anim);
        Animation blink = AnimationUtils.loadAnimation(this , R.anim.zoomout);
        blink.setDuration(3000);
        simple.setDuration(3000);

        image = findViewById(R.id.dn);
        cpRight = findViewById(R.id.cr2);

        cpRight.setAnimation(blink);
        image.setAnimation(simple);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(SplashScreen.this, Login.class);
                startActivity(intent);
                finish();

            }
        },4000 );
    }
}
