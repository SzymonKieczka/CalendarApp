package com.example.calendarapplication

import org.junit.Test
import org.junit.Assert.*

class CompareHoursUnitTest {

    @Test
    fun compareInvalidHoursFalse_Exception() {
        assertFalse(compareHours("wrong val", "12:23"))
    }

    @Test
    fun compareInvalidHoursFalse_HourLimit() {
        assertFalse(compareHours("11:23", "49:50"))
    }

    @Test
    fun compareInvalidHoursFalse_HourLimit2() {
        assertFalse(compareHours("-11:00", "12:20"))
    }

    @Test
    fun compareInvalidHoursFalse_MinuteLimit() {
        assertFalse(compareHours("11:00", "13:98"))
    }

    @Test
    fun compareInvalidHoursFalse_MinuteLimit2() {
        assertFalse(compareHours("11:-10", "11:20"))
    }

    @Test
    fun compareValidHoursFalse_HoursLater() {
        assertFalse(compareHours("11:10", "10:10"))
    }

    @Test
    fun compareValidHoursFalse_MinutesLater() {
        assertFalse(compareHours("12:30", "12:10"))
    }

    @Test
    fun compareValidHoursTrue() {
        assertTrue(compareHours("11:00", "12:30"))
    }

    fun compareHours(start: String, end: String): Boolean {
        //returns false if start > end
        try {
            val startHour = start.split(":")[0].toInt()
            val endHour = end.split(":")[0].toInt()
            val startMinute = start.split(":")[1].toInt()
            val endMinute = end.split(":")[1].toInt()
            if (startHour < 0 || endHour < 0 || startMinute < 0 || endMinute < 0) {
                return false
            } else if (startHour > 23 || endHour > 23 || startMinute > 59 || endMinute > 59) {
                return false
            } else if (endHour < startHour) {
                return false
            } else if (endHour == startHour && endMinute < startMinute) {
                return false
            }
            return true
        } catch (ex: Exception) {
            return false
        }
    }
}