package com.example.nowmoneytask.ui;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import com.example.nowmoneytask.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;


@RunWith(AndroidJUnit4ClassRunner.class)
public class ReceiversFragmentTest {


    @Rule
    public ActivityTestRule<HomeActivity> activityTestRule = new ActivityTestRule<>(HomeActivity.class);

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
    public void isHomeFragmentDisplayed(){


        onView(withId(R.id.homeReceiver)).check(matches(isDisplayed()));


        onView(withId(R.id.recyclerView)).perform(click());

        onView(withId(R.id.addDialogView)).check(matches(isDisplayed()));

        pressBack();

        onView(withId(R.id.homeReceiver)).check(matches(isDisplayed()));

        onView(withId(R.id.addReceiverBtn)).perform(click());

        onView(withId(R.id.addDialogView)).check(matches(isDisplayed()));

        pressBack();

        onView(withId(R.id.homeReceiver)).check(matches(isDisplayed()));


    }

    @Test
    public void test_RecyclerViewClick(){



        onView(withId(R.id.homeReceiver)).check(matches(isDisplayed()));

        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        onView(withId(R.id.addDialogView)).check(matches(isDisplayed()));

        pressBack();

        onView(withId(R.id.homeReceiver)).check(matches(isDisplayed()));

        onView(withId(R.id.addReceiverBtn)).perform(click());

        onView(withId(R.id.addDialogView)).check(matches(isDisplayed()));

        pressBack();

        onView(withId(R.id.homeReceiver)).check(matches(isDisplayed()));


    }


    @Test
    public void testAddReceiver() {

        onView(withId(R.id.addReceiverBtn)).perform(click());

        onView(withId(R.id.addDialogView)).check(matches(isDisplayed()));


        onView(withId(R.id.nameEd)).perform(typeText("Ali"));
        onView(withId(R.id.numberEd)).perform(typeText("7777"));
        onView(withId(R.id.addressEd)).perform(typeText("Nasr City"));


        onView(withId(R.id.addBtn)).perform(click());


        onView(withId(R.id.homeReceiver)).check(matches(isDisplayed()));


    }


    @Test
    public void test_DeleteBtn() {


        onView(withId(R.id.homeReceiver)).check(matches(isDisplayed()));

        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));

        onView(withId(R.id.addDialogView)).check(matches(isDisplayed()));


        onView(withId(R.id.deleteBtn)).perform(click());


        onView(withId(R.id.homeReceiver)).check(matches(isDisplayed()));


    }


}