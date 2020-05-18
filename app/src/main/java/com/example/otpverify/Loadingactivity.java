package com.example.otpverify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

public class Loadingactivity extends AppCompatActivity {

    private Animation top,bottom;
    private ImageView imageView;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loadingactivity);
        imageView=findViewById(R.id.imgview);
        textView=findViewById(R.id.explore);
        top= AnimationUtils.loadAnimation(Loadingactivity.this,R.anim.top_anim);
        bottom=AnimationUtils.loadAnimation(Loadingactivity.this,R.anim.bottom_anim);
        imageView.setAnimation(top);
        textView.setAnimation(bottom);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeintent = new Intent(Loadingactivity.this, SplashScreen.class);
                startActivity(homeintent);
                finish();
            }
        },3000);


    }

    }

