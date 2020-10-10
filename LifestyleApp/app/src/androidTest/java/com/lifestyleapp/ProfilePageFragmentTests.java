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
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.*;

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
        ViewInteraction moveToMyProfBtn = onView(withId(R.id.my_prof_btn_frag));
        moveToMyProfBtn.perform(click());
        onView(withId(R.id.profileNameFrag)).perform(typeText(name));
        onView(withId(R.id.profileNameFrag)).check(matches(withText(containsString(name))));
    }

    @Test
    public void ageEntry(){
        //move to profile button and click
        String age = "31";
        ViewInteraction moveToMyProfBtn = onView(withId(R.id.my_prof_btn_frag));
        moveToMyProfBtn.perform(click());
        onView(withId(R.id.profileAgeFrag)).perform(typeText(age));
        onView(withId(R.id.profileAgeFrag)).check(matches(withText(containsString(age))));
    }

    @Test
    public void cityEntry(){
        //move to profile button and click
        String city = "Denver";
        ViewInteraction moveToMyProfBtn = onView(withId(R.id.my_prof_btn_frag));
        moveToMyProfBtn.perform(click());
        onView(withId(R.id.profileCityFrag)).perform(typeText(city));
        onView(withId(R.id.profileCityFrag)).check(matches(withText(containsString(city))));
    }

    @Test
    public void countryEntry(){
        //move to profile button and click
        String country = "usa";
        ViewInteraction moveToMyProfBtn = onView(withId(R.id.my_prof_btn_frag));
        moveToMyProfBtn.perform(click());
        onView(withId(R.id.profileCountryFrag)).perform(typeText(country));
        onView(withId(R.id.profileCountryFrag)).check(matches(withText(containsString(country))));
    }

    @Test
    public void heightEntry(){
        //move to height slider and click
        int height = 74;
        ViewInteraction moveToMyProfBtn = onView(withId(R.id.my_prof_btn_frag));
        moveToMyProfBtn.perform(click());
        onView(withId(R.id.seekBarHeightFrag)).perform(scrollTo());
        onView(withId(R.id.seekBarHeightFrag)).perform(setProgress(height));
        onView(withId(R.id.textViewHeightFrag)).check(matches(withText(containsString(String.valueOf(height)))));
    }

    @Test
    public void weightEntry(){
        //move to weight slider and click
        int weight = 120;
        ViewInteraction moveToMyProfBtn = onView(withId(R.id.my_prof_btn_frag));
        moveToMyProfBtn.perform(click());
        onView(withId(R.id.seekBarWeightFrag)).perform(scrollTo());
        onView(withId(R.id.seekBarWeightFrag)).perform(setProgress(weight));
        onView(withId(R.id.textViewWeightFrag)).check(matches(withText(containsString(String.valueOf(weight)))));
    }

    @Test
    public void wholeShebang()
    {
        String name = "Jonathan Sullivan";
        int age = 38;
        String city = "Seattle";
        String country = "USA";
        int height = 74;
        int weight = 180;
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
        onView(withId(R.id.saveProfileFrag)).perform(scrollTo());
        onView(withId(R.id.saveProfileFrag)).perform(click());
        assertEquals(name, UserKt.getDefaultUser().getFullName());
        assertEquals(age, UserKt.getDefaultUser().getAge());
        assertEquals(city, UserKt.getDefaultUser().getCity());
        assertEquals(country, UserKt.getDefaultUser().getCountry());
        assertEquals(height, UserKt.getDefaultUser().getHeight(), 0.1);
        assertEquals(weight, UserKt.getDefaultUser().getWeight(), 0.1);
    }



}