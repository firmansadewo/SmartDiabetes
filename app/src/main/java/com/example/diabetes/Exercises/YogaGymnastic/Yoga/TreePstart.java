package com.example.diabetes.Exercises.YogaGymnastic.Yoga;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.diabetes.Exercises.Cycling;
import com.example.diabetes.R;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class TreePstart extends Fragment  {

    public static TreePstart newInstance() {
        TreePstart fragment = new TreePstart();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    private static final long START_TIME_IN_MILLIS = 30000;
    private ProgressBar progressBarCircle;
    private TextView mTextViewCountDown;
    private ImageView mButtonStartStop;
    private ImageView mButtonReset;
    private TextView textfinish;
    private CountDownTimer mCountDownTimer;

    private boolean mTimerRunning;

    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tree_pstart, container, false);
        progressBarCircle = (ProgressBar) v.findViewById(R.id.progressBarCircle);
        mTextViewCountDown = v.findViewById(R.id.textViewTime);
        textfinish = (TextView) v.findViewById(R.id.textfinish);
        mButtonStartStop = v.findViewById(R.id.imageViewStartStop);
        mButtonReset = v.findViewById(R.id.imageViewReset);

        mButtonStartStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTimerRunning) {
                    stopTimer();
                } else {
                    startTimer();
                }
            }
        });

        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });

        updateCountDownText();

        return v;
    }

    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                setProgressBarValues();
                mTextViewCountDown.setText(hmsTimeFormatter(millisUntilFinished));
                progressBarCircle.setProgress((int) (millisUntilFinished / 1000));


                //updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                mButtonStartStop.setTag("Start");
                mButtonStartStop.setVisibility(View.INVISIBLE);
                mButtonReset.setVisibility(View.VISIBLE);
                textfinish.setVisibility(View.VISIBLE);
            }
        }.start();

        mTimerRunning = true;
        mButtonStartStop.setTag("stop");
        mButtonStartStop.setVisibility(View.VISIBLE);
        // changing play icon to pause icon
        mButtonStartStop.setImageResource(R.drawable.icon_stop);
        mButtonReset.setVisibility(View.INVISIBLE);
        textfinish.setVisibility(View.INVISIBLE);
    }

    private void stopTimer() {
        mCountDownTimer.cancel();
        updateCountDownText();
        setProgressBarValues();
        mTimerRunning = false;
        mButtonReset.setVisibility(View.INVISIBLE);
        mButtonStartStop.setVisibility(View.VISIBLE);
        mButtonStartStop.setImageResource(R.drawable.icon_start);
        textfinish.setVisibility(View.INVISIBLE);
    }

    private void resetTimer() {
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
        setProgressBarValues();
        mButtonReset.setVisibility(View.INVISIBLE);
        mButtonStartStop.setVisibility(View.VISIBLE);
        mButtonStartStop.setImageResource(R.drawable.icon_start);
        textfinish.setVisibility(View.INVISIBLE);
    }

    private void setProgressBarValues() {

        progressBarCircle.setMax((int) mTimeLeftInMillis / 1000);
        progressBarCircle.setProgress((int) mTimeLeftInMillis / 1000);
    }

    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        mTextViewCountDown.setText(timeLeftFormatted);
    }

    private String hmsTimeFormatter(long milliSeconds) {

        String hms = String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(milliSeconds),
                TimeUnit.MILLISECONDS.toSeconds(milliSeconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliSeconds)));

        return hms;


    }
}