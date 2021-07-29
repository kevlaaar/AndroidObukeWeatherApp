package com.example.obukeweatherapp.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class DailyWeatherData(
    @SerializedName("clouds")
    val cloudsAmount: Int,
    @SerializedName("datetime")
    val dayDate: String,
    @SerializedName("max_temp")
    val maxTemperature: Float,
    @SerializedName("min_temp")
    val minTemperature: Float,
    @SerializedName("temp")
    val temperature: Float,
    @SerializedName("precip")
    val precipitation: Float,
    @SerializedName("weather")
    val weatherObject: WeatherObject,
    val isButton: Boolean = false
): Serializable



class WeatherObject(
    val description: String
): Serializable

