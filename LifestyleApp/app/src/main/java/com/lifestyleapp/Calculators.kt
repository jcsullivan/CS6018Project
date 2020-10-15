package com.lifestyleapp

import com.google.android.material.snackbar.Snackbar

// Calling Kotlin from Java:  https://kotlinlang.org/docs/reference/java-to-kotlin-interop.html 
class Calculators
{
    companion object
    {
        @JvmStatic
        fun calculateBMI(weight: Double, height: Double): Double
        {
            // Equation from https://en.wikipedia.org/wiki/Body_mass_index
            val bmi = (703 * weight) / (height * height);
            return bmi;
        }

        @JvmStatic
        fun calculateBMR(pounds: Double, inches: Double, age: Double, gender: Double): Double
        {

            // Equations from https://en.wikipedia.org/wiki/Harris%E2%80%93Benedict_equation
            var kgWeight = pounds / 2.205;
            var cmHeight = inches * 2.54;

            // gender neutral bmr
            var bmr = (10 * kgWeight) + (6.25 * cmHeight) - (5 * age);

            if (gender == 1.0) bmr += 5; // for men
            else bmr -= 161;  // for women

            return bmr;

        }

        @JvmStatic
        fun rawCalculateCaloriesToEat(bmr: Double, changeInPounds: Double, sedentary: Boolean): Double
        {

            var totalEnergyExpenditure =
                    if(!sedentary) bmr * 1.73;
                    else bmr * 1.53;

            // To reduce 1 lb of weight per week, about (1000 kcal / 7 days = 500 kcal / day) deficit is required.
            var dailyCalories = (500 * changeInPounds) + totalEnergyExpenditure;

            return dailyCalories;

        }

        @JvmStatic
        fun calculateCaloriesToEat(bmr: Double, changeInPounds: Double, sedentary: Boolean, gender: Double): String
        {
            var calories = rawCalculateCaloriesToEat(bmr, changeInPounds, sedentary);
            var response = "%.1f".format(calories);

            if (gender == 1.0 && calories < 1200 || calories < 1000) {

                response = response + " (WARNING)"
            }

            return response;
        }
    }
}