package com.example.nowmoneytask.ui;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import com.example.nowmoneytask.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4ClassRunner.class)
public class LoginFragmentTest {


    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    private IdlingResource mIdlingResource;


    @Before
    public void registerIdlingResource() {
        activityTestRule.getActivity()
                .getFragmentManager().beginTransaction();

        mIdlingResource = activityTestRule.getActivity().getIdlingResource();


        IdlingRegistry.getInstance().register(mIdlingResource);
    }


    @After
    public void unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(mIdlingResource);
    }

    @Test
    public void test_Title_visibility() {
        onView(withId(R.id.loginTitle)).check(matches(withText("Login")));

    }


    @Test
    public void test_LoginSuccess() {


        onView(withId(R.id.usernameEd)).perform(typeText("ahmed"));
        onView(withId(R.id.passwordEd)).perform(typeText("AhmeD123"));

        onView(withId(R.id.signInBtn)).perform(click());

        onView(withId(R.id.homeReceiver)).check(matches(isDisplayed()));


    }

    @Test
    public void test_LoginFail() {

        onView(withId(R.id.usernameEd)).perform(typeText("Ahmed"));
        onView(withId(R.id.passwordEd)).perform(typeText("AhmeD123"));

        onView(withId(R.id.signInBtn)).perform(click());


    }


}