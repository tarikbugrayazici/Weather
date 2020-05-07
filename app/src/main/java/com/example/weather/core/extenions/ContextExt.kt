package com.example.weather.core.extenions

import android.content.Context
import android.widget.Toast


fun Context.showToast(text: CharSequence, duration: Int = Toast.LENGTH_LONG) =
    this?.let { Toast.makeText(this, text, duration).show() }