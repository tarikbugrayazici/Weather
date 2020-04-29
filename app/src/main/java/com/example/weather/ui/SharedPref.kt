package com.example.weather.ui

import android.content.Context
import android.content.SharedPreferences

class SharedPref(context: Context) {
    internal lateinit var mSharedPref: SharedPreferences

    init {
        mSharedPref = context.getSharedPreferences("filename", Context.MODE_PRIVATE)
    }

    fun setNightModeState(state: Boolean?) {
        val editor = mSharedPref.edit()
        editor.putBoolean("night mode", state!!)
        editor.apply()
    }

    fun loadNightModeState(): Boolean? {
        return mSharedPref.getBoolean("night mode", false)
    }
}