package com.lifestyleapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener
{
    protected User newUser;

    private Button buttonCamera, buttonLifestyle, buttonSaveProfile;
    private EditText profileName, profileAge, profileCity, profileCountry, profileHeight, profileWeight;
    private RadioButton profileMale, profileFemale;
    private String stringName, stringCity, stringCountry, stringGender, stringAge, stringHeight, stringWeight;
    private int intAge;
    private double doubleHeight, doubleWeight;

    //ImageView profilePicture = null;
    Bitmap profilePicture = null;  

    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        buttonCamera = findViewById(R.id.profileUpdatePhoto);
        buttonLifestyle = findViewById(R.id.lifeBtnMyProf);
        buttonSaveProfile = findViewById(R.id.saveProfile);

        buttonCamera.setOnClickListener(this);
        buttonLifestyle.setOnClickListener(this);
        buttonSaveProfile.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.saveProfile:
            {
                profileName = findViewById(R.id.profileName);
                profileAge = findViewById(R.id.profileAge);
                profileCity = findViewById(R.id.profileCity);
                profileCountry = findViewById(R.id.profileCountry);
                profileHeight = findViewById(R.id.profileHeight);
                profileWeight = findViewById(R.id.profileWeight);
                profileMale = findViewById(R.id.profileMale);
                profileFemale = findViewById(R.id.profileFemale);

                stringName = profileName.getText().toString();
                stringAge = profileAge.getText().toString();
                stringCity = profileCity.getText().toString();
                stringCountry = profileCountry.getText().toString();
                stringHeight = profileHeight.getText().toString();
                stringWeight = profileWeight.getText().toString();

                if(profileMale.isSelected())
                {
                    stringGender = "Male";
                }
                //if(profileFemale.isSelected())
                else
                {
                    stringGender = "Female";
                }

                if(stringName == "" || stringAge == "" || stringCity == "" || stringCountry == "" || stringHeight == "" || stringWeight == "" || (!profileMale.isSelected() && !profileFemale.isSelected()))
                {
                    Toast.makeText(ProfileActivity.this, "Please fill out all fields!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    intAge = Integer.parseInt(stringAge);
                    doubleHeight = Double.parseDouble(stringHeight);
                    doubleWeight = Double.parseDouble(stringWeight);
                    newUser = new User(stringName, intAge, stringCity, stringCountry, doubleHeight, doubleWeight, stringGender, profilePicture);
                }

                break;
            }
            case R.id.profileUpdatePhoto:
            {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(cameraIntent.resolveActivity(getPackageManager()) != null)
                {
                    startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
                }
                break;
            }
            case R.id.lifeBtnMyProf: {
                Intent lifeIntent = new Intent(this, MainActivity.class);
                this.startActivity(lifeIntent);
                break;
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            profilePicture = (Bitmap) extras.get("data");
            //Bitmap thumbnailImage = (Bitmap) extras.get("data");
            //profilePicture = findViewById(R.id.profilePhoto);
            //profilePicture.setImageBitmap(thumbnailImage);
            // ***** STILL NEED TO ADD TO USER CLASS DATA.
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //Get the strings
        stringName = profileName.getText().toString();
        stringAge = profileAge.getText().toString();
        stringCity = profileCity.getText().toString();
        stringCountry = profileCountry.getText().toString();
        stringHeight = profileHeight.getText().toString();
        stringWeight = profileWeight.getText().toString();

        //Put them in the outgoing Bundle
        outState.putString("NAME_TEXT",stringName);
        outState.putString("AGE_TEXT",stringAge);
        outState.putString("CITY_TEXT",stringCity);
        outState.putString("COUNTRY_TEXT",stringCountry);
        outState.putString("HEIGHT_TEXT",stringHeight);
        outState.putString("WEIGHT_TEXT",stringWeight);

        //Save the view hierarchy
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        //Restore stuff
        profileName.setText(savedInstanceState.getString("NAME_TEXT"));
        profileAge.setText(savedInstanceState.getString("AGE_TEXT"));
        profileCity.setText(savedInstanceState.getString("CITY_TEXT"));
        profileCountry.setText(savedInstanceState.getString("COUNTRY_TEXT"));
        profileHeight.setText(savedInstanceState.getString("HEIGHT_TEXT"));
        profileWeight.setText(savedInstanceState.getString("WEIGHT_TEXT"));

        //Restore the view hierarchy automatically
        super.onRestoreInstanceState(savedInstanceState);
    }
}
