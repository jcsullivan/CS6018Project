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
    private Double doubleCurrent, doubleGoal, doubleHeight, doubleToLose, doubleBasal, doubleCalories, doubleBMI;
    private Boolean boolSedentary;

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

        if(UserKt.getDefaultUser().getHeight() != 0 && UserKt.getDefaultUser().getWeight() != 0)
        {
            weightCurrent.setText(String.format("%s", UserKt.getDefaultUser().getWeight()));
            weightHeight.setText(String.format("%s", UserKt.getDefaultUser().getHeight()));
            UserKt.getDefaultUser().setBmi(Calculators.BMI());
            weightBMI.setText(String.valueOf(UserKt.getDefaultUser().getBmi()));
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
                weightToLose = findViewById(R.id.lb_to_change);
                weightBasal = findViewById(R.id.basalMetRateEditText);
                weightCalories = findViewById(R.id.dailyCalEditText);

                doubleCurrent = Double.parseDouble(weightCurrent.getText().toString());
                doubleHeight = Double.parseDouble(weightHeight.getText().toString());
                doubleBMI = Double.parseDouble(weightBMI.getText().toString());
                doubleGoal = Double.parseDouble(weightGoal.getText().toString());
                doubleToLose = Double.parseDouble(weightToLose.getText().toString());

                // ***** Need to figure out spinners.
                boolSedentary = true;

                if(doubleCurrent != 0.0 && doubleHeight != 0.0 && doubleBMI != 0.0)
                {
                    UserKt.getDefaultUser().setHeight(doubleHeight);
                    UserKt.getDefaultUser().setWeight(doubleCurrent);
                    UserKt.getDefaultUser().setBmrtee(Calculators.BMRTEE());
                    weightBasal.setText(String.valueOf(UserKt.getDefaultUser().getBmrtee()));
                    weightCalories.setText(Calculators.caloriesToEat(doubleToLose));
                }
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