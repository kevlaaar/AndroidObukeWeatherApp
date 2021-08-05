package com.example.obukeweatherapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class WeatherActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        goToCityListFragment()
    }

    private fun goToCityListFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.rootFragmentContainer, CitiesFragment())
            .addToBackStack("fragment_backstack")
            .commit()
    }
}