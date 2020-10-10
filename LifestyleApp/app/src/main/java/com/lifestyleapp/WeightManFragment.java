package com.lifestyleapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


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

    private View weight_man_frag_view;
    OnLifePressFromWeightListener lifePressListenerFromWeight;

    private WeightManViewModel weightManViewModel;
    private User user;


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

        weightManViewModel = ViewModelProviders.of(this).get(WeightManViewModel.class);
        user = weightManViewModel.getProfileViewModelData().getValue();

        if (user.getProfilePhotoPath() != null)
        {

            FileInputStream fis = null;
            try {
                fis = getContext().openFileInput(user.getProfilePhotoPath());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            byte[] readBytes = new byte[user.getProfilePhotoSize()];
            try {
                fis.read(readBytes);
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Bitmap fromFileBmp = BitmapFactory.decodeByteArray(readBytes,0,readBytes.length);
            profilePhoto.setImageBitmap(fromFileBmp);
        }

        if(user.getHeight() != 0 && user.getWeight() != 0)
        {
            tvHeaderInformation.setText("Calculations based on a weight of " + user.getWeight() + " pounds and a height of " + user.getHeight() + " inches.");
            user.setBmi(Double.parseDouble(Calculators.BMI(user.getWeight(), user.getHeight())));
            weightBMI.setText(String.valueOf(user.getBmi()));
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

                user.setSedentary(boolSedentary);

                if(user.getHeight() != 0.0 && user.getWeight() != 0.0 && doubleBMI != 0.0)
                {
                    user.setBmrtee(Double.parseDouble(Calculators.BMRTEE(user.getWeight(), user.getHeight(), user.getAge(), user.getGender(), user.getSedentary())));
                    weightBasal.setText(String.valueOf(user.getBmrtee()));
                    weightCalories.setText(Calculators.caloriesToEat(user.getBmrtee(), user.getGender(), doubleToLose));
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