package com.cg.gweatherapplication.ui.Daily

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cg.gweatherapplication.R
import com.cg.gweatherapplication.model.GetWeather
import com.cg.gweatherapplication.model.WeatherData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A fragment representing a list of Items.
 */
class dailyFragment : Fragment() {

    private var columnCount = 1
    private var lat:Double = 34.25
    private var long:Double = 25.15
    lateinit var rview: RecyclerView
    var data : WeatherData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
           // lat=it.getDouble("lat")
           // long=it.getDouble("long")
        }

        val exclude = "current,alerts,minutely,hourly"
        val units = "metric"
        val apikey = "529ca6a3a479e33fd94d5aee99ebfe78"
        val request = GetWeather.getInstance().getdailyData("$lat","$long",exclude,apikey,units)
        request.enqueue(getWeatherDataCallback())

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = data?.let { MydailyRCVAdapter(it.daily) }
            }
        }
        return view
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
                data= response.body()!!
                Log.d("retroData","Messeage: $data")
                data?.let {
                    val list=it.daily
                    rview.adapter=MydailyRCVAdapter(list)
                }

            }
        }

        override fun onFailure(call: Call<WeatherData>, t: Throwable) {
            TODO("Not yet implemented")
        }
    }
}