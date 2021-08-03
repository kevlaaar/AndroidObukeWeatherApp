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
import com.example.obukeweatherapp.models.DailyWeatherData
import com.example.obukeweatherapp.models.WeatherResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TenDayFragment: Fragment()  {

    lateinit var tenDayRecycler: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentView = inflater.inflate(R.layout.fragment_ten_day, container, false)
        tenDayRecycler = fragmentView.findViewById(R.id.tenDayRecycler)
        tenDayRecycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        getWeatherData()
        return fragmentView
    }

    private fun getWeatherData() {
        val getWeatherCall = ApiInterface.create().getDailyForecast(days = 10)
        getWeatherCall.enqueue(object: Callback<WeatherResponse> {
            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {
                response.body()?.let {
                    val size = it.data.size
                    Log.e("RESPONSE SIZE", "$size")
                    populateTheRecycler(it.data)
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "SERVER CONNECTION FAILED", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun populateTheRecycler(listOfWeatherData: List<DailyWeatherData>){
        val adapter = WeatherAdapter(requireContext(), listOfWeatherData, goToTopListener)
        tenDayRecycler.adapter = adapter
    }

    val goToTopListener =  View.OnClickListener {
        tenDayRecycler.smoothScrollToPosition(0)
    }
}