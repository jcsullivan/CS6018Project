package com.lifestyleapp;

import android.view.View;
import android.widget.SeekBar;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.containsString;

@RunWith(AndroidJUnit4.class)

public class WeatherFragmentTests {

    @Rule
    public ActivityScenarioRule<MasterDetail> masterActRule = new ActivityScenarioRule<>(MasterDetail.class);

    @Test
    public void weatherFieldCheck(){
        //move to profile button and click
        String name = "Denver, usa";
        onView(withId(R.id.weather_btn_frag)).perform(click());
        onView(withId(R.id.location)).perform(typeText(name));
        onView(withId(R.id.resetLocation)).perform(click());
        onView(withId(R.id.weatherInfo)).check(matches(withText(containsString("Current Conditions"))));

    }
}