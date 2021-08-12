package com.example.obukeweatherapp

import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.obukeweatherapp.adapter.PhotosAdapter
import com.example.obukeweatherapp.models.Photos
import com.example.obukeweatherapp.models.PhotosResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.net.URL


class PhotosApiActivity : AppCompatActivity() {

    lateinit var photosRecycler: RecyclerView
    lateinit var photosAdapter: PhotosAdapter
    lateinit var downloadButton: Button
    lateinit var photoList: List<Photos>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photos_api)

        photosRecycler = findViewById(R.id.photosRecycler)
        downloadButton = findViewById(R.id.downloadPhotoButton)
        photosRecycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val photosCall = ApiInterface.create().getCuratedPhotos()

        photosCall.enqueue(object : Callback<PhotosResponse> {
            override fun onResponse(
                call: Call<PhotosResponse>,
                response: Response<PhotosResponse>
            ) {
                response.body()?.let {
                    photoList = it.photosData
                    loadTheAdapter(it.photosData)
                }
            }

            override fun onFailure(call: Call<PhotosResponse>, t: Throwable) {

            }
        })
        downloadButton.setOnClickListener {
            if (requestExternalStoragePermissions()) {
                val position =
                    (photosRecycler.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                downloadThePhoto(position)
            }
        }
    }

    private fun loadTheAdapter(listOfPhotos: List<Photos>) {
        photosAdapter = PhotosAdapter(this, listOfPhotos)
        photosRecycler.adapter = photosAdapter
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(photosRecycler)
    }

    private fun downloadThePhoto(position: Int) {
        val photoItem = photoList[position]
        bitmapFromUrl(photoItem.photoResources.original, photoItem.photographerName)

    }
    private fun bitmapFromUrl(url: String, photographer: String) {
        val thread = Thread {
            val bitmap: Bitmap
            // Ur URL
            try {
                val url = URL(url)
                bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream())
                // UI component
                savePhotoToInternalStorage(photographer, bitmap)
            } catch (e: Exception) {
//                Log.e("error message", Objects.requireNonNull(e.message))
            }
        }
        thread.start()
    }

    private val STORAGE_PERMISSIONS_CODE = 123
    private fun requestExternalStoragePermissions(): Boolean {
        val readStoragePermission = ContextCompat.checkSelfPermission(
            this, Manifest.permission.READ_EXTERNAL_STORAGE
        )
        val writeStoragePermission = ContextCompat.checkSelfPermission(
            this, Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        val listPermissionsNeeded = ArrayList<String>()

        if (readStoragePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }


        if (writeStoragePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }

        if (listPermissionsNeeded.size > 0) {
            ActivityCompat.requestPermissions(
                this,
                listPermissionsNeeded.toArray(arrayOfNulls<String>(listPermissionsNeeded.size)),
                STORAGE_PERMISSIONS_CODE
            )
            return false
        }
        return true
    }

    private fun savePhotoToInternalStorage(displayName: String, bitmap: Bitmap) {
        val imageCollection = if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        } else {
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }

        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "$displayName.jpg")
            put(MediaStore.Images.Media.WIDTH, bitmap.width)
            put(MediaStore.Images.Media.HEIGHT, bitmap.height)
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        }

        val imageUri = contentResolver.insert(imageCollection, contentValues)
        imageUri?.let { uri ->
            val outputStream = contentResolver.openOutputStream(uri)
            outputStream?.let { os ->
                if (!bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)) {
                    throw IOException("Couldn't save image.")
                }
            }
        } ?: throw IOException("Couldn't create Media Store")

    }

}