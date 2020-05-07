package com.example.weather.ui.view

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.weather.core.helper.PermissionHelper
import com.example.weather.R
import com.example.weather.core.extenions.loadFromUrl
import com.example.weather.core.extenions.showToast
import com.example.weather.core.util.Constants
import com.example.weather.data.entity.Forcastday
import com.example.weather.ui.SharedPref
import com.example.weather.ui.adapter.MainActivityAdapter
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

@Suppress("IMPLICIT_BOXING_IN_IDENTITY_EQUALS")
class MainActivity : AppCompatActivity() {
    private var layoutManager: LinearLayoutManager? = null
    private var adapter: MainActivityAdapter? = null
    private var liste = ArrayList<Forcastday>()
    private var cityName: String? = null
    private var longtitude: Double? = null
    private var latitude: Double? = null
    lateinit var mFusedLocationClient: FusedLocationProviderClient
    internal lateinit var mSharedPref: SharedPref
    private val REQUEST_CODE = 101
    private lateinit var viewModel: MainActivityViewModel
    private val permissionHelper: PermissionHelper by lazy {
        PermissionHelper(
            this
        )
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        mSharedPref = SharedPref(this)
        if (mSharedPref.loadNightModeState() === true)
            setTheme(R.style.DarkTheme)
        else
            setTheme(R.style.LightTheme)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.visibility = View.GONE
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        permissionHelper.checkPermission { getLastLocation() }
        if (mSharedPref.loadNightModeState() == true)
            toggle.isChecked = true

        toggle.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                mSharedPref.setNightModeState(true)
            } else {
                mSharedPref.setNightModeState(false)
            }
            finish()
            startActivity(getIntent())
        }
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        viewModel.getLastWeather()
        viewModel.weatherLiveData.observe(this, androidx.lifecycle.Observer {
            setRecyclerView(it)
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CODE -> {

                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    this.showToast("permission granted")
                    getLastLocation()
                } else {
                    this.showToast("permission denied")
                }
            }
        }
    }

    private fun getLastLocation() {
        mFusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
            val location: Location? = task.result

            longtitude = location!!.longitude
            latitude = location.latitude
            val geocoder = Geocoder(
                this,
                Locale.getDefault()
            )
            val address: ArrayList<Address>
            address =
                geocoder.getFromLocation(
                    latitude!!,
                    longtitude!!,
                    1
                ) as ArrayList<Address>
            cityName = address.get(0).adminArea
            locationAddress.text = cityName
        }
    }

    private fun setRecyclerView(list: ArrayList<Forcastday>) {
        recyclerView.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
        liste.addAll(list)
        setItems(0)
        layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = layoutManager
        adapter = MainActivityAdapter(this, list) { position -> setItems(position) }
        recyclerView.adapter = adapter
    }

    @SuppressLint("SetTextI18n")
    private fun setItems(position: Int) {
        val forcastday = liste[position]
        weather.text = forcastday.day!!.condition!!.text
        degree.text = forcastday.day.avgtemp_c!!.toInt().toString() + "\u00B0"
        weatherImage.loadFromUrl(Constants.url + forcastday.day.condition!!.icon)


    }
}
