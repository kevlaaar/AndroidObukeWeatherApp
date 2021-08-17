package com.example.obukeweatherapp.models

import android.net.Uri

class GalleryPhoto(
    val id: Long,
    val displayName: String,
    val width: Int,
    val height: Int,
    val contentUri: Uri
)