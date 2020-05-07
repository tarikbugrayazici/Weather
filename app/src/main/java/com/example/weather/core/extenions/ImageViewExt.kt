package com.example.weather.core.extenions

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadFromUrl(imageUrl: String) {
    Glide.with(context).load(imageUrl).override(130, 130).into(this)
}