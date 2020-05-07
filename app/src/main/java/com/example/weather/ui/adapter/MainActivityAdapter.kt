package com.example.weather.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weather.R
import com.example.weather.core.extenions.loadFromUrl
import com.example.weather.core.helper.DateFormatHelper
import com.example.weather.core.util.Constants
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
    private var rowIndex = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainActivityAdapterHolder {
        return MainActivityAdapterHolder(
            LayoutInflater
                .from(context)
                .inflate(R.layout.adapter_item, parent, false)
        )
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(
        holder: MainActivityAdapterHolder,
        position: Int
    ) {

        val forcastday = list[position]
        holder.degreeRecyclerView.text =
            forcastday.day!!.avgtemp_c!!.toInt().toString() + "\u00B0"
        holder.day.text = DateFormatHelper.formatDate(forcastday.date!!)
        holder.weatherImageRecyclerView.loadFromUrl(Constants.url + forcastday.day.condition!!.icon)
        holder.weatherLayout.setOnClickListener {
            listener(position)
            rowIndex = position
            notifyDataSetChanged()
        }
        if (rowIndex == position) {
            holder.weatherLayout.setBackgroundColor(Color.parseColor("#bbe5ff"))
        } else {
            holder.weatherLayout.setBackgroundColor(Color.parseColor("#f9f9f9"))
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
        var weatherLayout = itemView.findViewById<LinearLayout>(R.id.weatherLayout)
    }
}