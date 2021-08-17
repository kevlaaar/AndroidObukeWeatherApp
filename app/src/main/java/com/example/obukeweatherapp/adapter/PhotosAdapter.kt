package com.example.obukeweatherapp.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.obukeweatherapp.R
import com.example.obukeweatherapp.models.Photos

class PhotosAdapter(val context: Context, val listOfPhotos: List<Photos>, val photoOnClickListener: PhotoOnClickListener): RecyclerView.Adapter<PhotosAdapter.PhotoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_list_photo, parent, false)
        return PhotoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(listOfPhotos[position])
    }

    override fun getItemCount(): Int {
        return listOfPhotos.size
    }

    inner class PhotoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private val photoImageView: ImageView = itemView.findViewById(R.id.photoImageView)
        private val photographerName: TextView = itemView.findViewById(R.id.photographerName)
        private val downloadImage: ImageView = itemView.findViewById(R.id.downloadIcon)

        fun bind(photoItem: Photos) {
            photographerName.text = photoItem.photographerName
            Glide.with(context)
                .asBitmap()
                .load(photoItem.photoResources.original)
                .listener(object: RequestListener<Bitmap> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Bitmap>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        Log.e("Glide", "Image load failed $e")
                        return false
                    }

                    override fun onResourceReady(
                        resource: Bitmap?,
                        model: Any?,
                        target: Target<Bitmap>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        resource?.let { bitmap ->
                            downloadImage.setOnClickListener{
                                photoOnClickListener.onPhotoClick(bitmap)
                            }
                        }
                        return false
                    }
                })
                .into(photoImageView)

        }
    }
}
interface PhotoOnClickListener {
    fun onPhotoClick(photoBitmap: Bitmap)
}