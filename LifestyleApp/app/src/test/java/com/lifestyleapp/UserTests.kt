package com.lifestyleapp

import org.junit.*
import org.junit.Assert.*


class UserTests {

    @Before
    fun setup(){
        defaultUser = User("Sam Bauter", 31, "Denver", "USA", 72.0, 220.0, 1, null, 0.0, 0.0, false)

    }

    @Test
    fun firstNameSplitTest() {
        assertEquals(defaultUser.nameSplitter(defaultUser.fullName,0),"Sam")
    }

    @Test
    fun lastNameSplitTest() {
        assertEquals(defaultUser.nameSplitter(defaultUser.fullName,1),"Bauter")

    }

    @Test
    fun manualSetBMI() {
        defaultUser.manualSetBMI(20.0);
        assertEquals(defaultUser.bmi,20.0,.1);
    }

    @After
    fun teardown(){
        defaultUser.manualSetBMI(0.0);
    }

}