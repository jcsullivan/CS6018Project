package com.lifestyleapp;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.containsString;

@RunWith(AndroidJUnit4.class)

public class WeatherFragmentTests {

    @Rule
    public ActivityScenarioRule<MasterDetail> masterActRule = new ActivityScenarioRule<>(MasterDetail.class);

    @Test
    public void weatherFieldCheck(){
        String name = "Denver, usa";
        onView(withId(R.id.weather_btn_frag)).perform(click());
        onView(withId(R.id.location)).perform(typeText(name));
        onView(withId(R.id.resetLocation)).perform(click());
        onView(withId(R.id.weatherInfo)).check(matches(withText(containsString("Current Conditions"))));

    }
}