package com.lifestyleapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class ProfilePageFragment extends Fragment implements View.OnClickListener {

    private Button buttonCamera, buttonLifestyle, buttonSaveProfile;
    private EditText profileName, profileAge, profileCity, profileCountry;
    private RadioButton profileMale, profileFemale;
    private String stringName, stringCity, stringCountry, stringAge;
    private int intAge, intGender;
    private double doubleHeight, doubleWeight;
    private TextView tvHeight, tvWeight;
    private SeekBar seekBarHeight, seekBarWeight;
    private UserViewModel userViewModel;
    private int byteArrSize;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    ImageView profilePhotoView;
    Bitmap profilePicture = null;
    @Nullable String profilePhotoFileName = null;
    View myprofFragmentView;
    OnLifePressListener lifePressListener;

    public interface OnLifePressListener {
        public void onLifeBtnPress();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            lifePressListener = (OnLifePressListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnLifePressListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        myprofFragmentView =  inflater.inflate(R.layout.fragment_profile_page, container, false);

        buttonCamera = myprofFragmentView.findViewById(R.id.prof_update_photo_frag);
        buttonLifestyle = myprofFragmentView.findViewById(R.id.lifeBtnMyProfFrag);
        buttonSaveProfile = myprofFragmentView.findViewById(R.id.saveProfileFrag);
        profilePhotoView= myprofFragmentView.findViewById(R.id.myprof_photo_frag);

        buttonCamera.setOnClickListener(this);
        buttonLifestyle.setOnClickListener(this);
        buttonSaveProfile.setOnClickListener(this);

        //height seek bar
        seekBarHeight = myprofFragmentView.findViewById(R.id.seekBarHeightFrag);
        seekBarHeight.setOnSeekBarChangeListener(seekBarChangeListenerHeight);

        int inches = seekBarHeight.getProgress();
        tvHeight = myprofFragmentView.findViewById(R.id.textViewHeightFrag);
        tvHeight.setText("Height: " + inches + " inches");

        //weight seek bar
        seekBarWeight = myprofFragmentView.findViewById(R.id.seekBarWeightFrag);
        seekBarWeight.setOnSeekBarChangeListener(seekBarChangeListenerWeight);

        int pounds = seekBarWeight.getProgress();
        tvWeight = myprofFragmentView.findViewById(R.id.textViewWeightFrag);
        tvWeight.setText("Weight " + pounds + " pounds");

        return myprofFragmentView;

    }

    @Override
    public void onStart()
    {
        super.onStart();

        profileName = myprofFragmentView.findViewById(R.id.profileNameFrag);
        profileAge = myprofFragmentView.findViewById(R.id.profileAgeFrag);
        profileCity = myprofFragmentView.findViewById(R.id.profileCityFrag);
        profileCountry = myprofFragmentView.findViewById(R.id.profileCountryFrag);
        tvHeight = myprofFragmentView.findViewById(R.id.textViewHeightFrag);
        seekBarHeight = myprofFragmentView.findViewById(R.id.seekBarHeightFrag);
        tvWeight = myprofFragmentView.findViewById(R.id.textViewWeightFrag);
        seekBarWeight = myprofFragmentView.findViewById(R.id.seekBarWeightFrag);
        profileMale = myprofFragmentView.findViewById(R.id.profileMaleFrag);
        profileFemale = myprofFragmentView.findViewById(R.id.profileFemaleFrag);

        // GET USER FROM VIEWMODEL (IF THERE IS ONE), THEN SET THE TEXT FIELDS ON THE UI
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        User user = userViewModel.getProfileViewModelData().getValue();

        if (user != null) {

            if (user.getProfilePhotoPath() != null) {
                String profPhotoFileName = user.getProfilePhotoPath();

                FileInputStream fis = null;
                try {
                    fis = getContext().openFileInput(profPhotoFileName);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                byte[] readBytes = new byte[byteArrSize];
                try {
                    fis.read(readBytes);
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Bitmap fromFileBmp = BitmapFactory.decodeByteArray(readBytes,0,readBytes.length);
                profilePhotoView.setImageBitmap(fromFileBmp);
            }
            if (!user.getFullName().equals("")) profileName.setText(user.getFullName());
            if (user.getAge() != 0) profileAge.setText(String.valueOf(user.getAge()));
            if (!user.getCity().equals("")) profileCity.setText(user.getCity());
            if (!user.getCountry().equals("")) profileCountry.setText(user.getCountry());

            if (user.getGender() == 1) {
                profileMale.setChecked(true);
                profileFemale.setChecked(false);
            } else {
                profileMale.setChecked(false);
                profileFemale.setChecked(true);
            }

        }
    }


    @Override
    public void onClick(View view) {

        switch(view.getId()) {

            case R.id.saveProfileFrag: {

                profileName = myprofFragmentView.findViewById(R.id.profileNameFrag);
                profileAge = myprofFragmentView.findViewById(R.id.profileAgeFrag);
                profileCity = myprofFragmentView.findViewById(R.id.profileCityFrag);
                profileCountry = myprofFragmentView.findViewById(R.id.profileCountryFrag);
                tvHeight = myprofFragmentView.findViewById(R.id.textViewHeightFrag);
                seekBarHeight = myprofFragmentView.findViewById(R.id.seekBarHeightFrag);
                tvWeight = myprofFragmentView.findViewById(R.id.textViewWeightFrag);
                seekBarWeight = myprofFragmentView.findViewById(R.id.seekBarWeightFrag);
                profileMale = myprofFragmentView.findViewById(R.id.profileMaleFrag);
                profileFemale = myprofFragmentView.findViewById(R.id.profileFemaleFrag);

                stringName = profileName.getText().toString();
                stringAge = profileAge.getText().toString();
                stringCity = profileCity.getText().toString();
                stringCountry = profileCountry.getText().toString();
                doubleWeight = (double)seekBarWeight.getProgress();
                doubleHeight = (double)seekBarHeight.getProgress();

                intGender = (profileMale.isSelected()) ? 1 : 0;  // assign 1 for male, 0 for female

                if(stringName.isEmpty() || stringAge.isEmpty() || stringCity.isEmpty() || stringCountry.isEmpty()) {

                    Toast.makeText(getActivity(), "Please fill out all fields!", Toast.LENGTH_SHORT).show();

                } else {

                    intAge = Integer.parseInt(stringAge);
                    userViewModel.setProfileViewModelData(stringName, intAge, stringCity, stringCountry, doubleHeight, doubleWeight, intGender, profilePhotoFileName, byteArrSize,0.0, 0.0, false);
                    Toast.makeText(getActivity(), "User information saved!", Toast.LENGTH_SHORT).show();

                }

                break;

            }
            case R.id.prof_update_photo_frag:
            {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);

                break;
            }
            case R.id.lifeBtnMyProfFrag:
            {
                lifePressListener.onLifeBtnPress();
                break;
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (resultCode == RESULT_OK) {
                profilePicture = (Bitmap) data.getExtras().get("data");


                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                profilePicture.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                byteArrSize = byteArray.length;
                profilePicture.recycle();
                profilePhotoFileName = "profilephoto.bmp";
                try(FileOutputStream fos = getContext().openFileOutput(profilePhotoFileName, Context.MODE_PRIVATE)){
                    fos.write(byteArray);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Toast.makeText(getActivity(), "File Written", Toast.LENGTH_LONG).show();
                FileInputStream fis = null;
                try {
                    fis = getContext().openFileInput(profilePhotoFileName);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                byte[] readBytes = new byte[byteArrSize];
                try {
                    fis.read(readBytes);
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Bitmap fromFileBmp = BitmapFactory.decodeByteArray(readBytes,0,readBytes.length);
                profilePhotoView.setImageBitmap(fromFileBmp);



            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_LONG).show();
            }
        }

    }

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


}