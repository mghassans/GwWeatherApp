package com.cg.gweatherapplication.model

data class WeatherData( val timezone_offset : Long,
                        val daily : List<Properties>,
                        val hourly : List<HourlyProperties>,
                        val current : CurrentDayDetails){



    data class HourlyProperties(val dt : Long,
                                val temp : Double,
                                val pressure : Int,
                                val weather : List<Weather>
    )
    data class Properties(val dt : Long,
                          val sunrise : String,
                          val sunset : String,
                          val temp : Temp,
                          val weather : List<Weather>)

    data class Weather(val description : String,
                       val icon : String)

    data class CurrentDayDetails(val dt:Long,
                                 val sunrise : Long,
                                 val sunset : Long,
                                 val temp : Double,
                                 val weather : List<Weather>,
                                 val humidity : Int,
                                 val pressure : Int,
                                 val wind_speed : Double,
                                 val wind_deg :Int
    )
    data class Temp(val max : Double,
                    val min : Double)

}