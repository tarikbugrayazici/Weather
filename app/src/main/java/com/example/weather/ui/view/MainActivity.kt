package com.example.weather.ui.view

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.weather.R
import com.example.weather.data.Constants
import com.example.weather.data.entity.BaseEntity
import com.example.weather.data.entity.Forcastday
import com.example.weather.data.service.API
import com.example.weather.data.service.RetrofitService
import com.example.weather.ui.adapter.MainActivityAdapter
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private var layoutManager: LinearLayoutManager? = null
    private var adapter: MainActivityAdapter? = null
    private var liste = ArrayList<Forcastday>()
    private var cityName: String? = null
    private val TAG = "PermissionDemo"
    private val REQUEST_CODE = 101

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkPermission()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CODE -> {

                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {

                    Toast.makeText(this, "permission granted", Toast.LENGTH_LONG).show()
                    service()
                } else {
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun checkPermission() {
        val permission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        )

        if (permission != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Permission denied")
            makeRequest()
        } else {
            Toast.makeText(this, "Permission already granted", Toast.LENGTH_LONG).show()
        }
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            REQUEST_CODE
        )
    }

    private fun service() {
        val retrofit: API = RetrofitService.getRetrofit()!!.create(API::class.java)
        val call = retrofit.getWeather(Constants.API_KEY, "Istanbul", 7)
        call.enqueue(object : retrofit2.Callback<BaseEntity> {
            override fun onResponse(
                call: retrofit2.Call<BaseEntity>,
                response: Response<BaseEntity>
            ) {
                setRecyclerView(response.body()!!.forecast!!.forecastday)
            }

            override fun onFailure(call: retrofit2.Call<BaseEntity>, t: Throwable) {
                System.out.println("hata" + t)
            }
        })
    }

    private fun setRecyclerView(list: ArrayList<Forcastday>) {
        liste.addAll(list)
        setItems(0)
        layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = layoutManager
        adapter = MainActivityAdapter(this, list) { position -> setItems(position) }
        recyclerView.adapter = adapter
    }

    private fun setItems(position: Int) {
        val forcastday = liste[position]
        weather.text = forcastday.day!!.condition!!.text
        degree.text = forcastday.day!!.avgtemp_c!!.toInt().toString() + "\u00B0"
        Glide
            .with(this)
            .load("http:" + forcastday.day!!.condition!!.icon)
            .override(170, 170)
            .into(weatherImage)
    }


}