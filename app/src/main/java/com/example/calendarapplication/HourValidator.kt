package com.example.calendarapplication

class HourValidator {
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