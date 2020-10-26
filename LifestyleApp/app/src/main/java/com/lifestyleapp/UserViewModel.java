package com.lifestyleapp;

import android.app.Application;

import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class UserViewModel extends AndroidViewModel {

    private UserRepository profilePageRepository;
    private LiveData<List<User>> mUsers;
    private String activeUserFullName;


    public UserViewModel(Application application) {

        super(application);

        profilePageRepository = UserRepository.getInstance(application);

        mUsers = profilePageRepository.getUsersData();

    }

    // FORWARD ALL OF THE DATA TO THE REPOSITORY
    public void setProfileViewModelData(String fullName, int age, String city, String country, double height, double weight, int gender, @Nullable String profilePhotoFileName, @Nullable int profilePhotoSize, @Nullable Integer steps, double bmi, double bmr, boolean sedentary){
        profilePageRepository.setUserData(fullName, age, city, country, height, weight, gender, profilePhotoFileName, profilePhotoSize, steps, bmi, bmr, sedentary);
    }

    // RETRIEVE DATA FROM THE REPOSITORY
    public LiveData<List<User>> getProfileViewModelData() {
        return mUsers;
    }

    public void setActiveUserFullName(String fullName){
        this.activeUserFullName = fullName;
        //mUsers.setValue(mUsers.getValue());
    }

    public String getActiveUserFullName(){
        return this.activeUserFullName;
    }

    public User getActiveUser() {
        List<User> userList = mUsers.getValue();
        if (activeUserFullName != null) {
            return userList.stream()
                    .filter(user ->
                            activeUserFullName.equals(user.getFullName()))
                    .findAny()
                    .orElse(null);
        }else{
            return null;
        }
    }

}

