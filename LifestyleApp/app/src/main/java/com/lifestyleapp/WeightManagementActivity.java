package com.lifestyleapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lifestyleapp.Calculators;

public class WeightManagementActivity extends AppCompatActivity implements View.OnClickListener{

    private Button buttonLifestyle, buttonCalculate;
    private EditText weightBasal, weightCalories, weightBMI;
    private Double doubleToLose, doubleBasal, doubleCalories, doubleBMI;
    private Boolean boolSedentary = true;
    private TextView tvPoundsPerWeek, tvHeaderInformation;
    private SeekBar seekBarPoundsPerWeek;
    private RadioButton radioButtonActive, radioButtonSedentary;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_management);

        buttonLifestyle = findViewById(R.id.lifeBtnMyProf);
        buttonCalculate = findViewById(R.id.saveProfile);
        radioButtonActive = findViewById(R.id.calculatorActive);
        radioButtonSedentary = findViewById(R.id.calculatorSedentary);

        buttonLifestyle.setOnClickListener(this);
        buttonCalculate.setOnClickListener(this);

        //pounds per week seek bar
        seekBarPoundsPerWeek = findViewById(R.id.calculatorPoundsPerWeek);
        seekBarPoundsPerWeek.setOnSeekBarChangeListener(seekBarChangePoundsPerWeek);

        //text above pounds per week seek bar
        doubleToLose = seekBarPoundsPerWeek.getProgress()/10.0;
        tvPoundsPerWeek = findViewById(R.id.tvCalculatorChangeText);
        tvPoundsPerWeek.setText("Pounds To Change Per Week: " + doubleToLose);

    }

    @Override
    protected void onStart()
    {
        super.onStart();

        weightBMI = findViewById(R.id.bmiEditText);
        tvHeaderInformation = findViewById(R.id.headerInformation);

        if(UserKt.getDefaultUser().getHeight() != 0 && UserKt.getDefaultUser().getWeight() != 0)
        {
            tvHeaderInformation.setText("Calculations based on a weight of " + UserKt.getDefaultUser().getWeight() + " pounds and a height of " + UserKt.getDefaultUser().getHeight() + " inches.");
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
                weightBMI = findViewById(R.id.bmiEditText);
                seekBarPoundsPerWeek = findViewById(R.id.calculatorPoundsPerWeek);
                weightBasal = findViewById(R.id.basalMetRateEditText);
                weightCalories = findViewById(R.id.dailyCalEditText);

                doubleBMI = Double.parseDouble(weightBMI.getText().toString());

                if(radioButtonActive.isSelected())
                {
                    boolSedentary = false;
                }

                UserKt.getDefaultUser().setSedentary(boolSedentary);

                if(UserKt.getDefaultUser().getHeight() != 0.0 && UserKt.getDefaultUser().getWeight() != 0.0 && doubleBMI != 0.0)
                {
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

    // seek bar listener for pounds per week
    SeekBar.OnSeekBarChangeListener seekBarChangePoundsPerWeek = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int pounds, boolean fromUser) {
            // updated continuously as the user slides the thumb
            doubleToLose = ((double)pounds / 10.0);
            tvPoundsPerWeek.setText("Pounds To Lose Per Week: " + doubleToLose);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            // called when the user first touches the SeekBar
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // called after the user finishes moving the SeekBar
        }
    };


}