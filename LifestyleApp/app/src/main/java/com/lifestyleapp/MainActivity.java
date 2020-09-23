package com.lifestyleapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button profileButton;
    Button weightManButton;
    Button calculatorButton;
    Button hikesButton;
    Button weatherButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lifestyle_tools);

        profileButton = findViewById(R.id.my_prof_btn); //change ID to Sam's
        weightManButton = findViewById(R.id.weight_man_btn);
        //calculatorButton = findViewById(R.id.); //change ID to Sam's
        hikesButton = findViewById(R.id.hike_btn); //change ID to Sam's
        weatherButton = findViewById(R.id.weather_btn);

        profileButton.setOnClickListener(this);
        weightManButton.setOnClickListener(this);
        hikesButton.setOnClickListener(this);
        weatherButton.setOnClickListener(this);

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.my_prof_btn: {
                Intent myProfIntent = new Intent(this, ProfileActivity.class);
                this.startActivity(myProfIntent);
            }
            break;
            case R.id.weight_man_btn: {
                Intent weightManIntent = new Intent(this, WeightManagementActivity.class);
                this.startActivity(weightManIntent);
            }
            break;
            case R.id.hike_btn: {
                // Search for nearby hikes on Google Maps
                // https://developers.google.com/maps/documentation/urls/android-intents#java_6
                // The level of Zoom can be adjusted - click on the link above for code
                Uri gmmIntentUri = Uri.parse("geo:0,0?q=hiking");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);

            }
            break;
            case R.id.weather_btn: {
                // Search for local weather using the browser
                // https://www.youtube.com/watch?v=mMKC_gRSL5Q
                Uri uri = Uri.parse("http://www.google.com/search?q=local weather");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
            break;


            }
        }



    }






