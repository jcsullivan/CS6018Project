package com.lifestyleapp;

import android.graphics.Bitmap;
import androidx.lifecycle.MutableLiveData;

public class ProfilePageRepository {

    // Singleton pattern setup
    // create an object of the class
    private static ProfilePageRepository instance = new ProfilePageRepository();
    // make constructor private so the class can't be instantiated
    private ProfilePageRepository() {};
    // get the only repository in existence
    public static ProfilePageRepository getInstance() { return instance; }

    private final MutableLiveData<User> currentUserLiveData = new MutableLiveData<>();

    public MutableLiveData<User> getUserData() { return currentUserLiveData; }

    public void setUserData (String fullName, int age, String city, String country, double height, double weight, int gender, String photoPath, double bmi, double bmr, boolean sedentary) {
        User userData = new User(fullName, age, city, country, height, weight, gender, photoPath, bmi, bmr, sedentary);
        currentUserLiveData.setValue(userData);
    }

}
