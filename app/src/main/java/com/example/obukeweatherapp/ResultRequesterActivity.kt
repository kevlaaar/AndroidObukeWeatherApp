package com.example.obukeweatherapp

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class ResultRequesterActivity: AppCompatActivity() {

    lateinit var getResultButton: Button
    lateinit var resultTextView: TextView

    val ACTIVITY_REQUEST_CODE = 11

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_requester)
        getResultButton = findViewById(R.id.goForResultButton)
        resultTextView = findViewById(R.id.valueTextView)

        getResultButton.setOnClickListener{
            val intent = Intent(this, ResultSenderActivity::class.java)
            startActivityForResult(intent, ACTIVITY_REQUEST_CODE)
        }


        checkForLocationPermission()

        val bitamp = BitmapFactory.decodeResource(resources, R.drawable.icon_sunny_big)
        if(checkForLocationPermission())savePhotoToExternalStorage("sunny", bitamp)
    }

    private fun checkForLocationPermission(): Boolean {
        val coarseLocationPermission = ContextCompat.checkSelfPermission(
            this, Manifest.permission.READ_EXTERNAL_STORAGE
        )

        val fineLocationPermission = ContextCompat.checkSelfPermission(
            this, Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        val listPermissionsNeeded = ArrayList<String>()

        if (coarseLocationPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }

        if (fineLocationPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (listPermissionsNeeded.size > 0) {
            ActivityCompat.requestPermissions(
                this,
                listPermissionsNeeded.toArray(arrayOfNulls<String>(listPermissionsNeeded.size)),
                123
            )
            return false
        }
        return true

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == ACTIVITY_REQUEST_CODE) {
            if(resultCode == Activity.RESULT_OK) {
                val result = data?.getStringExtra("RESULT")
                result?.let {
                    resultTextView.text = result
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                val value = "NO VALUE PASSED"
                resultTextView.text = value
            }
        }
    }
    private fun savePhotoToExternalStorage(displayName: String, bmp: Bitmap): Boolean {
        val imageCollection = if(Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        } else { MediaStore.Images.Media.EXTERNAL_CONTENT_URI }

        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "$displayName.jpg")
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.WIDTH, bmp.width)
            put(MediaStore.Images.Media.HEIGHT, bmp.height)
        }
        return try {
            val imageUri = contentResolver.insert(imageCollection, contentValues)
            imageUri?.let { uri ->
                val os = contentResolver.openOutputStream(uri)
                os?.let{ outputStream ->
                    if(!bmp.compress(Bitmap.CompressFormat.JPEG, 95, outputStream)) {
                        throw IOException("Couldn't save bitmap")
                    }
                }
            } ?: throw IOException("Couldn't create MediaStore entry")
            true
        } catch(e: IOException) {
            e.printStackTrace()
            false
        }
    }
}