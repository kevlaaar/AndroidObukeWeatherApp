package com.example.obukeweatherapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.obukeweatherapp.adapter.WeatherAdapter
import com.example.obukeweatherapp.models.CityModel
import com.example.obukeweatherapp.models.DailyWeatherData
import com.example.obukeweatherapp.models.WeatherResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SixteenDayFragment: Fragment()  {

    lateinit var sixteenDayRecycler: RecyclerView
    lateinit var cityModel: CityModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentView = inflater.inflate(R.layout.fragment_sixteen_day, container, false)
        sixteenDayRecycler = fragmentView.findViewById(R.id.sixteenDayRecycler)
        sixteenDayRecycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        cityModel = arguments?.getSerializable("CITY_OBJECT") as CityModel
        getWeatherData()
        return fragmentView
    }
    private fun getWeatherData() {
        val getWeatherCall = ApiInterface.create().getDailyForecast(latitude = cityModel.latitude, longitude = cityModel.longitude, days = 16)
        getWeatherCall.enqueue(object: Callback<WeatherResponse> {
            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {
                response.body()?.let {
                    val size = it.data.size
                    Log.e("RESPONSE SIZE", "$size")
                    populateTheRecycler(it.data, it.cityName)
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "SERVER CONNECTION FAILED", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun populateTheRecycler(listOfWeatherData: List<DailyWeatherData>, cityName: String){
        val adapter = WeatherAdapter(requireContext(), listOfWeatherData, goToTopListener, cityName = cityName)
        sixteenDayRecycler.adapter = adapter
    }

    val goToTopListener =  View.OnClickListener {
        sixteenDayRecycler.smoothScrollToPosition(0)
    }
}