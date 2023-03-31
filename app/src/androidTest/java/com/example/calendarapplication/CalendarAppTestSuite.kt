package com.example.calendarapplication

import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.junit.runners.Suite.SuiteClasses

@RunWith(Suite::class)
@SuiteClasses(
    MainActivityTest::class,
    NavigationTest::class,
    DatabaseTest::class,
    TaskCRUDTest::class
)
class CalendarAppTestSuite