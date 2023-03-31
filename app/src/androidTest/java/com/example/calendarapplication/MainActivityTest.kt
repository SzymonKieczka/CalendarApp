package com.example.calendarapplication

import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Assert.*
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {

    @get: Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test1_is_activity_in_view() {
        //val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.main_screen_layout)).check(matches(isDisplayed()))
    }

    @Test
    fun test2_are_components_visible() {
        onView(withId(R.id.main_screen_title)).check(matches(isDisplayed()))
        onView(withId(R.id.main_screen_icon)).check(matches(isDisplayed()))
        onView(withId(R.id.enterButton)).check(matches(isDisplayed()))
    }

    @Test
    fun test3_are_component_texts_displayed() {
        onView(withId(R.id.main_screen_title)).check(matches(withText(R.string.calendar)))
        onView(withId(R.id.enterButton)).check(matches(withText(R.string.my_schedule)))
    }

    @Test
    fun test4_are_components_of_proper_types() {
        onView(withId(R.id.main_screen_title))
            .check(matches(instanceOf(TextView::class.java)))
        onView(withId(R.id.main_screen_icon))
            .check(matches(instanceOf(ImageView::class.java)))
        onView(withId(R.id.enterButton))
            .check(matches(instanceOf(Button::class.java)))
    }
}

