package com.example.lifestyleapp

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
        fun caloriesToEat(bmr: Double, changeInPounds: Double, sedentary: Boolean): Double
        {

            var totalEnergyExpenditure =
                    if(!sedentary) bmr * 1.73;
                    else bmr * 1.53;

            // To reduce 1 lb of weight per week, about (3500 kcal / 7 days = 500 kcal / day) deficit is required.
            var dailyCalories = (500 * changeInPounds) + totalEnergyExpenditure;

            return dailyCalories;

        }
    }
}