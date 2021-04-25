package com.cg.gweatherapplication.view

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.cg.gweatherapplication.R
import com.cg.gweatherapplication.model.PincodeData
import com.cg.gweatherapplication.view_model.PincodeViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    val PREF_NAME="pincodeValues"

    lateinit var model :PincodeViewModel
    // Cubbon Park is default location and will update once set by you
    private var lati : Double = 12.973826
    private var longi : Double = 77.590591
    lateinit var lManager: LocationManager
    var providerName=""
    var pincode = "560001"



    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (checkPermission()){

            lManager = getSystemService(LOCATION_SERVICE) as LocationManager

        val providerList = lManager.getProviders(true)

        if (providerList.isNotEmpty()) {
            if (providerList.contains(LocationManager.GPS_PROVIDER))
                providerName = LocationManager.GPS_PROVIDER
            else if (providerList.contains(LocationManager.NETWORK_PROVIDER))
                providerName = LocationManager.NETWORK_PROVIDER
            else
                providerName = providerList[0]
        } else
            Toast.makeText(this, " location not traced", Toast.LENGTH_SHORT).show()
        val loc = lManager.getLastKnownLocation(providerName)
        if (loc != null) {
            lati = loc.latitude
            longi = loc.longitude
        }
    }

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications,R.id.navigation_map
        ))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val vmProvider = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory(application))

        model = vmProvider.get(PincodeViewModel::class.java)
        model.add(PincodeData(lat = lati , longi = longi))

        checkNetwork()

        saveCredentials(lati,longi)


        // val pinCodeDB = model.pin

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater?.inflate(R.menu.maps_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.mapViewMenu->{
                startActivity(Intent(this,MapsActivity::class.java) )
            }
        }
        return super.onOptionsItemSelected(item)

    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun checkPermission() : Boolean{
        if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
            checkSelfPermission(
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED){
            //ask user to grant
             requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),1)
            return false

        }
        else {
           // Toast.makeText(this,"All permissions are granted ",Toast.LENGTH_SHORT).show()
            return true

        }
    }


    fun getLocClicked(view: View) {


    CoroutineScope(Dispatchers.Default).launch {
        val pincodeGet = pincodeET.text.toString()
        if (pincodeGet.length == 6) {
            pincode = pincodeGet
            val newG = Geocoder(applicationContext)
            val adresses = newG.getFromLocationName(pincode, 1)

                        val address = adresses[0]

                        lati = address.latitude
                        longi = address.longitude


                        model.update(PincodeData(lat = lati, longi = longi, id = 1))
                        saveCredentials(lati, longi)


        }
    }


    }

    private fun saveCredentials(lati : Double, longi : Double){
        val pref =getSharedPreferences(PREF_NAME, MODE_PRIVATE)
        val editor =pref.edit()
        editor.putString("latitude", lati.toString())
        editor.putString("longitude",longi.toString())
        editor.commit()
    }
    private fun checkNetwork(): Boolean {
        val conManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val internetInfo =conManager.activeNetworkInfo
        return internetInfo!=null && internetInfo.isConnected
    }





}