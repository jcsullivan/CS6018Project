package com.lifestyleapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


public class NavPaneFragment extends Fragment implements View.OnClickListener {

    View navFragmentView;

    OnNavSelectedListener listener;
    Button profileButton;
    Button weightManButton;
    Button hikesButton;
    Button weatherButton;
    ImageView profilePhotoView;
    public final int PROFILE_BUTTON_INDEX =1;
    public final int WEIGHT_BUTTON_INDEX =2;
    public final int WEATHER_BUTTON_INDEX=3;

    private UserViewModel userViewModel;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (OnNavSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnNavSelectedListener");
        }
    }

    public interface OnNavSelectedListener {
        public void onNavSelected(int navIndex);
    }


    public NavPaneFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //if (getArguments() != null) {
            //mParam1 = getArguments().getString(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        navFragmentView = inflater.inflate(R.layout.fragment_nav_pane, container, false);
        profileButton = navFragmentView.findViewById(R.id.my_prof_btn_frag);
        weightManButton = navFragmentView.findViewById(R.id.weight_man_btn_frag);
        hikesButton = navFragmentView.findViewById(R.id.hike_btn_frag);
        weatherButton = navFragmentView.findViewById(R.id.weather_btn_frag);
        profilePhotoView = navFragmentView.findViewById(R.id.photo_nav_pane_frag);

        profileButton.setOnClickListener(this);
        weightManButton.setOnClickListener(this);
        hikesButton.setOnClickListener(this);
        weatherButton.setOnClickListener(this);

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
                byte[] readBytes = new byte[user.getProfilePhotoSize()];
                try {
                    fis.read(readBytes);
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Bitmap fromFileBmp = BitmapFactory.decodeByteArray(readBytes, 0, readBytes.length);
                profilePhotoView.setImageBitmap(fromFileBmp);
            }
        }
        return navFragmentView;
    }

    @Override
    public void onStart()
    {
        super.onStart();

        profilePhotoView = navFragmentView.findViewById(R.id.photo_nav_pane_frag);

    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.my_prof_btn_frag: {
                //Intent myProfIntent = new Intent(this, ProfileActivity.class);
                //this.startActivity(myProfIntent);
                listener.onNavSelected(PROFILE_BUTTON_INDEX);
            }
            break;
            case R.id.weight_man_btn_frag: {
                //Intent weightManIntent = new Intent(this, WeightManagementActivity.class);
                //this.startActivity(weightManIntent);
                listener.onNavSelected(WEIGHT_BUTTON_INDEX);
            }
            break;
            case R.id.hike_btn_frag: {
                // Search for nearby hikes on Google Maps
                // https://developers.google.com/maps/documentation/urls/android-intents#java_6
                // The level of Zoom can be adjusted - click on the link above for code
                Uri gmmIntentUri = Uri.parse("geo:0,0?q=hiking");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);

            }
            break;
            case R.id.weather_btn_frag: {
                // Search for local weather using the browser
                // https://www.youtube.com/watch?v=mMKC_gRSL5Q
                /*Uri uri = Uri.parse("http://www.google.com/search?q=local weather");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                 */
                listener.onNavSelected(WEATHER_BUTTON_INDEX);
            }
            break;


        }
    }
}