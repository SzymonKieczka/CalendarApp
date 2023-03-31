package com.example.calendarapplication

class Task(
    val name: String?,
    val beginning: String?,
    val ending: String?,
    val details: String?,
    val date: String?
) {

    override fun equals(other: Any?): Boolean {
        if (other is Task) {
            return equalsTask(other)
        }
        return false
    }

    fun equalsTask(other: Task): Boolean {
       return other.name == this.name &&
              other.beginning == this.beginning &&
              other.ending == this.ending &&
              other.details == this.details &&
              this.date == other.date
    }
}