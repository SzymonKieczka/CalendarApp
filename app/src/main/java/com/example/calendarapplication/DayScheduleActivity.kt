package com.example.calendarapplication

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DayScheduleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.day_schedule_screen)

        val backButton: Button = findViewById(R.id.day_schedule_to_calendar_button)
        backButton.setOnClickListener() {
            val intent = Intent(this, CalendarActivity::class.java)
            startActivity(intent)
        }

        var mydate = intent.getStringExtra("EXTRA_DATE")
        if (mydate == "") {
            mydate = SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().time)
        }
        val selected_date: TextView = findViewById(R.id.selected_date)
        selected_date.setText(mydate)


        val dbhelper = DatabaseHelper(this)
        val dataset = dbhelper.viewTask(mydate)
        val recyclerView = findViewById<RecyclerView>(R.id.day_recycler_view)
        recyclerView.adapter = MyAdapter(this, dataset, mydate)


        val newTaskButton: Button = findViewById(R.id.add_task_button)
        newTaskButton.setOnClickListener() {
            val intent = Intent(this, NewTaskActivity::class.java)
            intent.putExtra("EXTRA_DATE", mydate)
            startActivity(intent)
        }
    }
}