package com.lifestyleapp;


import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static androidx.test.espresso.assertion.ViewAssertions.*;

import static org.hamcrest.Matchers.*;
import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;


import java.util.List;


@RunWith(AndroidJUnit4.class)
public class NavPaneClickTests {
    @Rule
    public ActivityScenarioRule<MasterDetail> masterActRule = new ActivityScenarioRule<>(MasterDetail.class);


    @Test
    public void clickMyProf(){
        ViewInteraction moveToMyProfBtn = onView(withId(R.id.my_prof_btn_frag));
        moveToMyProfBtn.perform(click());
        onView(withId(R.id.title_my_prof_frag)).check(matches(withText(containsString("MY PROFILE"))));
    }

    @Test
    public void clickWeightMan(){
        ViewInteraction moveToWeightManBtn = onView(withId(R.id.weight_man_btn_frag));
        moveToWeightManBtn.perform(click());
        onView(withId(R.id.title_weight_man_frag)).check(matches(withText(containsString("WEIGHT MANAGEMENT CALCULATOR"))));
    }

    @Test
    public void clickLocalWeather(){
        ViewInteraction moveToWeatherBtn = onView(withId(R.id.weather_btn_frag));
        moveToWeatherBtn.perform(click());
        onView(withId(R.id.resetLocation)).check(matches(withText(containsString("Set Location"))));
    }










}