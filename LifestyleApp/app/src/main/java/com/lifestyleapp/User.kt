package com.lifestyleapp

import android.graphics.Bitmap

class User(fullName: String, age: Int, city: String, country: String, height: Double, weight: Double, gender: String, profilePhoto: Bitmap) {
    val firstName: String;

    val lastName: String;

    val age: Int;

    val city: String;

    val country: String;

    val height: Double;

    val weight: Double;

    val gender: String;

    val profilePhoto: Bitmap;

    init
    {
        this.firstName = nameSplitter(fullName, 0);

        this.lastName = nameSplitter(fullName, 1);  

        this.age = age;

        this.city = city;

        this.country = country;

        this.height = height;

        this.weight = weight;

        this.gender = gender;

        this.profilePhoto = profilePhoto;
    }

    fun nameSplitter(fullName: String, position: Int): String
    {
        val parts = fullName.split(" ");

        return parts[position];
    }
}