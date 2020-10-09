package com.lifestyleapp

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

// TODO DELETE ALL INSTANCES OF DEFAULT USER
var defaultUser = User("", 0, "", "", 0.0, 0.0, 1, null, 0.0, 0.0, false);

@Entity(tableName = "user_table")
class User constructor(fullName: String, age: Int, city: String, country: String, height: Double, weight: Double, gender: Int, profilePhotoPath: String?, bmi: Double, bmrtee: Double, sedentary: Boolean)
{

    @PrimaryKey
    @NonNull
    var fullName: String;  // fullName is the primary key for the user table, can never be null
    var age: Int;
    var city: String;
    var country: String;
    var height: Double;
    var weight: Double;
    var gender: Int;      // 1 male, 0 female;
    var profilePhotoPath: String?;
    var bmi: Double;
    var bmrtee: Double;
    var sedentary: Boolean;

    init {
        this.fullName = fullName;
        this.age = age;
        this.city = city;
        this.country = country;
        this.height = height;
        this.weight = weight;
        this.gender = gender;
        this.profilePhotoPath = profilePhotoPath;
        this.bmi = bmi;
        this.bmrtee = bmrtee;
        this.sedentary = sedentary;
    }

    fun manualSetBMI(BMI: Double)
    {
        this.bmi = BMI;
    }

}