package com.lifestyleapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class RegistrationFragment extends Fragment implements View.OnClickListener {

    OnSubmitPressListener submitPressListener;
    View regFragmentView;
    Button regCodeSubmitButton;
    EditText regCodeEditText;
    String regCode;

    public interface OnSubmitPressListener {
        public void onSubmitBtnPress(String regCode);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            submitPressListener = (RegistrationFragment.OnSubmitPressListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnSubmitPressListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        regFragmentView = inflater.inflate(R.layout.fragment_registration, container, false);
        regCodeEditText = regFragmentView.findViewById(R.id.regCodeEditText);
        regCodeSubmitButton = regFragmentView.findViewById(R.id.submit_reg_code);
        regCodeSubmitButton.setOnClickListener(this);


        return regFragmentView;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.submit_reg_code:
                regCode = regCodeEditText.getText().toString();
                if (regCode.isEmpty()) {
                    Toast.makeText(getActivity(), "Please enter Registration code!", Toast.LENGTH_SHORT).show();
                    break;
                } else {
                    submitPressListener.onSubmitBtnPress(regCode);
                }
        }

    }
}