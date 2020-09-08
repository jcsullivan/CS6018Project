package com.lifestyleapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class WeightManagementActivity extends AppCompatActivity implements View.OnClickListener{


    Button buttonLifestyleWeightMan;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_management);

        buttonLifestyleWeightMan = findViewById(R.id.lifeBtnMyProf);


        buttonLifestyleWeightMan.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.lifeBtnMyProf:
            {
                Intent lifeIntent = new Intent(this, MainActivity.class);
                this.startActivity(lifeIntent);
            }
            break;
        }
    }


}