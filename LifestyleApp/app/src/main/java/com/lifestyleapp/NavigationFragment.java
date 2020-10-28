package com.lifestyleapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.amplifyframework.core.Amplify;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class NavigationFragment extends Fragment implements View.OnClickListener {

    View navFragmentView;

    OnNavSelectedListener listener;
    Button profileButton;
    Button weightManButton;
    Button hikesButton;
    Button weatherButton;
    Button stepsButton;
    FloatingActionButton exitButton;
    ImageView profilePhotoView;
    User localUser;
    public final int PROFILE_BUTTON_INDEX = 1;
    public final int WEIGHT_BUTTON_INDEX = 2;
    public final int WEATHER_BUTTON_INDEX = 3;
    public final int STEPS_BUTTON_INDEX=4;

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

    public NavigationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


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
        stepsButton = navFragmentView.findViewById(R.id.steps_btn_frag);
        profilePhotoView = navFragmentView.findViewById(R.id.photo_nav_pane_frag);
        exitButton = navFragmentView.findViewById(R.id.exit_button);

        profileButton.setOnClickListener(this);
        weightManButton.setOnClickListener(this);
        hikesButton.setOnClickListener(this);
        weatherButton.setOnClickListener(this);
        stepsButton.setOnClickListener(this);
        exitButton.setOnClickListener(this);

        userViewModel = ViewModelProviders.of(getActivity()).get(UserViewModel.class);
        LiveData<List<User>> usersList = userViewModel.getProfileViewModelData();
        usersList.observe(getActivity(),new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable final List<User> userList) {
                if(!userList.isEmpty()&&userViewModel.getActiveUserFullName()!=null) {
                    localUser = userList.stream()
                            .filter(user ->
                                    userViewModel.getActiveUserFullName().equals(user.getFullName()))
                            .findAny()
                            .orElse(null);
                }
            }
        });

        if (localUser != null) {

            if (localUser.getProfilePhotoPath() != null) {
                String profPhotoFileName = localUser.getProfilePhotoPath();

                FileInputStream fis = null;
                try {
                    fis = getContext().openFileInput(profPhotoFileName);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                byte[] readBytes = new byte[localUser.getProfilePhotoSize()];
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

    private void uploadFile(String fileKey, File toUpload) {
        Amplify.Storage.uploadFile(
                fileKey,
                toUpload,
                result -> Log.i("MyAmplifyApp", "Successfully uploaded: " + result.getKey()),
                storageFailure -> Log.e("MyAmplifyApp", "Upload failed", storageFailure)
        );
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
                String city = "Salt Lake City";
                if(localUser!=null){
                    city = localUser.getCity();
                }


                Uri gmmIntentUri = Uri.parse("geo:0,0?q=hiking" + " " + Uri.encode(city));
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
            case R.id.steps_btn_frag:
            {
                listener.onNavSelected(STEPS_BUTTON_INDEX);
                break;
            }
            case R.id.exit_button: {
                File db_path = getActivity().getDatabasePath("user_database");
                UserRoomDatabase.closeDB();
                uploadFile("lifestyle_app_db",db_path);
                Toast.makeText(getActivity(), "Exiting program.....", Toast.LENGTH_LONG).show();
                try{
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
                getActivity().finish();
                System.exit(0);

            }

        }
    }
}