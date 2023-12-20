package com.groovylamp;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

public class Timer extends AppCompatActivity {
    private static final long START_TIME_IN_MILLIS = 0;

    private TextView mTextViewCountDown;
    private Button mButtonStartPause;
    private Button mButtonReset;
    private Button mButtonAddMinute;
    private Button mButtonSubtractMinute;
    private Button mButtonAddHour;
    private Button mButtonSubtractHour;

    private CountDownTimer mCountDownTimer;

    private boolean mTimerRunning;

    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        mTextViewCountDown = findViewById(R.id.text_view_countdown);
        mButtonStartPause = findViewById(R.id.button_start_pause);
        mButtonReset = findViewById(R.id.button_reset);
        mButtonAddMinute = findViewById(R.id.button_add_minute);
        mButtonSubtractMinute = findViewById(R.id.button_subtract_minute);
        mButtonAddHour = findViewById(R.id.button_add_hour);
        mButtonSubtractHour = findViewById(R.id.button_subtract_hour);

        mButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTimerRunning) {
                    pauseTimer();
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

        mButtonAddMinute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMinute();
            }
        });

        mButtonSubtractMinute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subtractMinute();
            }
        });

        mButtonAddHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addHour();
            }
        });

        mButtonSubtractHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subtractHour();
            }
        });

        updateCountDownText();
    }

    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                mButtonStartPause.setText("Start");
                mButtonStartPause.setVisibility(View.INVISIBLE);
                mButtonReset.setVisibility(View.VISIBLE);

                Intent broadcastIntent = new Intent("TIMER_FINISH_ACTION");
                sendBroadcast(broadcastIntent);
            }

        }.start();

        mTimerRunning = true;
        mButtonStartPause.setText("pause");
        mButtonReset.setVisibility(View.INVISIBLE);
    }

    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        mButtonStartPause.setText("Start");
        mButtonReset.setVisibility(View.VISIBLE);
    }

    private void resetTimer() {
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
        mButtonReset.setVisibility(View.INVISIBLE);
        mButtonStartPause.setVisibility(View.VISIBLE);
    }

    private void updateCountDownText() {
        int hours = (int) ((mTimeLeftInMillis / 1000) / 3600);
        int minutes = (int) ((mTimeLeftInMillis / 1000) % 3600) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);

        mTextViewCountDown.setText(timeLeftFormatted);
    }


    private void addMinute() {
        mTimeLeftInMillis += 60000; // Добавление 1 минуты в миллисекундах
        updateCountDownText();
    }

    private void subtractMinute() {
        mTimeLeftInMillis -= 60000; // Уменьшение 1 минуты в миллисекундах
        if (mTimeLeftInMillis < 0) {
            mTimeLeftInMillis = 0;
        }
        updateCountDownText();
    }

    private void addHour() {
        mTimeLeftInMillis += 3600000; // Добавление 1 часа в миллисекундах
        updateCountDownText();
    }

    private void subtractHour() {
        mTimeLeftInMillis -= 3600000; // Уменьшение 1 часа в миллисекундах
        if (mTimeLeftInMillis < 0) {
            mTimeLeftInMillis = 0;
        }
        updateCountDownText();
    }
}