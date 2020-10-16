package com.lifestyleapp;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses(
        {
                NavPaneClickTests.class,
                WeatherFragmentTests.class,
                WeightManagementTests.class,
                ProfilePageFragmentTests.class
        }
)

public class RunAllITests
{

}
