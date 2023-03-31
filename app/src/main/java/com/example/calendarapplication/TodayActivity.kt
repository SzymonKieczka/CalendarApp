package com.example.calendarapplication

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TodayActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.today_screen)

        val button: Button = findViewById(R.id.today_to_calendar_button)
        button.setOnClickListener() {
            val intent = Intent(this, CalendarActivity::class.java)
            startActivity(intent)
        }

        val mydate = SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().time)
        val currentDate: TextView = findViewById(R.id.currentDate)
        currentDate.setText(mydate)

        val dbhelper = DatabaseHelper(this)
        val dataset = dbhelper.viewTask(mydate)
        val recyclerView = findViewById<RecyclerView>(R.id.today_recycler_view)
        recyclerView.adapter = MyAdapter(this, dataset, mydate)

    }
}