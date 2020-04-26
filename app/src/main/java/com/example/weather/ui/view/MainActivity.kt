package com.example.weather.ui.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.TextView
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
import com.google.android.gms.location.*
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private var layoutManager: LinearLayoutManager? = null
    private var adapter: MainActivityAdapter? = null
    private var liste = ArrayList<Forcastday>()
    private var cityName: String? = null
    private var longtitude: Double? = null
    private var latitude: Double? = null

    lateinit var mFusedLocationClient: FusedLocationProviderClient
    private val TAG = "PermissionDemo"
    private val REQUEST_CODE = 101

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

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
                    getLastLocation()
                } else {
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show()
                }
            }
        }
    }


    private fun getLastLocation() {
        mFusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
            var location: Location? = task.result

            longtitude = location!!.longitude
            latitude = location.latitude
            val geocoder = Geocoder(
                this,
                Locale.getDefault()
            );
            var address: ArrayList<Address>
            address =
                geocoder.getFromLocation(
                    latitude!!,
                    longtitude!!,
                    1
                ) as ArrayList<Address>
            cityName = address.get(0).adminArea
            locationAddress.text = cityName
            service(cityName)

        }
    }

    fun checkPermission() {
        val permission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        )

        if (permission != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Permission denied")
            makeRequest()
        } else {
            Toast.makeText(this, "Permission already granted", Toast.LENGTH_LONG).show()
            getLastLocation()
        }
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            REQUEST_CODE
        )
    }

    private fun service(cityName: String?) {
        val retrofit: API = RetrofitService.getRetrofit()!!.create(API::class.java)
        val call = retrofit.getWeather(Constants.API_KEY, cityName!!, 7)
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
            .load("https:" + forcastday.day!!.condition!!.icon)
            .override(170, 170)
            .into(weatherImage)
    }
}