package com.lifestyleapp

import android.graphics.Bitmap

var defaultUser = User("", 0, "", "", 0.0, 0.0, 1, null, 0.0, 0.0, false);

class User(fullName: String, age: Int, city: String, country: String, height: Double, weight: Double, gender: Int, profilePhoto: Bitmap?, bmi: Double, bmr: Double, sedentary: Boolean)
{
    var fullName: String;

    var firstName: String;

    var lastName: String;

    var age: Int;

    var city: String;

    var country: String;

    var height: Double;

    var weight: Double;

    // 1 male, 0 female;
    var gender: Int;

    var profilePhoto: Bitmap?;

    var bmi: Double;

    var bmrtee: Double;

    var sedentary: Boolean;

    init
    {
        this.fullName = fullName;

        this.firstName = nameSplitter(fullName, 0);

        this.lastName = nameSplitter(fullName, 1);

        this.age = age;

        this.city = city;

        this.country = country;

        this.height = height;

        this.weight = weight;

        this.gender = gender;

        this.profilePhoto = profilePhoto;

        this.bmi = bmi;

        this.bmrtee = bmr;

        this.sedentary = sedentary;
    }

    fun nameSplitter(fullName: String, position: Int): String
    {
        if(fullName.isEmpty())
        {
            return "";
        }

        val parts = fullName.split(" ");

        return parts[position];
    }

    fun manualSetBMI(BMI: Double)
    {
        this.bmi = BMI;
    }
}