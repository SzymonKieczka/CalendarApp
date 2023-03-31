package com.example.calendarapplication

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class EditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_screen)
        val dbhelper = DatabaseHelper(this)

        val name: EditText = findViewById(R.id.edit_taskname)
        val beginning: EditText = findViewById(R.id.edit_taskfrom)
        val ending: EditText = findViewById(R.id.edit_taskto)
        val details: EditText = findViewById(R.id.edit_taskdesc)

        val mydate: String? = intent.getStringExtra("EXTRA_DATE")
        val parentclass: Class<Context> = intent.getSerializableExtra("EXTRA_CLASS") as Class<Context>
        val oldname = intent.getStringExtra("EXTRA_NAME")
        val oldbeginning = intent.getStringExtra("EXTRA_FROM")
        val oldending = intent.getStringExtra("EXTRA_TO")
        val olddesc = intent.getStringExtra("EXTRA_DESC")
        val oldtask = Task(oldname, oldbeginning, oldending, olddesc, mydate)

        name.setText(oldname)
        beginning.setText(oldbeginning)
        ending.setText(oldending)
        details.setText(olddesc)

        val saveButton: AppCompatButton = findViewById(R.id.edit_save_button)
        saveButton.setOnClickListener() {
            val nametext = name.text.toString().trim()
            val beginningtext = beginning.text.toString().trim()
            val endingtext = ending.text.toString().trim()
            val detailstext = details.text.toString().trim()
            if (nametext == "") {
                Toast.makeText(this, "name cannot be empty!", Toast.LENGTH_SHORT).show()
            } else if (beginningtext == "") {
                Toast.makeText(this, "beginning cannot be empty!", Toast.LENGTH_SHORT).show()
            } else if (endingtext == "") {
                Toast.makeText(this, "ending cannot be empty!", Toast.LENGTH_SHORT).show()
            } else if (detailstext == "") {
                Toast.makeText(this, "details cannot be empty!", Toast.LENGTH_SHORT).show()
            } else if (!verifyHours(beginningtext, endingtext, mydate, oldtask)) {
                Toast.makeText(this,
                    "invalid hours! start is later than end or hours overlap with another task",
                    Toast.LENGTH_SHORT).show()
            } else {
                val task = Task(nametext, beginningtext, endingtext, detailstext, mydate)
                val result = dbhelper.updateTask(task, oldtask)
                if (result > -1) {
                    Toast.makeText(this, "save successful", Toast.LENGTH_SHORT).show()
                    name.text.clear()
                    beginning.text.clear()
                    ending.text.clear()
                    details.text.clear()
                } else {
                    Toast.makeText(this, "save failed", Toast.LENGTH_SHORT).show()
                }
                val intent = Intent(this, parentclass)
                intent.putExtra("EXTRA_DATE", mydate)
                startActivity(intent)
            }
        }

        val cancelButton: Button = findViewById(R.id.edit_cancel_button)
        cancelButton.setOnClickListener() {
            val intent = Intent(this, parentclass)
            intent.putExtra("EXTRA_DATE", mydate)
            startActivity(intent)
        }
    }

    fun verifyHours(start: String, end: String, mydate: String?, task: Task): Boolean {
        val regex = Regex("[0-9]{2}:[0-9]{2}")
        if (start == end) {
            return false
        }
        if (!(start matches regex && end matches regex)) {
            return false
        } else if (!compareHours(start, end)) {
            return false
        } else {
            val db = DatabaseHelper(this)
            val others = db.viewTask(mydate)
            if (others.isNotEmpty()) {
                for (other in others) {
                    if (!task.equals(other) &&
                        compareHours(start, other.ending!!) &&
                        compareHours(other.beginning!!, end)) {
                        return false
                    }
                }
            }
        }
        return true
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