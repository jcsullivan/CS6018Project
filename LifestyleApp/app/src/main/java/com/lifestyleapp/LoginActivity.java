package com.lifestyleapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class LoginActivity extends AppCompatActivity  implements View.OnClickListener, RegistrationFragment.OnSubmitPressListener {

    private Button loginButton;
    private Button registerButton;
    private EditText userEmailEditText;
    private EditText userPswEditText;
    private String userEmail;
    private String userPsw;
    private RegistrationFragment registrationFragment;
    public static final String EXTRA_MESSAGE = "com.lifestyleapp.MESSAGE";




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Amplify.Auth.fetchAuthSession(
                result -> Log.i("AmplifyQuickstart", result.toString()),
                error -> Log.e("AmplifyQuickstart", error.toString())
        );


        registrationFragment = new RegistrationFragment();
        loginButton = findViewById(R.id.login_button);
        registerButton = findViewById(R.id.register_button);
        loginButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);
        userEmailEditText = findViewById(R.id.usernameEditText);
        userPswEditText = findViewById(R.id.loginPswEditText);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_button:
                userEmail = userEmailEditText.getText().toString();
                userPsw = userPswEditText.getText().toString();
                if (userEmail.isEmpty() || userPsw.isEmpty()) {
                    Toast.makeText(this, "Please fill out both Username and Password!", Toast.LENGTH_SHORT).show();
                } else {
                    Amplify.Auth.signIn(
                            userEmail,
                            userPsw,
                            result -> Log.i("AuthQuickstart", result.isSignInComplete() ? "Sign in succeeded" : "Sign in not complete"),
                            error -> Log.e("AuthQuickstart", error.toString())
                    );
                    Intent intent = new Intent(this, MasterDetail.class);
                    intent.putExtra(EXTRA_MESSAGE, userEmail);
                    startActivity(intent);

                }
                break;
            case R.id.register_button:
                userEmail = userEmailEditText.getText().toString();
                userPsw = userPswEditText.getText().toString();
                if (userEmail.isEmpty() || userPsw.isEmpty()) {
                    Toast.makeText(this, "Please fill out both Username and Password to Register.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Amplify will set up user and fragment should load", Toast.LENGTH_SHORT).show();
                    Amplify.Auth.signUp(
                            userEmail,
                            userPsw,
                            AuthSignUpOptions.builder().userAttribute(AuthUserAttributeKey.email(), userEmail).build(),
                            result -> Log.i("AuthQuickStart", "Result: " + result.toString()),
                            error -> Log.e("AuthQuickStart", "Sign up failed", error)
                    );
                    FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
                    fTrans.replace(R.id.registration_frame, registrationFragment, "frag_reg");
                    fTrans.commit();
                }
                break;




        }

    }

    @Override
    public void onSubmitBtnPress(String regCode) {
        Toast.makeText(this,"Registration Code Received" +regCode,Toast.LENGTH_SHORT).show();
        Amplify.Auth.confirmSignUp(
                userEmail,
                regCode,
                result -> Log.i("AuthQuickstart", result.isSignUpComplete() ? "Confirm signUp succeeded" : "Confirm sign up not complete"),
                error -> Log.e("AuthQuickstart", error.toString())
        );
        FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.remove(registrationFragment);
        fTrans.commit();

    }
}

