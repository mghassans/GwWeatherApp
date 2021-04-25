package com.cg.gweatherapplication.ui.Daily

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cg.gweatherapplication.R
import com.cg.gweatherapplication.model.GetWeather
import com.cg.gweatherapplication.model.MyDialog
import com.cg.gweatherapplication.model.WeatherData
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DailyDataFragment : Fragment() {

    private var columnCount = 1
    private var lat:Double ? = 0.0
    private var long:Double ?= 0.0
    lateinit var rview: RecyclerView
    var data : WeatherData? = null
    val dlg = MyDialog()
    val PREF_NAME="pincodeValues"
    lateinit var pref: SharedPreferences



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }

        pref= activity?.getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE)!!
        lat = pref.getString("latitude", "12.973826")?.toDouble()
        long = pref.getString("longitude", "17.0590591")?.toDouble()

        Toast.makeText(activity, "$lat  and $long", Toast.LENGTH_LONG).show()

       doWithCoRoutines()


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rview = inflater.inflate(R.layout.fragment_notifications, container, false) as RecyclerView



        // Set the adapter
        if (rview is RecyclerView) {
            with(rview) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = data?.let { MydailyRCVAdapter(it.daily) }
            }
        }
        return rview
    }



    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int,latitude:Double,longitude:Double) =
            dailyFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                    putDouble("lat",latitude)
                    putDouble("long",longitude)
                }
            }
    }
    inner class getWeatherDataCallback: Callback<WeatherData> {
        override fun onResponse(call: Call<WeatherData>, response: Response<WeatherData>) {
            if(response.isSuccessful){
                data = response.body()!!
                data?.let {
                    val list = it.daily
                    rview.adapter = MydailyRCVAdapter(list)
                }

            }
        }

        override fun onFailure(call: Call<WeatherData>, t: Throwable) {
            Toast.makeText(activity, "Problem while fetching data...(Check Internet)", Toast.LENGTH_LONG).show()
        }
    }
    fun doWithCoRoutines() {
        CoroutineScope(Dispatchers.Default).launch {
            val exclude = "current,alerts,minutely,hourly"
            val units = "metric"
            val apikey = "529ca6a3a479e33fd94d5aee99ebfe78"
            val result = CoroutineScope(Dispatchers.Main).async {

                val request = GetWeather.getInstance().getdailyData("$lat","$long",exclude,apikey,units)
                request.enqueue(getWeatherDataCallback())
                delay(1000)

            }
            dlg.show(activity?.supportFragmentManager!!, "")
            result.await()
            dlg.dismiss()
        }
    }
}