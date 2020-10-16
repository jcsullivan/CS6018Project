package com.lifestyleapp

import org.junit.Assert.assertEquals
import org.junit.Test

class CalculatorsTest {

    @Test
    fun BMI() {
        assertEquals(Calculators.calculateBMI(220.0, 72.0), 29.834104938271604, 0.1);

    }

    @Test
    fun BMRTEE() {
        assertEquals(Calculators.calculateBMR(220.0, 72.0, 31.0, 1.0), 1990.732426303855, 0.1);
    }

    @Test
    fun rawCaloriesToEat() {
        assertEquals(Calculators.calculateCaloriesToEat(1990.0, 1.0, false), 3942.7, 0.1);
    }

    @Test
    fun maleWarningCaloriesToEat()
    {
        assertEquals(Calculators.calculateCaloriesToEat(1200.0, -2.0, false), 1076.0, 0.1);
    }

    @Test
    fun femaleWarningCaloriesToEat()
    {
        assertEquals(Calculators.calculateCaloriesToEat(1000.0, -2.0, false), 730.0, 0.1);
    }

}
