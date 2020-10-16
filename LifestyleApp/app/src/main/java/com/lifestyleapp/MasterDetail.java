package com.lifestyleapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;

public class MasterDetail extends AppCompatActivity implements NavigationFragment.OnNavSelectedListener, ProfilePageFragment.OnLifePressListener, WeightManFragment.OnLifePressFromWeightListener {

    private NavigationFragment mMasterListNavFrag;
    private ProfilePageFragment profilePageFragment;
    private WeightManFragment weightManFragment;
    private WeatherFragment weatherFragment;

    // Gesture fields
    private SensorManager sensorManager;
    private Sensor gyroscope;
    private Sensor accelerometer;
    private double accelerationThreshold = 10.5;
    private double rotationThreshold = 1.8;
    private boolean isStepCounterRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_detail);

        mMasterListNavFrag = new NavigationFragment();
        profilePageFragment = new ProfilePageFragment();
        weightManFragment = new WeightManFragment();
        weatherFragment = new WeatherFragment();


        FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
        if (isTablet()) {
            fTrans.replace(R.id.master_detail_nav_pane_tablet, mMasterListNavFrag, "frag_masterlist");
            fTrans.replace(R.id.master_detail_right_pane_tablet, profilePageFragment, "frag_profile");
        } else {
            fTrans.replace(R.id.master_detail_pane_phone, mMasterListNavFrag, "frag_masterlist");
        }
        fTrans.commit();

        // SENSOR SETUP
        isStepCounterRunning = false;
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

    }

    // SHAKE TO START STEP COUNTER
    private SensorEventListener accelerationListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {

            Context context = getApplicationContext();

            double xAcceleration = sensorEvent.values[0];
            double yAcceleration = sensorEvent.values[1];
            double zAcceleration = sensorEvent.values[2];

            double magnitude = Math.sqrt(Math.pow(xAcceleration, 2) + Math.pow(yAcceleration, 2) + Math.pow(zAcceleration, 2));

            //Check if this is greater than some threshold
            if(magnitude > accelerationThreshold && !isStepCounterRunning) {
                    MediaPlayer dingMediaPlayer = MediaPlayer.create(context, R.raw.ding);
                    dingMediaPlayer.start();
                    isStepCounterRunning = true;
                    // TODO START STEP COUNTER
            }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {}

    };

    // SPIN TO STOP STEP COUNTER
    private SensorEventListener rotationListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {

            Context context = getApplicationContext();

            double xRotation = sensorEvent.values[0];
            double yRotation = sensorEvent.values[1];
            double zRotation = sensorEvent.values[2];

            double magnitude = Math.sqrt(Math.pow(xRotation, 2) + Math.pow(yRotation, 2) + Math.pow(zRotation, 2));

            if (magnitude > rotationThreshold && isStepCounterRunning) {
                MediaPlayer drumsMediaPlayer = MediaPlayer.create(context, R.raw.drums);
                drumsMediaPlayer.start();
                isStepCounterRunning = false;
                // TODO STOP STEP COUNTER
            }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    };

    @Override
    public void onResume() {
        super.onResume();
        if(accelerometer !=null){
            sensorManager.registerListener(accelerationListener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
            sensorManager.registerListener(rotationListener, gyroscope,SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(accelerometer!=null){
            sensorManager.unregisterListener(accelerationListener);
            sensorManager.unregisterListener(rotationListener);
        }
    }

    boolean isTablet() {
        return getResources().getBoolean(R.bool.isTablet);
    }

    @Override
    public void onNavSelected(int navIndex) {
        FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
        if (isTablet()) {
            if (navIndex == mMasterListNavFrag.PROFILE_BUTTON_INDEX) {
                fTrans.replace(R.id.master_detail_right_pane_tablet, profilePageFragment, "frag_myprof_tab").commit();
            } else if (navIndex == mMasterListNavFrag.WEIGHT_BUTTON_INDEX) {
                fTrans.replace(R.id.master_detail_right_pane_tablet, weightManFragment, "frag_weightman_tab").commit();
            }
            else{
                fTrans.replace(R.id.master_detail_right_pane_tablet, weatherFragment, "frag_weather_tab").commit();
            }
        } else {
            if (navIndex == mMasterListNavFrag.PROFILE_BUTTON_INDEX) {
                fTrans.replace(R.id.master_detail_pane_phone, profilePageFragment, "frag_myprof_phone").addToBackStack("profile").commit();
            } else if (navIndex == mMasterListNavFrag.WEIGHT_BUTTON_INDEX) {
                fTrans.replace(R.id.master_detail_pane_phone, weightManFragment, "frag_weightman_phone").addToBackStack("weight").commit();
            } else{
                fTrans.replace(R.id.master_detail_pane_phone, weatherFragment, "frag_weather_phone").addToBackStack("weather").commit();
            }
        }
        //fTrans.commit();
    }

    @Override
    public void onLifeBtnPress() {
        FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
        if (isTablet()) {
                fTrans.remove(profilePageFragment);
        } else {
            fTrans.replace(R.id.master_detail_pane_phone, mMasterListNavFrag);
        }
        fTrans.commit();
    }


    @Override
    public void onLifeBtnPressFromWeight() {
        FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
        if (isTablet()) {
                fTrans.remove(weightManFragment);
            }
         else {
            fTrans.replace(R.id.master_detail_pane_phone, mMasterListNavFrag);
        }
        fTrans.commit();
    }
    }