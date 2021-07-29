package com.example.obukeweatherapp

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.obukeweatherapp.models.DailyWeatherData

class SingleWeatherActivity: AppCompatActivity() {

    lateinit var weatherImage: ImageView
    lateinit var dateText: TextView
    lateinit var temperatureText: TextView
    lateinit var rootView: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_weather)

        rootView = findViewById(R.id.rootView)
        weatherImage = findViewById(R.id.singleWeatherImage)
        dateText = findViewById(R.id.dateText)
        temperatureText = findViewById(R.id.temperatureText)
        val dataObject = intent.getSerializableExtra("WEATHER_DATA") as DailyWeatherData
        loadTheUi(dataObject)
    }

    private fun loadTheUi(weatherDataObject: DailyWeatherData) {
        when {
            weatherDataObject.precipitation > 1.0f -> {
                weatherImage.setImageResource(R.drawable.icon_rain_big)
                rootView.background =
                    ContextCompat.getDrawable(this, R.drawable.border_round_purple)
            }
            weatherDataObject.cloudsAmount > 20 -> {
                weatherImage.setImageResource(R.drawable.icon_cloud_big)
                rootView.background =
                    ContextCompat.getDrawable(this, R.drawable.border_round_blue)
            }
            else -> {
                weatherImage.setImageResource(R.drawable.icon_sunny_big)
                rootView.background =
                    ContextCompat.getDrawable(this, R.drawable.border_round_yellow)
            }
        }
        dateText.text = weatherDataObject.dayDate
        val temperatureData = "${weatherDataObject.temperature} °"
        temperatureText.text = temperatureData
    }
}