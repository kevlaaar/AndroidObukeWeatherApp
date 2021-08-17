package com.example.obukeweatherapp

import android.content.ContentUris
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.obukeweatherapp.adapter.GridPhotosAdapter
import com.example.obukeweatherapp.models.GalleryPhoto

class DownloadedPhotosActivity: AppCompatActivity() {

    lateinit var gridRecycler: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_downloaded_photos)
        gridRecycler = findViewById(R.id.gridRecyclerView)
        loadPhotosFromInternalStorage()
    }

    private fun loadPhotosFromInternalStorage() {
        val imageCollection = if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        } else {
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }
        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.WIDTH,
            MediaStore.Images.Media.HEIGHT
        )
        val photoList = mutableListOf<GalleryPhoto>()

        val cursor = contentResolver.query(imageCollection, projection, null, null, null)
        cursor?.let { nonNullCursor ->
            val idColumn = nonNullCursor.getColumnIndex(MediaStore.Images.Media._ID)
            val displayNameColumn = nonNullCursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)
            val widthColumn = nonNullCursor.getColumnIndex(MediaStore.Images.Media.WIDTH)
            val heightColumn = nonNullCursor.getColumnIndex(MediaStore.Images.Media.HEIGHT)

            cursor.moveToLast()
            do {
                val id = cursor.getLong(idColumn)
                val displayName = cursor.getString(displayNameColumn)
                val width = cursor.getInt(widthColumn)
                val height = cursor.getInt(heightColumn)
                val contentUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)

                photoList.add(GalleryPhoto(id, displayName, width, height, contentUri))
            } while (cursor.moveToPrevious())

            loadTheRecycler(photoList)
        }
        cursor?.close()

    }

    private fun loadTheRecycler(photoList: List<GalleryPhoto>) {
        val gridAdapter = GridPhotosAdapter(this, photoList)
        gridRecycler.adapter = gridAdapter
        gridRecycler.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
//        (gridRecycler.layoutManager as StaggeredGridLayoutManager).reverseLayout = true
//        (gridRecycler.layoutManager as StaggeredGridLayoutManager).scrollToPosition(0)
    }
}