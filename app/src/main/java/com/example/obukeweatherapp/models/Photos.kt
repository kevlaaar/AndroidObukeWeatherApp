package com.example.obukeweatherapp.models

import com.google.gson.annotations.SerializedName

data class Photos(
    val id: Int,
    val width: Int,
    val height: Int,
    @SerializedName("photographer")
    val photographerName: String,
    @SerializedName("src")
    val photoResources: PhotoResource
)

data class PhotoResource(
    val original: String,
    val large: String,
    val small: String
)