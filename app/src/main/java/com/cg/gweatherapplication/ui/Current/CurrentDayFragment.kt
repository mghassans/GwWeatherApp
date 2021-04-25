package com.cg.gweatherapplication.ui.Current

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cg.gweatherapplication.R
import com.cg.gweatherapplication.model.GetWeather
import com.cg.gweatherapplication.model.MyDialog
import com.cg.gweatherapplication.model.WeatherData
import com.cg.gweatherapplication.ui.Daily.DailyDataFragment
import com.cg.gweatherapplication.ui.Hourly.MyHourlyRCVAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class CurrentDayFragment : Fragment() {

    private var columnCount = 1
    private var lat:Double = 12.77234
    private var long:Double = 77.8764
    var data : WeatherData? = null
    val dlg = MyDialog()
    lateinit var list :WeatherData.CurrentDayDetails

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(DailyDataFragment.ARG_COLUMN_COUNT)
            lat=it.getDouble("lat")
            long=it.getDouble("long")
        }


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home,container,false)
    }

    @SuppressLint("SetTextI18n")
    fun setDetails(){

            val details = list
            val date = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).format(Date(details.dt*1000))
            val sunrise = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ENGLISH).format(Date(details.sunrise*1000)).subSequence(10..15)
            val sunset = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ENGLISH).format(Date(details.sunset*1000)).subSequence(10..15)
            val weather = details.weather[0]
            val imageIcon = weather.icon
            val humidity = details.humidity
            val descrption = weather.description
            val windSpeed = details.wind_speed
            val windDeg = details.wind_deg

            dateHome.text = date ?: "Today"
            humidityHome.text = "Humidity level: $humidity"
            tempHome.text = "Temp: ${details.temp}℃"
            sunriseHome.text = "Sunrise: $sunrise"
            sunsetHome.text = "Sunset: $sunset"
            descriptionHome.text = "Description: $descrption"
            windSpecsHome.text =" Wind Speed: $windSpeed at $windDeg \u00B0"


            val imageURL="https://openweathermap.org/img/wn/$imageIcon.png"
            Glide.with(this)
                    .load(Uri.parse(imageURL)).into(CurrentLogo)



    }

    inner class getWeatherDataCallback: Callback<WeatherData> {
        override fun onResponse(call: Call<WeatherData>, response: Response<WeatherData>) {
            if(response.isSuccessful){
                data = response.body()!!
                Log.d("retroData","Message $data")
                data?.let {
                     list = it.current

                }
                setDetails()
            }
        }

        override fun onFailure(call: Call<WeatherData>, t: Throwable) {
            Toast.makeText(activity, "Problem while fetching data...(CheckInternet)", Toast.LENGTH_LONG).show()
        }
    }

    private fun doWithCoRoutines() {

        CoroutineScope(Dispatchers.Default).launch {
            val exclude = "hourly,alerts,minutely,daily"
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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        doWithCoRoutines()

        super.onViewCreated(view, savedInstanceState)
    }

}