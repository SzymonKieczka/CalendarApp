package com.example.calendarapplication

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.PerformException
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.hamcrest.Matchers.*
import org.junit.Assert.*
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4ClassRunner::class)
class TaskCRUDTest {
    val testName = "testName"
    val testNewName = "testNewName"
    val testFrom = "12:00"
    val testTo = "12:01"
    val testDesc = "testDesc"
    val testNewDesc = "testNewDesc"
    val testDate = "2025-01-17"
    object MyTestException : Exception()

    @Test
    fun test1_task_creation() {
        val intent = Intent(ApplicationProvider.getApplicationContext(),
            NewTaskActivity::class.java)
        intent.putExtra("EXTRA_DATE", testDate)
        val activityScenario = ActivityScenario.launch<NewTaskActivity>(intent)
        onView(withId(R.id.taskname)).perform(typeText(testName))
        onView(withId(R.id.taskfrom)).perform(typeText(testFrom))
        onView(withId(R.id.taskto)).perform(typeText(testTo))
        onView(withId(R.id.taskdesc)).perform(typeText(testDesc))
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.save_button)).perform(click())
        onView(withId(R.id.day_recycler_view))
            .perform(RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                hasDescendant(withText(testName))))
    }

    @Test
    fun test2_task_edition() {
        val intent = Intent(ApplicationProvider.getApplicationContext(),
            DayScheduleActivity::class.java)
        intent.putExtra("EXTRA_DATE", testDate)
        val activityScenario = ActivityScenario.launch<DayScheduleActivity>(intent)

        onView(withId(R.id.day_recycler_view))
            .perform(RecyclerViewActions
                .actionOnItem<RecyclerView.ViewHolder>(
                    hasDescendant(withText(testName)),
                    RecyclerViewButtonAction.clickChildViewWithId(R.id.editbutton)))

        onView(withId(R.id.edit_taskname)).perform(clearText())
        onView(withId(R.id.edit_taskname)).perform(typeText(testNewName))
        onView(withId(R.id.edit_taskdesc)).perform(clearText())
        onView(withId(R.id.edit_taskdesc)).perform(typeText(testNewDesc))
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.edit_save_button)).perform(click())

        try {
            onView(withId(R.id.day_recycler_view))
                .perform(RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                    hasDescendant(withText(testName))))
            throw MyTestException
        } catch (e: PerformException) {
            onView(withId(R.id.day_recycler_view))
                .perform(RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                    hasDescendant(withText(testNewName))))
        }

    }

    @Test(expected = MyTestException::class)
    fun test3_task_deletion() {
        val intent = Intent(ApplicationProvider.getApplicationContext(),
            DayScheduleActivity::class.java)
        intent.putExtra("EXTRA_DATE", testDate)
        val activityScenario = ActivityScenario.launch<DayScheduleActivity>(intent)

        onView(withId(R.id.day_recycler_view))
            .perform(RecyclerViewActions
                .actionOnItem<RecyclerView.ViewHolder>(
                    hasDescendant(withText(testNewName)),
                    RecyclerViewButtonAction.clickChildViewWithId(R.id.deletebutton)))

        onView(withId(R.id.confirm_screen_layout)).check(matches(isDisplayed()))
        onView(withId(R.id.yes_button)).perform(click())

        try {
        onView(withId(R.id.day_recycler_view))
            .perform(RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                hasDescendant(withText(testNewName))))
        } catch (e: PerformException) {
            throw MyTestException
        }
    }
}