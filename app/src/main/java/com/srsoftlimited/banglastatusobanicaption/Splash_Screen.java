package com.srsoftlimited.banglastatusobanicaption;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.ads.MobileAds;
import com.srsoftlimited.banglastatusobanicaption.ads.OpenAds;

public class Splash_Screen extends AppCompatActivity {

    TextView textView1, textView2;
    LottieAnimationView progressbar;
    CardView imagesplash;
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        progressbar = findViewById(R.id.progressBar);
        imagesplash = findViewById(R.id.imagesplash);

        animation = AnimationUtils.loadAnimation(Splash_Screen.this, R.anim.zoom_in);


        textView1.startAnimation(animation);
        textView2.startAnimation(animation);
        progressbar.startAnimation(animation);
        imagesplash.startAnimation(animation);


        MobileAds.initialize(this);
        Application application = getApplication();
        ((OpenAds) application).loadAd(this);

        createTimer();
    }

    private void createTimer() {
        CountDownTimer countDownTimer = new CountDownTimer(6000, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                Application application = getApplication();
                ((OpenAds) application).showAdIfAvailable(Splash_Screen.this, () -> {
                    Intent intent = new Intent(Splash_Screen.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                });
            }
        };
        countDownTimer.start();
    }
}