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
        fun caloriesToEat(bmr: Double, gender: Double, desiredChange: Double, sedentary: Boolean): String
        {

            var totalEnergyExpenditure = bmr;

            if(!sedentary) totalEnergyExpenditure *= 1.73;
            else totalEnergyExpenditure *= 1.53;

            // To reduce 1 kg (2.205 lbs) of weight, about 7000 kcal deficit is required.
            var dailyCalories = (1000.0 / (desiredChange * 2.205)) + totalEnergyExpenditure;
            var caloriesString = "%.0f".format(dailyCalories);

            var warning = "";

            if(gender == 1.0 && dailyCalories < 1200)
            {
                warning = " (WARNING: low caloric intake)"
            }
            else if(dailyCalories < 1000)
            {
                warning = " (WARNING: low caloric intake)"
            }

            return caloriesString + warning;

        }
    }

}