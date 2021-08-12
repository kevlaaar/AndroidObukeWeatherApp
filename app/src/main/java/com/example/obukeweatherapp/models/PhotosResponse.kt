package com.example.obukeweatherapp.models

import com.google.gson.annotations.SerializedName

data class PhotosResponse(
    @SerializedName("photos")
    val photosData: List<Photos>
)