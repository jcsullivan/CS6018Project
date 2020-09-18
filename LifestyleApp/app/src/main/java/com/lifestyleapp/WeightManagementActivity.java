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
    private EditText weightCurrent, weightHeight, weightBasal, weightCalories, weightBMI;
    private Double doubleCurrent, doubleHeight, doubleToLose, doubleBasal, doubleCalories, doubleBMI;
    private Boolean boolSedentary;
    private TextView tvPoundsPerWeek;
    private SeekBar seekBarPoundsPerWeek;
    boolean boolIsActive;
    private RadioButton radioButtonActive, radioButtonSedentary;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_management);

        buttonLifestyle = findViewById(R.id.lifeBtnMyProf);
        buttonCalculate = findViewById(R.id.saveProfile);

        buttonLifestyle.setOnClickListener(this);
        buttonCalculate.setOnClickListener(this);

        //pounds per week seek bar
        seekBarPoundsPerWeek = findViewById(R.id.calculatorPoundsPerWeek);
        seekBarPoundsPerWeek.setOnSeekBarChangeListener(seekBarChangePoundsPerWeek);

        //text above pounds per week seek bar
        double pounds = seekBarPoundsPerWeek.getProgress()/10.0;
        tvPoundsPerWeek = findViewById(R.id.tvCalculatorChangeText);
        tvPoundsPerWeek.setText("Pounds To Lose Per Week: " + pounds);

    }

    @Override
    protected void onStart()
    {
        super.onStart();

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
                seekBarPoundsPerWeek = findViewById(R.id.calculatorPoundsPerWeek);
                weightBasal = findViewById(R.id.basalMetRateEditText);
                weightCalories = findViewById(R.id.dailyCalEditText);

                doubleCurrent = Double.parseDouble(weightCurrent.getText().toString());
                doubleHeight = Double.parseDouble(weightHeight.getText().toString());
                doubleBMI = Double.parseDouble(weightBMI.getText().toString());

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

    // seek bar listener for pounds per week
    SeekBar.OnSeekBarChangeListener seekBarChangePoundsPerWeek = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int pounds, boolean fromUser) {
            // updated continuously as the user slides the thumb
            double decimalPounds = ((double)pounds / 10.0);
            tvPoundsPerWeek.setText("Pounds To Lose Per Week: " + decimalPounds);
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