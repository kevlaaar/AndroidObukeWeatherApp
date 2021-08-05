package com.example.obukeweatherapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.obukeweatherapp.OnCityClickListener
import com.example.obukeweatherapp.R
import com.example.obukeweatherapp.models.CityModel

class CityAdapter(
    private val context: Context,
    private val citiesList: List<CityModel>,
    private val onCityClickListener: OnCityClickListener
) : RecyclerView.Adapter<CityAdapter.CityViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_list_city, parent, false)
        return CityViewHolder(view)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val city = citiesList[position]
        holder.bind(city)
    }

    override fun getItemCount(): Int {
        return citiesList.size
    }

    inner class CityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val cityNameText: TextView = itemView.findViewById(R.id.cityNameText)
        private val cityLatitudeText: TextView = itemView.findViewById(R.id.cityLatitudeText)
        private val cityLongitudeText: TextView = itemView.findViewById(R.id.cityLongitudeText)

        fun bind(city: CityModel) {
            cityNameText.text = city.name
            cityLatitudeText.text = city.latitude.toString()
            cityLongitudeText.text = city.longitude.toString()
            itemView.setOnClickListener{
                onCityClickListener.onCityClick(city)
            }
        }
    }
}