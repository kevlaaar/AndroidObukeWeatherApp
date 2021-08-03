package com.example.obukeweatherapp

import com.example.obukeweatherapp.models.WeatherResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiInterface {

    companion object {
        var BASE_URL = "https://api.weatherbit.io/v2.0/forecast/"
        fun create(): ApiInterface {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

            val retrofit = Retrofit.Builder().
            addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .client(client)
                .build()

            return retrofit.create(ApiInterface::class.java)
        }
    }



    @GET("daily")
    fun getDailyForecast(
        @Query("lat") latitude: Float = 51.5074f,
        @Query("lon") longitude: Float = 0.1278f,
        @Query("days") days: Int = 4,
        @Query("key") key: String = "e4eea2eedb4d41bc8a05e597c6ddd01f"
    ): Call<WeatherResponse>



}