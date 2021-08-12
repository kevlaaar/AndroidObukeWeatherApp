package com.example.obukeweatherapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.obukeweatherapp.R
import com.example.obukeweatherapp.models.Photos

class PhotosAdapter(val context: Context, val listOfPhotos: List<Photos>): RecyclerView.Adapter<PhotosAdapter.PhotoViewHolder>() {

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

        fun bind(photoItem: Photos) {
            photographerName.text = photoItem.photographerName
            Glide.with(context)
                .load(photoItem.photoResources.original)
                .into(photoImageView)
        }
    }
}