package com.example.diabetes.Exercises;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.diabetes.R;

public class Disclaimer extends AppCompatActivity {
    TextView textngerti;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.disclaimer);
        textngerti = (TextView) findViewById(R.id.textngerti);


        textngerti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openExercise();
            }

            private void openExercise() {
                Intent intent = new Intent(Disclaimer. this, Exercise.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.appearalt, R.anim.appearalt);

            }

        });
    }
}
