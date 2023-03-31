package com.example.calendarapplication

import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class DatabaseTest {
    var db: DatabaseHelper = DatabaseHelper(ApplicationProvider.getApplicationContext())
    lateinit var alltasks: ArrayList<Task>
    val testname = "testingname"
    val teststart = "12:00"
    val testend = "13:00"
    val testdate = "2025-01-15"
    val testdetails = "details"
    val newname = "newname"
    val task = Task(testname, teststart, testend, testdate, testdetails)
    val newtask = Task(newname, teststart, testend, testdate, testdetails)


    @Test
    fun test0_initial_check() {
        db.deleteTask(task)
        db.deleteTask(newtask)
        alltasks = db.viewTask() as ArrayList<Task>
        assertFalse(alltasks.contains(task))
    }

    @Test
    fun test1_insert() {
        val result = db.addTask(task)
        assertFalse(result == -1L)
    }

    @Test
    fun test2_get() {
        alltasks = db.viewTask() as ArrayList<Task>
        assertTrue(alltasks.contains(task))
    }

    @Test
    fun test3_update() {
        val result = db.updateTask(newtask, task)
        alltasks = db.viewTask() as ArrayList<Task>
        assertFalse(result == -1)
        assertTrue(alltasks.contains(newtask))
        assertFalse(alltasks.contains(task))
    }

    @Test
    fun test4_delete() {
        val result = db.deleteTask(newtask)
        alltasks = db.viewTask() as ArrayList<Task>
        assertFalse(result == -1)
        assertFalse(alltasks.contains(newtask))
        db.close()
    }

}