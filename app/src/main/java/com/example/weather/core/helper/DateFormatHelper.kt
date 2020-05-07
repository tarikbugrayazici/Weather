package com.example.weather.core.helper

import android.annotation.SuppressLint
import com.example.weather.core.util.Constants
import java.text.ParseException
import java.text.SimpleDateFormat


@SuppressLint("SimpleDateFormat")
object DateFormatHelper {

    fun formatDate(date: String): String {
        var spf = SimpleDateFormat(Constants.UNFORMATTED_DATE)
        try {
            val newDate = spf.parse(date)
            spf = SimpleDateFormat(Constants.FORMATTED_DATE)
            return spf.format(newDate)
        } catch (e: ParseException) {
            return date
        }

    }
}
