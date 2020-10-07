package com.lifestyleapp;

import android.app.Application;
import android.graphics.Bitmap;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class ProfilePageViewModel extends AndroidViewModel {

    private MutableLiveData<User> userMutableLiveData;
    private ProfilePageRepository profilePageRepository;

    public ProfilePageViewModel (Application application) {

        super(application);

        profilePageRepository = ProfilePageRepository.getInstance();

        userMutableLiveData = profilePageRepository.getUserData();

    }

    // FORWARD ALL OF THE DATA TO THE REPOSITORY
    public void setProfileViewModelData(String fullName, int age, String city, String country, double height, double weight, int gender, String profilePhotoPath, double bmi, double bmr, boolean sedentary){
        profilePageRepository.setUserData(fullName, age, city, country, height, weight, gender, profilePhotoPath, bmi, bmr, sedentary);
    }

    // RETRIEVE DATA FROM THE REPOSITORY
    public LiveData<User> getProfileViewModelData() {
        return profilePageRepository.getUserData();
    }

}
