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
import static androidx.test.espresso.matcher.ViewMatchers.*;

@RunWith(AndroidJUnit4.class)
public class ProfilePageFragmentTests {

    public static ViewAction setProgress(final int progress) {
        return new ViewAction() {
            @Override
            public void perform(UiController uiController, View view) {
                SeekBar seekBar = (SeekBar) view;
                seekBar.setProgress(progress);
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

    @Test
    public void nameEntry(){
        //move to profile button and click
        String name = "Sam Bauter";
        onView(withId(R.id.my_prof_btn_frag)).perform(click());
        onView(withId(R.id.profileNameFrag)).perform(typeText(name));

    }

    @Test
    public void ageEntry(){
        //move to profile button and click
        String age = "31";
        onView(withId(R.id.my_prof_btn_frag)).perform(click());
        onView(withId(R.id.profileAgeFrag)).perform(typeText(age));

    }

    @Test
    public void cityEntry(){
        //move to profile button and click
        String city = "Denver";
        onView(withId(R.id.my_prof_btn_frag)).perform(click());
        onView(withId(R.id.profileCityFrag)).perform(typeText(city));

    }

    @Test
    public void countryEntry(){
        //move to profile button and click
        String country = "usa";
        onView(withId(R.id.my_prof_btn_frag)).perform(click());
        onView(withId(R.id.profileCountryFrag)).perform(typeText(country));
    }




}