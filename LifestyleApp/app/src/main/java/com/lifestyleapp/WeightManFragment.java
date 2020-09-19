package com.lifestyleapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.lifestyleapp.Calculators;


public class WeightManFragment extends Fragment implements View.OnClickListener {
    public final int WEIGHT_MAN_SIGNAL = 2;
    private Button buttonLifestyle, buttonCalculate;
    private EditText weightCurrent, weightGoal, weightHeight, weightToLose, weightBasal, weightCalories, weightBMI;
    private Spinner weightSedentary;
    private Double doubleCurrent, doubleGoal, doubleHeight, doubleToLose, doubleBasal, doubleCalories, doubleBMI;
    private Boolean boolSedentary;
    private View weight_man_frag_view;
    OnLifePressFromWeightListener lifePressListenerFromWeight;



    public WeightManFragment() {
        // Required empty public constructor
    }

    public interface OnLifePressFromWeightListener {
        public void onLifeBtnPressFromWeight();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            lifePressListenerFromWeight = (WeightManFragment.OnLifePressFromWeightListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnLifePressFromWeightListener");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        weight_man_frag_view=  inflater.inflate(R.layout.fragment_weight_man, container, false);
        buttonLifestyle = weight_man_frag_view.findViewById(R.id.lifestyle_btn_weightman_frag);
        buttonCalculate = weight_man_frag_view.findViewById(R.id.calc_weightman_frag);

        buttonLifestyle.setOnClickListener(this);
        buttonCalculate.setOnClickListener(this);
        return weight_man_frag_view;
    }

    @Override
    public void onStart()
    {
        super.onStart();

        weightCurrent = weight_man_frag_view.findViewById(R.id.curr_weight_frag);
        weightHeight = weight_man_frag_view.findViewById(R.id.weight_height_frag);
        weightBMI = weight_man_frag_view.findViewById(R.id.bmiEditTextFrag);

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
            case R.id.calc_weightman_frag:
            {
                weightCurrent = weight_man_frag_view.findViewById(R.id.curr_weight_frag);
                weightHeight = weight_man_frag_view.findViewById(R.id.weight_height_frag);
                weightBMI = weight_man_frag_view.findViewById(R.id.bmiEditTextFrag);
                weightGoal = weight_man_frag_view.findViewById(R.id.goal_weight_frag);
                weightSedentary = weight_man_frag_view.findViewById(R.id.sedActSpinnerFrag);
                weightToLose = weight_man_frag_view.findViewById(R.id.lb_to_change_frag);
                weightBasal = weight_man_frag_view.findViewById(R.id.basalMetRateEditTextFrag);
                weightCalories = weight_man_frag_view.findViewById(R.id.dailyCalEditTextFrag);

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
            case R.id.lifestyle_btn_weightman_frag:
            {
                lifePressListenerFromWeight.onLifeBtnPressFromWeight();
            }
            break;
        }
    }
}