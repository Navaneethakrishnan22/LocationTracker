package com.shop.mobile.locationtracker.utilities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shop.mobile.locationtracker.R

class Note_Adapter(var list: List<Note>) : RecyclerView.Adapter<Note_Adapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var et_get_city_name:TextView = view.findViewById(R.id.et_get_city_name)
        var tv_temp:TextView = view.findViewById(R.id.tv_temp)
        var Result : TextView = view.findViewById(R.id.Result)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note=list[position]
        holder.et_get_city_name.text = note.et_get_city_name
        holder.tv_temp.text = note.tv_temp
        holder.Result.text=note.Result
    }

    override fun getItemCount(): Int {
        return list.size
    }
}