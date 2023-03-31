package com.example.calendarapplication

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Assert.*
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4ClassRunner::class)
class NavigationTest {

    @Test
    fun test1_main_to_today() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.enterButton)).perform(click())
        onView(withId(R.id.today_screen_layout)).check(matches(isDisplayed()))
    }

    @Test
    fun test2_today_to_calendar() {
        val activityScenario = ActivityScenario.launch(TodayActivity::class.java)
        onView(withId(R.id.today_to_calendar_button)).perform(click())
        onView(withId(R.id.calendar_screen_layout)).check(matches(isDisplayed()))
    }

    @Test
    fun test3_calendar_to_today() {
        val activityScenario = ActivityScenario.launch(CalendarActivity::class.java)
        onView(withId(R.id.calendar_back_to_schedule_button)).perform(click())
        onView(withId(R.id.today_screen_layout)).check(matches(isDisplayed()))
    }

    @Test
    fun test4_calendar_to_day() {
        val activityScenario = ActivityScenario.launch(CalendarActivity::class.java)
        onView(withId(R.id.schedule_for_this_day)).perform(click())
        onView(withId(R.id.day_schedule_screen_layout)).check(matches(isDisplayed()))
    }


    @Test
    fun test5_day_to_calendar() {
        val intent = Intent(ApplicationProvider.getApplicationContext(),
            DayScheduleActivity::class.java)
        val testDate = "2023-01-17"
        intent.putExtra("EXTRA_DATE", testDate)
        val activityScenario = ActivityScenario.launch<DayScheduleActivity>(intent)
        //onView(withId(R.id.selected_date)).check(matches(withText(test_date)))
        onView(withId(R.id.day_schedule_to_calendar_button)).perform(click())
        onView(withId(R.id.calendar_screen_layout)).check(matches(isDisplayed()))
    }

    @Test
    fun test6_day_to_new_task() {
        val intent = Intent(ApplicationProvider.getApplicationContext(),
            DayScheduleActivity::class.java)
        val testDate = "2023-01-17"
        intent.putExtra("EXTRA_DATE", testDate)
        val activityScenario = ActivityScenario.launch<DayScheduleActivity>(intent)
        onView(withId(R.id.add_task_button)).perform(click())
        onView(withId(R.id.new_task_screen_layout)).check(matches(isDisplayed()))
    }

    @Test
    fun test7_new_task_to_day_as_cancel() {
        val intent = Intent(ApplicationProvider.getApplicationContext(),
            NewTaskActivity::class.java)
        val testDate = "2023-01-17"
        intent.putExtra("EXTRA_DATE", testDate)
        val activityScenario = ActivityScenario.launch<NewTaskActivity>(intent)
        onView(withId(R.id.cancel_button)).perform(click())
        onView(withId(R.id.day_schedule_screen_layout)).check(matches(isDisplayed()))
    }

    @Test
    fun test8_new_task_to_day_as_save() {
        val intent = Intent(ApplicationProvider.getApplicationContext(),
            NewTaskActivity::class.java)
        val testDate = "2023-01-17"
        intent.putExtra("EXTRA_DATE", testDate)
        val activityScenario = ActivityScenario.launch<NewTaskActivity>(intent)
        onView(withId(R.id.taskname)).perform(typeText("testName"))
        onView(withId(R.id.taskfrom)).perform(typeText("12:00"))
        onView(withId(R.id.taskto)).perform(typeText("12:01"))
        onView(withId(R.id.taskdesc)).perform(typeText("testDesc"))
        closeSoftKeyboard()
        onView(withId(R.id.save_button)).perform(click())
        onView(withId(R.id.day_schedule_screen_layout)).check(matches(isDisplayed()))
    }

}







