package com.example.obukeweatherapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.obukeweatherapp.R
import com.example.obukeweatherapp.SingleWeatherActivity
import com.example.obukeweatherapp.models.DailyWeatherData

class WeatherAdapter(
    private val context: Context, private val listOfWeatherData: List<DailyWeatherData>, val buttonListener: View.OnClickListener,
    private val cityName: String
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
    : RecyclerView.ViewHolder {
        if (viewType == 0) {
            val view =
                LayoutInflater.from(context).inflate(R.layout.item_list_weather, parent, false)
            return WeatherItemViewHolder(view)
        } else {
            val view =
                LayoutInflater.from(context)
                    .inflate(R.layout.item_list_footer_button, parent, false)
            return ButtonViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(getItemViewType(position) == 0) {
            val weatherDataItem = listOfWeatherData[position]
            holder as WeatherItemViewHolder
            holder.bind(weatherDataItem)
        } else {
            holder as ButtonViewHolder
            holder.bind()
        }
    }

    override fun getItemCount(): Int {
        return listOfWeatherData.size+1
    }

    // 1 - WeatherViewHolder
    // 2 - ButtonViewHolder
    override fun getItemViewType(position: Int): Int {
        return if (position < listOfWeatherData.size) {
            0
        } else {
            1
        }
    }

    inner class WeatherItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val locationText: TextView = itemView.findViewById(R.id.weatherLocationText)
        val dateText: TextView = itemView.findViewById(R.id.weatherDateText)
        val degreesText: TextView = itemView.findViewById(R.id.weatherDegreesText)
        val minDegreesText: TextView = itemView.findViewById(R.id.weatherMinDegreesText)
        val maxDegreesText: TextView = itemView.findViewById(R.id.weatherMaxDegreesText)
        val weatherImage: ImageView = itemView.findViewById(R.id.weatherImage)

        fun bind(weatherData: DailyWeatherData) {
            when {
                weatherData.precipitation > 1.0f -> {
                    weatherImage.setImageResource(R.drawable.icon_rain_big)
                    itemView.background =
                        ContextCompat.getDrawable(context, R.drawable.border_round_purple)
                }
                weatherData.cloudsAmount > 20 -> {
                    weatherImage.setImageResource(R.drawable.icon_cloud_big)
                    itemView.background =
                        ContextCompat.getDrawable(context, R.drawable.border_round_blue)
                }
                else -> {
                    weatherImage.setImageResource(R.drawable.icon_sunny_big)
                    itemView.background =
                        ContextCompat.getDrawable(context, R.drawable.border_round_yellow)
                }
            }

            locationText.text = cityName

            dateText.text = weatherData.dayDate

            val degreesValue = "${weatherData.temperature} °"
            degreesText.text = degreesValue

            val minValue = "Min: ${weatherData.minTemperature} °"
            minDegreesText.text = minValue

            val maxValue = "Max: ${weatherData.maxTemperature} °"
            maxDegreesText.text = maxValue

            itemView.setOnClickListener{
                goToSingleWeatherActivity(weatherData)
            }
        }

        private fun goToSingleWeatherActivity(singleWeatherData: DailyWeatherData) {
            val intent = Intent( context , SingleWeatherActivity::class.java)
            intent.putExtra("WEATHER_DATA", singleWeatherData)
            context.startActivity(intent)
        }

    }

    inner class ButtonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val button: Button = itemView.findViewById(R.id.backToTopButton)

        fun bind() {
            button.setOnClickListener(buttonListener)
        }
    }
}