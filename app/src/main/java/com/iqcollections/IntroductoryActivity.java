package com.iqcollections;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class IntroductoryActivity extends AppCompatActivity {

    ImageView logo, splashImg;
    TextView app_name;
    //LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introductory);

        logo = findViewById(R.id.logo);
        app_name = findViewById(R.id.app_name);
        //splashImg = findViewById(R.id.splashImg);
        //lottieAnimationView = findViewById(R.id.lottie)

        splashImg.animate().translationY(-1600).setDuration(1000).setStartDelay(4000);
        logo.animate().translationY(1400).setDuration(1000).setStartDelay(4000);
        app_name.animate().translationY(1400).setDuration(1000).setStartDelay(4000);
        //lottieAnimationView.animate().translationY(1400).setDuration(1000).setStartDelay(4000);


    }
}