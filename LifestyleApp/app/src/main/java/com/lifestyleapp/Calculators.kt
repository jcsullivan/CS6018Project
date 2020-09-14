//@file:JvmName("Calcs")

package com.example.lifestyleapp

import com.lifestyleapp.defaultUser

// Calling Kotlin from Java:  https://kotlinlang.org/docs/reference/java-to-kotlin-interop.html 
class Calculators
{
    fun BMI(): Double
    {
        // Equation from https://en.wikipedia.org/wiki/Body_mass_index
        val bmi = (703 * defaultUser.weight) / (defaultUser.height * defaultUser.height);

        return bmi;
    }

    fun BMRTEE(): Double
    {
        // Converting weight to kilograms.
        var kgWeight = defaultUser.weight / 2.205;
        // Converting height to centimeters.
        var cmHeight = defaultUser.height * 2.54;

        // Equations from https://en.wikipedia.org/wiki/Harris%E2%80%93Benedict_equation
        var genderlessBMR = (10 * kgWeight) + (6.25 * cmHeight) - (5 * defaultUser.age);

        var bmr = 0.0;

        if (defaultUser.gender == 1)
        {
            bmr = genderlessBMR + 5;
        }
        else
        {
            bmr = genderlessBMR - 161;
        }

        var tee = 0.0;

        if(!defaultUser.sedentary)
        {
            tee = bmr * 1.76;
        }
        else
        {
            tee = bmr * 1.53;
        }

        return tee;
    }

    fun caloriesToEat(desiredChange: Double, tee: Double): String
    {
        // To reduce 1 kg (2.205 lbs) of weight, about 7000 kcal deficit is required.
        var dailyCalories = (1000 / (desiredChange / 2.205)) + tee;

        var warning = "";

        var gender = defaultUser.gender;

        if(gender == 1 && dailyCalories < 1200)
        {
            warning = " (WARNING: low caloric intake)"
        }
        else if(dailyCalories < 1000)
        {
            warning = " (WARNING: low caloric intake)"
        }

        return dailyCalories.toBigDecimal().toPlainString() + warning;
    }
}