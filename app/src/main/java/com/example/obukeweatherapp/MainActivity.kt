package com.example.obukeweatherapp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.obukeweatherapp.adapter.WeatherAdapter
import com.example.obukeweatherapp.models.DailyWeatherData
import com.example.obukeweatherapp.models.WeatherObject
import com.example.obukeweatherapp.models.WeatherResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity: AppCompatActivity() {

//    lateinit var weatherRecycler: RecyclerView
    lateinit var fourDayButton: Button
    lateinit var tenDayButton: Button
    lateinit var sixteenDayButton: Button
    lateinit var popFragmentBackStackButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getWeatherData()
        popFragmentBackStackButton = findViewById(R.id.popBackStackButton)
        popFragmentBackStackButton.setOnClickListener{
            supportFragmentManager.popBackStackImmediate()
        }
        fourDayButton = findViewById(R.id.fourDayButton)

        fourDayButton.setOnClickListener {
            showDialogFragment("Four day button clicked")
        }
        tenDayButton = findViewById(R.id.tenDayButton)
        tenDayButton.setOnClickListener {
            showDialogFragment("Ten day button clicked")
        }

        sixteenDayButton = findViewById(R.id.sixteenDayButton)
        sixteenDayButton.setOnClickListener {
            showDialogFragment("Sixteen day button clicked")
        }
    }

    private fun goToFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainer, fragment)
            .addToBackStack("fragment_backstack")
            .commit()

    }
    private fun showDialogFragment(message: String) {
        val messageDialogFragment = MessageDialogFragment.newInstance(message)
        messageDialogFragment.show(supportFragmentManager, "dialog_fragment")
    }

    private fun getWeatherData() {
        val getWeatherCall = ApiInterface.create().getDailyForecast()
        getWeatherCall.enqueue(object: Callback<WeatherResponse> {
            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {
                response.body()?.let {
                    val size = it.data.size
                    Log.e("RESPONSE SIZE", "$size")
//                    populateTheRecycler(it.data)
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                Toast.makeText(applicationContext, "SERVER CONNECTION FAILED", Toast.LENGTH_LONG).show()
            }
        })
    }
    private fun populateTheRecycler(listOfWeatherData: List<DailyWeatherData>){
        val adapter = WeatherAdapter(this, listOfWeatherData, goToTopListener)
//        weatherRecycler.adapter = adapter
    }
    val goToTopListener =  View.OnClickListener {
//        weatherRecycler.smoothScrollToPosition(0)
    }
}