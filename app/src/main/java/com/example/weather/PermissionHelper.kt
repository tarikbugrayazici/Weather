package com.example.weather

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class PermissionHelper(private val activity: Activity) {

    private val REQUEST_CODE = 101
    private val TAG = ""

    fun checkPermission(block: () -> Unit) {
        val permission = ContextCompat.checkSelfPermission(
            activity,
            Manifest.permission.ACCESS_FINE_LOCATION
        )

        if (permission != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Permission denied")
            makeRequest()
        } else {
            Toast.makeText(activity, "Permission already granted", Toast.LENGTH_LONG).show()
            block()
        }
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            REQUEST_CODE
        )
    }

}