package com.example.diabetes;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;


public class Splash extends AppCompatActivity {
    ImageView circle;
    ImageView phone;
    ImageView blood;
    Animation leftoright,righttoleft,appear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        circle       = findViewById(R.id.circle);
        phone      = findViewById(R.id.phone);
        blood       = findViewById(R.id.blood);

        leftoright  = AnimationUtils.loadAnimation(this,R.anim.lefttoright);
        righttoleft = AnimationUtils.loadAnimation(this,R.anim.righttoleft);

        circle.setAnimation(appear);
        blood.setAnimation(righttoleft);
        phone.setAnimation(leftoright);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent a = new Intent(Splash.this, com.example.diabetes.Login.Login.class);
                startActivity(a);
                finish();
            }
        },1000);
    }
}
