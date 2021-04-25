package com.cg.gweatherapplication.ui.Hourly

import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cg.gweatherapplication.R
import com.cg.gweatherapplication.model.WeatherData
import java.text.SimpleDateFormat
import java.util.*

class MyHourlyRCVAdapter(private val values: List<WeatherData.HourlyProperties>)
    : RecyclerView.Adapter<MyHourlyRCVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.hourly_list_layout, parent, false)
        return ViewHolder(view)
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values?.get(position)
        val temp=item.temp
        val weather=item.weather.get(0)
        val pressure= item.pressure
        val da= SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ENGLISH).format(Date(item.dt*1000))
        holder.hour.text="Time:${da?.subSequence(10..15)} "
        holder.TempH.text=("Temp:${temp}℃ ")
        holder.pressureH.text =("Pressure: ${pressure}")

        val imagepath=weather.icon
        val imageURL="https://openweathermap.org/img/wn/$imagepath.png"
        Glide.with(holder.itemView.context).load(Uri.parse(imageURL)).into(holder.hourlyLogo)
    }

    override fun getItemCount() : Int = values.size


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val hour = view.findViewById<TextView>(R.id.hourTV)
        val pressureH = view.findViewById<TextView>(R.id.pressureTV)
        val TempH = view.findViewById<TextView>(R.id.tempTV)
        val hourlyLogo = view.findViewById<ImageView>(R.id.houlyImage)

    }



}