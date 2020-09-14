package com.lifestyleapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class WeightManagementActivity extends AppCompatActivity implements View.OnClickListener{

    private Button buttonLifestyle, buttonCalculate;
    private EditText weightCurrent, weightGoal, weightHeight, weightToLose, weightBasal, weightCalories, weightBMI;
    private Spinner weightSedentary;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_management);

        buttonLifestyle = findViewById(R.id.lifeBtnMyProf);
        buttonCalculate = findViewById(R.id.saveProfile);

        buttonLifestyle.setOnClickListener(this);
        buttonCalculate.setOnClickListener(this);
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        weightCurrent = findViewById(R.id.profileName);
        weightHeight = findViewById(R.id.profileCity);

        if(UserKt.getDefaultUser().getWeight() != 0)
        {
            weightCurrent.setText(String.format("%s", UserKt.getDefaultUser().getWeight()));
        }
        if(UserKt.getDefaultUser().getHeight() != 0)
        {
            weightHeight.setText(String.format("%s", UserKt.getDefaultUser().getHeight()));
        }
    }

    @Override
    public void onClick(View view)
    {
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