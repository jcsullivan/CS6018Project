package com.lifestyleapp;

import android.view.View;
import android.widget.SeekBar;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.containsString;

@RunWith(AndroidJUnit4.class)
public class WeightManagementTests
{

    public static ViewAction setProgress(final int progress) {
        return new ViewAction() {
            @Override
            public void perform(UiController uiController, View view) {
                SeekBar seekBar = (SeekBar) view;
                seekBar.setProgress(progress, true);
            }
            @Override
            public String getDescription() {
                return "Set a progress on a SeekBar";
            }
            @Override
            public Matcher<View> getConstraints() {
                return ViewMatchers.isAssignableFrom(SeekBar.class);
            }
        };
    }

    @Rule
    public ActivityScenarioRule<MasterDetail> masterActRule = new ActivityScenarioRule<>(MasterDetail.class);

    @Before
    public void setup()
    {
        String name = "Jonathan Sullivan";
        int age = 25;
        String city = "Seattle";
        String country = "USA";
        int height = 54;
        int weight = 80;
        ViewInteraction moveToMyProfBtn = onView(withId(R.id.my_prof_btn_frag));
        moveToMyProfBtn.perform(click());

        onView(withId(R.id.profileNameFrag)).perform(typeText(name));
        onView(withId(R.id.profileAgeFrag)).perform(typeText(String.valueOf(age)));
        onView(withId(R.id.profileCityFrag)).perform(typeText(city));
        onView(withId(R.id.profileCountryFrag)).perform(scrollTo());
        onView(withId(R.id.profileCountryFrag)).perform(typeText(country));
        onView(withId(R.id.seekBarHeightFrag)).perform(scrollTo());
        onView(withId(R.id.seekBarHeightFrag)).perform(setProgress(height));
        onView(withId(R.id.seekBarWeightFrag)).perform(scrollTo());
        onView(withId(R.id.seekBarWeightFrag)).perform(setProgress(weight));
        onView(withId(R.id.profileMaleFrag)).perform(scrollTo());
        onView(withId(R.id.profileFemaleFrag)).perform(click());
        onView(withId(R.id.saveProfileFrag)).perform(scrollTo());
        onView(withId(R.id.saveProfileFrag)).perform(click());

        onView(withId(R.id.lifeBtnMyProfFrag)).perform(click());

        onView(withId(R.id.my_prof_btn_frag)).perform(click());

        onView(withId(R.id.profileNameFrag)).check(matches(withText(containsString(name))));
        onView(withId(R.id.profileAgeFrag)).check(matches(withText(containsString(String.valueOf(age)))));
        onView(withId(R.id.profileCityFrag)).check(matches(withText(containsString(city))));
        onView(withId(R.id.profileCountryFrag)).check(matches(withText(containsString(country))));
        onView(withId(R.id.textViewHeightFrag)).check(matches(withText(containsString(String.valueOf(height)))));
        onView(withId(R.id.textViewWeightFrag)).check(matches(withText(containsString(String.valueOf(weight)))));

        onView(withId(R.id.saveProfileFrag)).perform(scrollTo());
        onView(withId(R.id.lifeBtnMyProfFrag)).perform(click());
    }

    @Test
    public void desiredWeightChange(){
        //move to height slider and click
        int weight = -20;
        ViewInteraction moveToMyProfBtn = onView(withId(R.id.weight_man_btn_frag));
        moveToMyProfBtn.perform(click());
        onView(withId(R.id.calculatorPoundsPerWeekFrag)).perform(scrollTo());
        onView(withId(R.id.calculatorPoundsPerWeekFrag)).perform(setProgress(19));
        onView(withId(R.id.calculatorPoundsPerWeekFrag)).perform(setProgress(weight));

        onView(withId(R.id.dailyCalEditTextFrag)).perform(scrollTo());
        onView(withId(R.id.dailyCalEditTextFrag)).check(matches(withText(containsString("665 (WARNING)"))));
    }
}