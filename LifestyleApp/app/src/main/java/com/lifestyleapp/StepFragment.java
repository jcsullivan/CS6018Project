package com.lifestyleapp;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.snackbar.Snackbar;

public class StepFragment extends Fragment implements View.OnClickListener
{
    private ConstraintLayout mainLayout;
    private TextView counterField;
    private Button startSteps, stopSteps, stepBackToLifestyle;

    private UserViewModel mUserViewModel;
    private User user;

    private SensorManager mSensorManager;
    private Sensor mStepCounter;

    ProfilePageFragment.OnLifePressListener lifePressListener;

    private View step_frag_view;

    private int steps;

    // Gesture fields
    private Sensor gyroscope;
    private Sensor accelerometer;
    private double accelerationThreshold = 10.5;
    private double rotationThreshold = 1.8;
    private boolean isStepCounterRunning;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            lifePressListener = (ProfilePageFragment.OnLifePressListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnLifePressListener");
        }
    }

    public StepFragment()
    {

    }

    public static StepFragment newInstance()
    {
        StepFragment fragment = new StepFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        step_frag_view = inflater.inflate(R.layout.fragment_step, container, false);

        counterField = step_frag_view.findViewById(R.id.counterField);
        startSteps = (Button)step_frag_view.findViewById(R.id.startSteps);
        stopSteps = (Button)step_frag_view.findViewById(R.id.stopSteps);
        stepBackToLifestyle = (Button)step_frag_view.findViewById(R.id.stepBackToLifestyle);
        mainLayout = step_frag_view.findViewById(R.id.main_layout);

        startSteps.setOnClickListener(this);
        stopSteps.setOnClickListener(this);
        stepBackToLifestyle.setOnClickListener(this);

        //Create the view model
        mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        user = mUserViewModel.getProfileViewModelData().getValue();

        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        mStepCounter = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        isStepCounterRunning = false;
        gyroscope = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        //Set the observer
        //mUserViewModel.getData().observe(this, stepsObserver);

        return step_frag_view;
    }

    private SensorEventListener mListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            counterField.setText("STEPS: " + String.valueOf(sensorEvent.values[0]));
            steps = (int)sensorEvent.values[0];
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    @Override
    public void onStart()
    {
        super.onStart();

        mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        user = mUserViewModel.getProfileViewModelData().getValue();

        counterField = step_frag_view.findViewById(R.id.counterField);
        startSteps = (Button)step_frag_view.findViewById(R.id.startSteps);
        stopSteps = (Button)step_frag_view.findViewById(R.id.stopSteps);
        stepBackToLifestyle = (Button)step_frag_view.findViewById(R.id.stepBackToLifestyle);
        mainLayout = step_frag_view.findViewById(R.id.main_layout);

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.startSteps:
                startSteps();
                break;

            case R.id.stopSteps:
                stopSteps();
                break;

            case R.id.stepBackToLifestyle:
                stopSteps();
                lifePressListener.onLifeBtnPress();
                break;

        }
    }

    private void startSteps() {
        if(!isStepCounterRunning) {

            if (mStepCounter != null) mSensorManager.registerListener(mListener, mStepCounter, SensorManager.SENSOR_DELAY_NORMAL);
            counterField.setText("STEPS: 0");
            Snackbar.make(mainLayout, "STEP COUNTER STARTED", 2500).show();
            isStepCounterRunning = true;

        }
    }

    private void stopSteps() {
        if (isStepCounterRunning) {

            if (user != null) user.setSteps(steps);
            if (mStepCounter != null) mSensorManager.unregisterListener(mListener);
            Snackbar.make(mainLayout, "STEP COUNTER STOPPED", 2500).show();
            isStepCounterRunning = false;

        }
    }

    // SHAKE TO START STEP COUNTER
    private SensorEventListener accelerationListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {

            Context context = getActivity().getApplicationContext();

            double xAcceleration = sensorEvent.values[0];
            double yAcceleration = sensorEvent.values[1];
            double zAcceleration = sensorEvent.values[2];

            double magnitude = Math.sqrt(Math.pow(xAcceleration, 2) + Math.pow(yAcceleration, 2) + Math.pow(zAcceleration, 2));

            //Check if this is greater than some threshold
            if(magnitude > accelerationThreshold && !isStepCounterRunning) {
                MediaPlayer dingMediaPlayer = MediaPlayer.create(context, R.raw.ding);
                dingMediaPlayer.start();
                startSteps();
            }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {}

    };

    // SPIN TO STOP STEP COUNTER
    private SensorEventListener rotationListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {

            Context context = getActivity().getApplicationContext();

            double xRotation = sensorEvent.values[0];
            double yRotation = sensorEvent.values[1];
            double zRotation = sensorEvent.values[2];

            double magnitude = Math.sqrt(Math.pow(xRotation, 2) + Math.pow(yRotation, 2) + Math.pow(zRotation, 2));

            if (magnitude > rotationThreshold && isStepCounterRunning) {
                MediaPlayer drumsMediaPlayer = MediaPlayer.create(context, R.raw.drums);
                drumsMediaPlayer.start();
                stopSteps();
            }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    };

    @Override
    public void onResume() {
        super.onResume();
        if(mStepCounter!=null){
            mSensorManager.registerListener(mListener,mStepCounter,SensorManager.SENSOR_DELAY_NORMAL);
        }
        if(accelerometer !=null){
            mSensorManager.registerListener(accelerationListener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if(gyroscope != null) {
            mSensorManager.registerListener(rotationListener, gyroscope,SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(user != null)
        {
            user.setSteps(steps);
        }
        if(mStepCounter!=null){
            mSensorManager.unregisterListener(mListener);
        }
        if(accelerometer!=null){
            mSensorManager.unregisterListener(accelerationListener);
        }
        if(gyroscope!=null){
            mSensorManager.unregisterListener(rotationListener);
        }
    }
}
