package com.lifestyleapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class NavPaneFragment extends Fragment implements View.OnClickListener {



    OnNavSelectedListener listener;
    Button profileButton;
    Button weightManButton;
    Button hikesButton;
    Button weatherButton;
    public final int PROFILE_BUTTON_INDEX =1;
    public final int WEIGHT_BUTTON_INDEX =2;
    //int HIKE_BUTTON_INDEX =3;
    //int WEATHER_BUTTON_INDEX =4;




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
         View navFragmentView = inflater.inflate(R.layout.fragment_nav_pane, container, false);
         profileButton = navFragmentView.findViewById(R.id.my_prof_btn_frag);
         weightManButton = navFragmentView.findViewById(R.id.weight_man_btn_frag);
         hikesButton = navFragmentView.findViewById(R.id.hike_btn_frag);
         weatherButton = navFragmentView.findViewById(R.id.weather_btn_frag);

        profileButton.setOnClickListener(this);
        weightManButton.setOnClickListener(this);
        hikesButton.setOnClickListener(this);
        weatherButton.setOnClickListener(this);

        return navFragmentView;
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
                Uri uri = Uri.parse("http://www.google.com/search?q=local weather");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
            break;


        }
    }
}