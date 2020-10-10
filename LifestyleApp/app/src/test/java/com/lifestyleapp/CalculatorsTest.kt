package com.lifestyleapp

import com.example.lifestyleapp.Calculators
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*


class CalculatorsTest {

    @Before
    fun setUp() {
        //defaultUser = User("Sam Bauter", 31, "Denver", "USA", 72.0, 220.0, 1, null, 0.0, 0.0, false)

    }

    @Test
    fun BMI() {
        assertEquals(Calculators.BMI(220.0, 72.0), "29.8341");

    }

    @Test
    fun BMRTEE() {
        assertEquals(Calculators.BMRTEE(220.0, 72.0, 31.0, 1.0, false), "3503.6891");
    }

    @Test
    fun caloriesToEat() {
        assertEquals(Calculators.caloriesToEat(3500.0, 1.0, 1.0), "3953.5147");
    }

}
