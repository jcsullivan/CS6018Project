package com.lifestyleapp;

import android.app.Application;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class UserRepository {

    private UserDao mUserDao;
    private LiveData<List<User>> mUsers;

    // Singleton pattern setup
    // create an object of the class
    private static UserRepository instance;

    // make constructor private so the class can't be instantiated
    private UserRepository(Application application) {
        UserRoomDatabase db = UserRoomDatabase.getDatabase(application);
        mUserDao = db.userDao();
        mUsers = mUserDao.getUsers();
    };
    // get the only repository in existence
    public static UserRepository getInstance(Application application) {
        if (instance == null)
        {
            instance = new UserRepository(application);
        }
        return instance; }


    public LiveData<List<User>> getUsersData() { return mUsers; }

    public void setUserData (String fullName, int age, String city, String country, double height, double weight, int gender, @Nullable String profilePhotoFileName, @Nullable int profilePhotoSize, @Nullable Integer steps, double bmi, double bmr, boolean sedentary) {
        User userData = new User(fullName, age, city, country, height, weight, gender, profilePhotoFileName, profilePhotoSize, steps);
        //currentUserLiveData.setValue(userData);
        UserRoomDatabase.databaseWriteExecutor.execute(() -> {
                mUserDao.insert(userData);
            });
        }
    }


