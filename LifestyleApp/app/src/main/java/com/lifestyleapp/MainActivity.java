package com.lifestyleapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements ViewStub.OnClickListener {

    Button profileButton;
    Button calculatorButton;
    Button hikesButton;
    Button weatherButton;
    Button weightManagementButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.dynamic_activity);
        FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.add(R.id.dynamic_header,new Life_Tool_fTop(),"Life_Tool_fTop");
        fTrans.add(R.id.dynamic_full_center,new Life_Tool_fMid(),"Life_Tool_fMid");

        fTrans.commit();
        //weightManagementButton = findViewById(R.id.weight_man_btn);
        //weightManagementButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.weight_man_btn: {
                Log.e("PRESS","PRESS");
                FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
                fTrans.add(R.id.dynamic_header, new Weight_Man_fTop(), "Weight_Man_fTop");
                fTrans.add(R.id.dynamic_full_center, new Weight_Man_fMid(), "Weight_Man_fMid");
                fTrans.add(R.id.dynamic_footer, new Weight_Man_fBot(), "Weight_Man_fBot");
                break;
            }
        }
    }




        //profileButton = findViewById(R.id.profileButton); //change ID to Sam's
        //calculatorButton = findViewById(R.id.calculatorButton); //change ID to Sam's
        //hikesButton = findViewById(R.id.hikesButton); //change ID to Sam's
        //weatherButton = findViewById(R.id.weatherButton);

        /*profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO
            }
        });

        calculatorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO
            }
        });

        hikesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Search for nearby hikes on Google Maps
                // https://developers.google.com/maps/documentation/urls/android-intents#java_6
                // The level of Zoom can be adjusted - click on the link above for code
                Uri gmmIntentUri = Uri.parse("geo:0,0?q=hiking");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

        weatherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO
            }
        });

    }*/

}