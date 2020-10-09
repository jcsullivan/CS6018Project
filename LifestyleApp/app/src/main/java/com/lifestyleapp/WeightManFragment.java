package com.lifestyleapp;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.lifestyleapp.Calculators;


public class WeightManFragment extends Fragment implements View.OnClickListener {

    public final int WEIGHT_MAN_SIGNAL = 2;
    private Button buttonLifestyle, buttonCalculate;
    private EditText weightBasal, weightCalories, weightBMI;
    private Double doubleToLose, doubleBasal, doubleCalories, doubleBMI;
    private Boolean boolSedentary = true;
    private TextView tvPoundsPerWeek, tvHeaderInformation;
    private SeekBar seekBarPoundsPerWeek;
    private RadioButton radioButtonActive, radioButtonSedentary;
    private ImageView profilePhoto;
    private UserViewModel userViewModel;

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
        buttonCalculate = weight_man_frag_view.findViewById(R.id.weight_man_calc_btn);
        radioButtonActive = weight_man_frag_view.findViewById(R.id.calculatorActiveFrag);
        radioButtonSedentary = weight_man_frag_view.findViewById(R.id.calculatorSedentaryFrag);
        profilePhoto = weight_man_frag_view.findViewById(R.id.weight_man_profile_pic_frag);

        buttonLifestyle.setOnClickListener(this);
        buttonCalculate.setOnClickListener(this);

        //pounds per week seek bar
        seekBarPoundsPerWeek = weight_man_frag_view.findViewById(R.id.calculatorPoundsPerWeekFrag);
        seekBarPoundsPerWeek.setOnSeekBarChangeListener(seekBarChangePoundsPerWeek);

        //text above pounds per week seek bar
        doubleToLose = seekBarPoundsPerWeek.getProgress()/10.0;
        tvPoundsPerWeek = weight_man_frag_view.findViewById(R.id.tvCalculatorChangeTextFrag);
        tvPoundsPerWeek.setText("Pounds To Change Per Week: " + doubleToLose);
        return weight_man_frag_view;
    }

    @Override
    public void onStart()
    {
        super.onStart();

        profilePhoto = weight_man_frag_view.findViewById(R.id.weight_man_profile_pic_frag);
        weightBMI = weight_man_frag_view.findViewById(R.id.bmiEditTextFrag);
        tvHeaderInformation = weight_man_frag_view.findViewById(R.id.headerInformationFrag);

        // GET USER FROM VIEWMODEL (IF THERE IS ONE), THEN SET THE TEXT FIELDS ON THE UI
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        User user = userViewModel.getProfileViewModelData().getValue();

        if (user != null) {

            double ht = user.getHeight();
            double wt = user.getWeight();

            if (ht != 0 && wt != 0) {
                tvHeaderInformation.setText("Calculations based on a weight of " + wt + " pounds and a height of " + ht + " inches.");
            }

            double bmi = Double.parseDouble(Calculators.BMI());
            user.setBmi(bmi);
            weightBMI.setText(String.valueOf(bmi));

            if (user.getProfilePhotoPath() != null) {
                // TODO - GET THE PHOTO FROM THE DB
            }

        }

    }

    @Override
    public void onClick(View view)
    {
        switch(view.getId())
        {
            case R.id.weight_man_calc_btn:
            {
                weightBMI = weight_man_frag_view.findViewById(R.id.bmiEditTextFrag);
                seekBarPoundsPerWeek = weight_man_frag_view.findViewById(R.id.calculatorPoundsPerWeekFrag);
                weightBasal = weight_man_frag_view.findViewById(R.id.basalMetRateEditTextFrag);
                weightCalories = weight_man_frag_view.findViewById(R.id.dailyCalEditTextFrag);

                doubleBMI = Double.parseDouble(weightBMI.getText().toString());

                if(radioButtonActive.isSelected())
                {
                    boolSedentary = false;
                }

                UserKt.getDefaultUser().setSedentary(boolSedentary);

                if(UserKt.getDefaultUser().getHeight() != 0.0 && UserKt.getDefaultUser().getWeight() != 0.0 && doubleBMI != 0.0)
                {
                    UserKt.getDefaultUser().setBmrtee(Double.parseDouble(Calculators.BMRTEE()));
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

    // seek bar listener for pounds per week
    SeekBar.OnSeekBarChangeListener seekBarChangePoundsPerWeek = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int pounds, boolean fromUser) {
            // updated continuously as the user slides the thumb
            doubleToLose = ((double)pounds / 10.0);
            tvPoundsPerWeek.setText("Pounds To Change Per Week: " + doubleToLose);
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