package com.example.obukeweatherapp.models

import com.google.gson.annotations.SerializedName

class WeatherResponse(
    val data: List<DailyWeatherData>,
    @SerializedName("city_name")
    val cityName: String
)