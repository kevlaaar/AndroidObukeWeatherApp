package com.example.obukeweatherapp.models

import java.io.Serializable

class CityModel(
    val id: Int,
    val name: String,
    val latitude: Float,
    val longitude: Float
): Serializable