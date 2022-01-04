package com.example.myapplication.ui.alarm;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.myapplication.R;

import java.util.Calendar;
import java.util.Random;

public class RingActivity  extends AppCompatActivity implements SensorEventListener {

    Button dismiss;
    Button snooze;
    ImageView clock;
    SensorManager sensorManager;
    Sensor stepCountSensor;
    TextView stepCountView;
    int currentSteps = 0;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ring);

        dismiss = findViewById(R.id.activity_ring_dismiss);
        snooze = findViewById(R.id.activity_ring_snooze);
        stepCountView = findViewById(R.id.stepCountView);

        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentService = new Intent(getApplicationContext(), AlarmService.class);
                getApplicationContext().stopService(intentService);
                finish();
            }
        });

        snooze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.add(Calendar.MINUTE, 10);

                Alarm alarm = new Alarm(
                        new Random().nextInt(Integer.MAX_VALUE),
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        true,
                        "Snooze",
                        false,
                        false,
                        false,
                        false,
                        false,
                        false,
                        false,
                        false
                );

                alarm.schedule(getApplicationContext());

                Intent intentService = new Intent(getApplicationContext(), AlarmService.class);
                getApplicationContext().stopService(intentService);
                finish();
            }
        });

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED){
            requestPermissions(new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, 0);
        }

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        stepCountSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        stepCountView = findViewById(R.id.stepCountView);

        currentSteps = 0;
        String s = "You Walked " + String.valueOf(currentSteps) + " steps!!";
        stepCountView.setText(s);
//        while(true){
//            if(currentSteps >= 5) {
//                dismiss.setVisibility(View.VISIBLE);
//                snooze.setVisibility(View.VISIBLE);
//            }
//        }


//        animateClock();
    }

//    private void animateClock() {
//        ObjectAnimator rotateAnimation = ObjectAnimator.ofFloat(clock, "rotation", 0f, 20f, 0f, -20f, 0f);
//        rotateAnimation.setRepeatCount(ValueAnimator.INFINITE);
//        rotateAnimation.setDuration(800);
//        rotateAnimation.start();
//    }

    public void onStart() {
        super.onStart();
        if(stepCountSensor != null) {
            sensorManager.registerListener(this,stepCountSensor,SensorManager.SENSOR_DELAY_FASTEST);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.sensor.getType() == Sensor.TYPE_STEP_DETECTOR){
            if(sensorEvent.values[0]==1.0f){
                currentSteps++;
                String s = "You Walked " + String.valueOf(currentSteps) + " steps!!";
                stepCountView.setText(s);
                if(currentSteps >= 5) {
                    dismiss.setVisibility(View.VISIBLE);
                    snooze.setVisibility(View.VISIBLE);
                }
            }

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
