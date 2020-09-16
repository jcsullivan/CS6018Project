package com.lifestyleapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lifestyleapp.Calculators;

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
        weightBMI = findViewById(R.id.bmiEditText);

        if(UserKt.getDefaultUser().getWeight() != 0)
        {
            weightCurrent.setText(String.format("%s", UserKt.getDefaultUser().getWeight()));
        }
        if(UserKt.getDefaultUser().getHeight() != 0)
        {
            weightHeight.setText(String.format("%s", UserKt.getDefaultUser().getHeight()));
        }
        if(UserKt.getDefaultUser().getHeight() != 0 && UserKt.getDefaultUser().getWeight() != 0)
        {
            weightBMI.setText(String.valueOf(Calculators.BMI()));
        }
    }

    @Override
    public void onClick(View view)
    {
        switch(view.getId())
        {
            case R.id.saveProfile:
            {
                weightCurrent = findViewById(R.id.profileName);
                weightHeight = findViewById(R.id.profileCity);
                weightBMI = findViewById(R.id.bmiEditText);
                weightGoal = findViewById(R.id.profileAge);
                weightSedentary = findViewById(R.id.sedActSpinner);
                weightToLose = findViewById(R.id.lb_to_lose);
                weightBasal = findViewById(R.id.basalMetRateEditText);
                weightCalories = findViewById(R.id.dailyCalEditText);  

            }
            break;
            case R.id.lifeBtnMyProf:
            {
                Intent lifeIntent = new Intent(this, MainActivity.class);
                this.startActivity(lifeIntent);
            }
            break;
        }
    }


}