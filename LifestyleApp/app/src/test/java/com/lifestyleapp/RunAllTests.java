package com.lifestyleapp;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses(
        {
                CalculatorsTest.class,
                WeatherUtilitiesTests.class
        }
)

public class RunAllTests
{

}
