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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{

    protected User newUser;

    private Button buttonCamera, buttonLifestyle, buttonSaveProfile;
    private EditText profileName, profileAge, profileCity, profileCountry, profileHeight, profileWeight;
    private RadioButton profileMale, profileFemale;
    private String stringName, stringAge, stringCity, stringCountry, stringHeight, stringWeight;
    private Boolean male, female;

    ImageView profilePicture;

    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        buttonCamera = findViewById(R.id.profileUpdatePhoto);
        buttonLifestyle = findViewById(R.id.lifestyleButton);
        buttonSaveProfile = findViewById(R.id.saveProfile);

        buttonCamera.setOnClickListener(this);
        buttonLifestyle.setOnClickListener(this);
        buttonSaveProfile.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.lifestyleButton:
            {
                profileName = findViewById(R.id.profileName);
                profileAge = findViewById(R.id.profileAge);
                profileCity = findViewById(R.id.profileCity);
                profileCountry = findViewById(R.id.profileCountry);
                profileHeight = findViewById(R.id.profileHeight);
                profileWeight = findViewById(R.id.profileWeight);
                profileMale = findViewById(R.id.profileMale);
                profileFemale = findViewById(R.id.profileFemale);
            }
            case R.id.profileUpdatePhoto:
            {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(cameraIntent.resolveActivity(getPackageManager()) != null)
                {
                    startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap thumbnailImage = (Bitmap) extras.get("data");
            profilePicture = (ImageView) findViewById(R.id.profilePhoto);
            profilePicture.setImageBitmap(thumbnailImage);
            // ***** STILL NEED TO ADD TO USER CLASS DATA.
        }
    }
}
