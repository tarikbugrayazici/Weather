package com.example.weather.ui.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weather.R
import com.example.weather.data.entity.Forcastday
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*
import kotlin.collections.ArrayList


class MainActivityAdapter(
    private val context: Context,
    private val list: ArrayList<Forcastday>,
    private var listener: (Int) -> Unit
) :
    RecyclerView.Adapter<MainActivityAdapter.MainActivityAdapterHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainActivityAdapterHolder {
        return MainActivityAdapterHolder(
            LayoutInflater
                .from(context)
                .inflate(R.layout.adapter_item, parent, false)
        )
    }

    override fun onBindViewHolder(
        holder: MainActivityAdapterHolder,
        position: Int
    ) {

        val forcastday = list[position]
        for (day in list) {
            holder.degreeRecyclerView.text =
                forcastday.day!!.avgtemp_c!!.toInt().toString() + "\u00B0" //2020-04-23
            val date = forcastday.date
            val formattedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(date)
            val days = SimpleDateFormat("EEEE", Locale.getDefault()).format(formattedDate)
            holder.day.text = days
            Glide
                .with(context)
                .load("https:" + forcastday.day.condition!!.icon)
                .override(95, 95)
                .into(holder.weatherImageRecyclerView)
            holder.day.setOnClickListener {
                listener(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }


    inner class MainActivityAdapterHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var day = itemView.findViewById<TextView>(R.id.day)
        var weatherImageRecyclerView =
            itemView.findViewById<ImageView>(R.id.weatherImageRecyclerView)
        var degreeRecyclerView = itemView.findViewById<TextView>(R.id.degreeRecyclerView)
    }
}