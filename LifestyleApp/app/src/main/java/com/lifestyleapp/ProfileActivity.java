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
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener
{
    protected User newUser;

    private Button buttonCamera, buttonLifestyle, buttonSaveProfile;
    private EditText profileName, profileAge, profileCity, profileCountry;
    private RadioButton profileMale, profileFemale;
    private String stringName, stringCity, stringCountry, stringAge, stringHeight, stringWeight;
    private int intAge, intGender;
    private double doubleHeight, doubleWeight;
    private TextView tvHeight, tvWeight;
    private SeekBar seekBarHeight, seekBarWeight;

    ImageView profilePhotoView;
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
        profilePhotoView= findViewById(R.id.profilePhoto);

        buttonCamera.setOnClickListener(this);
        buttonLifestyle.setOnClickListener(this);
        buttonSaveProfile.setOnClickListener(this);

        //height seek bar
        seekBarHeight = findViewById(R.id.seekBarHeight);
        seekBarHeight.setOnSeekBarChangeListener(seekBarChangeListenerHeight);

        int inches = seekBarHeight.getProgress();
        tvHeight = findViewById(R.id.textViewHeight);
        tvHeight.setText("Height: " + inches + " inches");

        //weight seek bar
        seekBarWeight = findViewById(R.id.seekBarWeight);
        seekBarWeight.setOnSeekBarChangeListener(seekBarChangeListenerWeight);

        int pounds = seekBarWeight.getProgress();
        tvWeight = findViewById(R.id.textViewWeight);
        tvWeight.setText("Weight " + pounds + " pounds");


    }

    @Override
    protected void onStart()
    {
        super.onStart();

        profileName = findViewById(R.id.profileName);
        profileAge = findViewById(R.id.profileAge);
        profileCity = findViewById(R.id.profileCity);
        profileCountry = findViewById(R.id.profileCountry);
        tvHeight = findViewById(R.id.textViewHeight);
        seekBarHeight = findViewById(R.id.seekBarHeight);
        tvWeight = findViewById(R.id.textViewWeight);
        seekBarWeight = findViewById(R.id.seekBarWeight);
        profileMale = findViewById(R.id.profileMale);
        profileFemale = findViewById(R.id.profileFemale);

        if(UserKt.getDefaultUser().getProfilePhoto()!=null)
        {
            profilePhotoView.setImageBitmap(UserKt.getDefaultUser().getProfilePhoto());
        }
        if(!UserKt.getDefaultUser().getFullName().isEmpty())
        {
            profileName.setText(UserKt.getDefaultUser().getFullName());
        }
        if(UserKt.getDefaultUser().getAge() != 0)
        {
            profileAge.setText(String.valueOf(UserKt.getDefaultUser().getAge()));
        }
        if(!UserKt.getDefaultUser().getCity().isEmpty())
        {
            profileCity.setText(UserKt.getDefaultUser().getCity());
        }
        if(!UserKt.getDefaultUser().getCountry().isEmpty())
        {
            profileCountry.setText(UserKt.getDefaultUser().getCountry());
        }
        if(UserKt.getDefaultUser().getGender() == 1)
        {
            profileMale.setChecked(true);
            profileFemale.setChecked(false);
        }
        else
        {
            profileMale.setChecked(false);
            profileFemale.setChecked(true);
        }
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
                tvHeight = findViewById(R.id.textViewHeight);
                seekBarHeight = findViewById(R.id.seekBarHeight);
                tvWeight = findViewById(R.id.textViewWeight);
                seekBarWeight = findViewById(R.id.seekBarWeight);
                profileMale = findViewById(R.id.profileMale);
                profileFemale = findViewById(R.id.profileFemale);


                stringName = profileName.getText().toString();
                stringAge = profileAge.getText().toString();
                stringCity = profileCity.getText().toString();
                stringCountry = profileCountry.getText().toString();
                doubleWeight = seekBarWeight.getProgress();
                doubleHeight = seekBarHeight.getProgress();

                if(profileMale.isSelected())
                {
                    intGender = 1;  // male
                }
                else
                {
                    intGender = 0;  // female
                }

                if(stringName.isEmpty() || stringAge.isEmpty() || stringCity.isEmpty() || stringCountry.isEmpty())
                {
                    Toast.makeText(ProfileActivity.this, "Please fill out all fields!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    intAge = Integer.parseInt(stringAge);

                    //newUser = new User(stringName, intAge, stringCity, stringCountry, doubleHeight, doubleWeight, intGender, profilePicture);

                    UserKt.setDefaultUser(new User (stringName, intAge, stringCity, stringCountry, doubleHeight, doubleWeight, intGender, profilePicture, 0.0, 0.0, false));
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

            //Eventually should be relocated to Save onClick Method
            UserKt.getDefaultUser().setProfilePhoto(profilePicture);

            profilePhotoView= findViewById(R.id.profilePhoto);
            profilePhotoView.setImageBitmap(profilePicture);

        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //Get the strings
        stringName = profileName.getText().toString();
        stringAge = profileAge.getText().toString();
        stringCity = profileCity.getText().toString();
        stringCountry = profileCountry.getText().toString();
        stringHeight = String.valueOf(seekBarHeight.getProgress());  // FIXME
        stringWeight = String.valueOf(seekBarHeight.getProgress());  // FIXME

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
        seekBarHeight.setProgress(Integer.parseInt(Objects.requireNonNull(savedInstanceState.getString("HEIGHT_TEXT"))));  // FIXME
        seekBarWeight.setProgress(Integer.parseInt(Objects.requireNonNull(savedInstanceState.getString("WEIGHT_TEXT"))));  // FIXME

        //Restore the view hierarchy automatically
        super.onRestoreInstanceState(savedInstanceState);
    }

    // seek bar listener for height
    SeekBar.OnSeekBarChangeListener seekBarChangeListenerHeight = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int inches, boolean fromUser) {
            // updated continuously as the user slides the thumb
            tvHeight.setText("Height: " + inches + " inches");
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

    // seek bar listener for weight
    SeekBar.OnSeekBarChangeListener seekBarChangeListenerWeight = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int pounds, boolean fromUser) {
            // updated continuously as the user slides the thumb
            tvWeight.setText("Weight: " + pounds + " pounds");
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
} //
