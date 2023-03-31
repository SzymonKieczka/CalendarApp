package com.example.calendarapplication

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class DatabaseHelper(val context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "TaskDatabase.db"
        const val TABLE_NAME = "TasksTable"
        const val NAME = "name"
        const val BEGINNING = "beginning"
        const val ENDING = "ending"
        const val DETAILS = "details"
        const val DATE = "date"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val query = """CREATE TABLE $TABLE_NAME (
                $NAME TEXT,
                $BEGINNING TEXT,
                $ENDING TEXT,
                $DETAILS TEXT,
                $DATE TEXT,
                PRIMARY KEY ($NAME, $DATE) )"""
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addTask(task: Task): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(NAME, task.name)
        contentValues.put(BEGINNING, task.beginning)
        contentValues.put(ENDING, task.ending)
        contentValues.put(DETAILS, task.details)
        contentValues.put(DATE, task.date)
        val result = db.insert(TABLE_NAME, null, contentValues)
        db.close()
        /*
        if (result == -1L) {
            Toast.makeText(context, "operation failed", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "operation successful", Toast.LENGTH_SHORT).show()
        }
         */
        return result
    }

    fun updateTask(task: Task, oldtask: Task): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(NAME, task.name)
        contentValues.put(BEGINNING, task.beginning)
        contentValues.put(ENDING, task.ending)
        contentValues.put(DETAILS, task.details)
        contentValues.put(DATE, task.date)
        val whereclause = """$NAME = '${oldtask.name}' AND 
                             $BEGINNING = '${oldtask.beginning}' AND 
                             $ENDING = '${oldtask.ending}' AND 
                             $DATE = '${oldtask.date}'"""
        val result = db.update(TABLE_NAME, contentValues, whereclause, null)
        db.close()
        /*
        if (result == -1) {
            Toast.makeText(context, "operation failed", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "operation successful", Toast.LENGTH_SHORT).show()
        }

         */
        return result
    }

    fun deleteTask(task: Task): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(NAME, task.name)
        contentValues.put(BEGINNING, task.beginning)
        contentValues.put(ENDING, task.ending)
        contentValues.put(DETAILS, task.details)
        contentValues.put(DATE, task.date)
        val whereclause = """$NAME = '${task.name}' AND 
                             $BEGINNING = '${task.beginning}' AND 
                             $ENDING = '${task.ending}' AND 
                             $DATE = '${task.date}'"""
        val result = db.delete(TABLE_NAME,whereclause, null)
        db.close()
        /*
        if (result == -1) {
            Toast.makeText(context, "operation failed", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "operation successful", Toast.LENGTH_SHORT).show()
        }
         */
        return result
    }

    fun viewTask(): List<Task> {
        return viewTask(null)
    }

    @SuppressLint("Range")
    fun viewTask(qdate: String?): List<Task> {
        val db = this.readableDatabase
        val cursor: Cursor?
        val list = ArrayList<Task>()
        var query = "SELECT * FROM $TABLE_NAME"
        if (qdate != null) {
            query += " WHERE $DATE = '${qdate}'"
        }
        query += " ORDER BY $BEGINNING"
        try {
            cursor = db.rawQuery(query, null)
        } catch (e: SQLException) {
            db.execSQL(query)
            return list
        }
        var name: String
        var beginning: String
        var ending: String
        var details: String
        var date: String
        if (cursor.moveToFirst()) {
            do {
                name = cursor.getString(cursor.getColumnIndex(NAME))
                beginning = cursor.getString(cursor.getColumnIndex(BEGINNING))
                ending = cursor.getString(cursor.getColumnIndex(ENDING))
                details = cursor.getString(cursor.getColumnIndex(DETAILS))
                date = cursor.getString(cursor.getColumnIndex(DATE))
                val task = Task(name, beginning, ending, details, date)
                list.add(task)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return list
    }
}