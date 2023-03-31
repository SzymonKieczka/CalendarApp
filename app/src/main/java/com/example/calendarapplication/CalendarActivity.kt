package com.example.calendarapplication

import android.content.Intent
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CalendarView
import android.widget.TextView

class CalendarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calendar_screen)

        val button: Button = findViewById(R.id.calendar_back_to_schedule_button)
        button.setOnClickListener() {
            val intent = Intent(this, TodayActivity::class.java)
            startActivity(intent)
        }

        val calendar: CalendarView = findViewById(R.id.calendar)
        var selected_date: String = ""
        calendar.setOnDateChangeListener { calendarView, year, month, day ->
            selected_date = "" + year + "-" + (month+1) + "-" + day
        }

        val button2: Button = findViewById(R.id.schedule_for_this_day)
        button2.setOnClickListener() {
            val intent = Intent(this, DayScheduleActivity::class.java)
            intent.putExtra("EXTRA_DATE", selected_date)
            startActivity(intent)
        }
    }
}