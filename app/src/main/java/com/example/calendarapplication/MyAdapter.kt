package com.example.calendarapplication

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val context: Context, private val dataset: List<Task>, val mydate: String?):
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    class MyViewHolder(private val view: View): RecyclerView.ViewHolder(view){
        val taskname: TextView = view.findViewById(R.id.item_title)
        val tasktime: TextView = view.findViewById(R.id.item_from_to)
        val taskdetails: TextView = view.findViewById(R.id.item_details)
        val editbutton: AppCompatButton = view.findViewById(R.id.editbutton)
        val deletebutton: AppCompatButton = view.findViewById(R.id.deletebutton)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return MyViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = dataset[position]
        holder.taskname.text = item.name
        holder.tasktime.text = "${item.beginning} - ${item.ending}"
        if (item.details!!.length >= 27) {
            holder.taskdetails.text = item.details.substring(0, 27) + "..."
        } else {
            holder.taskdetails.text = item.details
        }

        holder.editbutton.setOnClickListener() {
            val intent = Intent(context, EditActivity::class.java)
            intent.putExtra("EXTRA_DATE", mydate)
            intent.putExtra("EXTRA_CLASS", context::class.java)
            intent.putExtra("EXTRA_NAME", item.name)
            intent.putExtra("EXTRA_FROM", item.beginning)
            intent.putExtra("EXTRA_TO", item.ending)
            intent.putExtra("EXTRA_DESC", item.details)
            startActivity(context, intent, null)
        }
        holder.deletebutton.setOnClickListener() {
            val intent = Intent(context, ConfirmActivity::class.java)
            intent.putExtra("EXTRA_DATE", mydate)
            intent.putExtra("EXTRA_CLASS", context::class.java)
            intent.putExtra("EXTRA_NAME", item.name)
            intent.putExtra("EXTRA_FROM", item.beginning)
            intent.putExtra("EXTRA_TO", item.ending)
            intent.putExtra("EXTRA_DESC", item.details)
            startActivity(context, intent, null)
        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

}