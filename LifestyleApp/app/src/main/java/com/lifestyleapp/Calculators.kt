//@file:JvmName("Calcs")

package com.example.lifestyleapp

// Calling Kotlin from Java:  https://kotlinlang.org/docs/reference/java-to-kotlin-interop.html 
class Calculators
{
    fun BMI(height: Int, weight: Int): Int
    {
        // Equation from https://en.wikipedia.org/wiki/Body_mass_index
        val bmi = (703 * weight) / (height * height);

        return bmi;
    }

    fun BMRTEE(gender: String, height: Int, weight: Int, age: Int, active: Boolean): Double
    {
        // Converting weight to kilograms.
        var kgWeight = weight / 2.205;
        // Converting height to centimeters.
        var cmHeight = height * 2.54;

        // Equations from https://en.wikipedia.org/wiki/Harris%E2%80%93Benedict_equation
        var genderlessBMR = (10 * kgWeight) + (6.25 * cmHeight) - (5 * age);

        var bmr = 0.0;

        if (gender == "Male")
        {
            bmr = genderlessBMR + 5;
        }
        else
        {
            bmr = genderlessBMR - 161;
        }

        var tee = 0.0;

        if(active)
        {
            tee = bmr * 1.76;
        }
        else
        {
            tee = bmr * 1.53;
        }

        return tee;
    }

    fun caloriesToEat(desiredChange: Double, tee: Double): Double
    {
        // To reduce 1 kg (2.205 lbs) of weight, about 7000 kcal deficit is required.
        var dailyCalories = (1000 / (desiredChange / 2.205)) + tee;

        return dailyCalories;
    }
}