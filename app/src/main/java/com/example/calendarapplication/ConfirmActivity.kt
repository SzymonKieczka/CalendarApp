package com.example.calendarapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton

class ConfirmActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm)

        val parentclass: Class<Context> = intent.getSerializableExtra("EXTRA_CLASS") as Class<Context>
        val taskname = intent.getStringExtra("EXTRA_NAME")
        val taskbeginning = intent.getStringExtra("EXTRA_FROM")
        val taskending = intent.getStringExtra("EXTRA_TO")
        val taskdesc = intent.getStringExtra("EXTRA_DESC")
        val mydate: String? = intent.getStringExtra("EXTRA_DATE")
        val task = Task(taskname, taskbeginning, taskending, taskdesc, mydate)

        val name: TextView = findViewById(R.id.confirm_name)
        val fromto: TextView = findViewById(R.id.confirm_from_to)
        val details: TextView = findViewById(R.id.confirm_details)

        name.setText("name: ${taskname}")
        fromto.setText("time: ${taskbeginning}-${taskending}")
        details.setText("details: ${taskdesc}")

        val yesButton: AppCompatButton = findViewById(R.id.yes_button)
        yesButton.setOnClickListener() {

            val dbhelper = DatabaseHelper(this)
            val result = dbhelper.deleteTask(task)
            if (result > -1) {
                Toast.makeText(this, "deletion successful", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "deletion failed", Toast.LENGTH_SHORT).show()
            }
            val intent = Intent(this, parentclass)
            intent.putExtra("EXTRA_DATE", mydate)
            startActivity(intent)
        }

        val noButton: AppCompatButton = findViewById(R.id.no_button)
        noButton.setOnClickListener() {
            val intent = Intent(this, parentclass)
            intent.putExtra("EXTRA_DATE", mydate)
            startActivity(intent)
        }

    }
}