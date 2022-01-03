package com.example.myapplication.ui.alarm;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class RingActivity  extends AppCompatActivity implements SensorEventListener {

//    Button dismiss;
    SensorManager sensorManager;
    Sensor stepCountSensor;
    TextView stepCountView;
    ImageView clock;
    int currentSteps = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ring);

        animateClock();

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        stepCountSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        stepCountView = findViewById(R.id.stepCountView);


//        dismiss = findViewById(R.id.activity_ring_dismiss);

//        dismiss.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intentService = new Intent(getApplicationContext(), AlarmService.class);
//                getApplicationContext().stopService(intentService);
//                finish();
//            }
//        });

        currentSteps = 0;
        while(true){
            if(currentSteps >= 5) {
                break;
            }
        }

        Intent intentService = new Intent(getApplicationContext(), AlarmService.class);
        getApplicationContext().stopService(intentService);
        finish();

        animateClock();
    }

    private void animateClock() {
        ObjectAnimator rotateAnimation = ObjectAnimator.ofFloat(clock, "rotation", 0f, 20f, 0f, -20f, 0f);
        rotateAnimation.setRepeatCount(ValueAnimator.INFINITE);
        rotateAnimation.setDuration(800);
        rotateAnimation.start();
    }

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
            }

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
