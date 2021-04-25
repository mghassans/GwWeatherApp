package com.cg.gweatherapplication.ui.Daily

import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.cg.gweatherapplication.R
import com.cg.gweatherapplication.model.WeatherData

import java.text.SimpleDateFormat
import java.util.*

/**
 * [RecyclerView.Adapter] that can display a [DummyItem].
 * TODO: Replace the implementation with code for your data type.
 */
class MydailyRCVAdapter(
    private val values: List<WeatherData.Properties>
) : RecyclerView.Adapter<MydailyRCVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.daily_weather_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values?.get(position)
        val wea=item.weather.get(0)
        val temp=item.temp
        val da= SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).format(Date(item.dt*1000))
        holder.date.text="Date:${da} "
        holder.discription.text="Desciption:${(wea.description)}"
        holder.maxTemp.text=("Max Temp:${temp.max} ℃ ")
        holder.minTemp.text=("Min Temp:${temp.min} ℃")

        val imagepath=wea.icon
        val imageURL="https://openweathermap.org/img/wn/$imagepath.png"
        Glide.with(holder.itemView.context).load(Uri.parse(imageURL)).into(holder.posterIV)
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val date=view.findViewById<TextView>(R.id.dateT)
        val discription=view.findViewById<TextView>(R.id.description)
        val minTemp=view.findViewById<TextView>(R.id.minTemp)
        val maxTemp=view.findViewById<TextView>(R.id.maxTemp)
        val posterIV=view.findViewById<ImageView>(R.id.imageView)

    }
}