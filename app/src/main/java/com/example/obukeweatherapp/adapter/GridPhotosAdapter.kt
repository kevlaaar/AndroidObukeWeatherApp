package com.example.obukeweatherapp.adapter

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.obukeweatherapp.R
import com.example.obukeweatherapp.models.GalleryPhoto

class GridPhotosAdapter(val context: Context, val photoList: List<GalleryPhoto>) :
    RecyclerView.Adapter<GridPhotosAdapter.GridPhotoViewHolder>() {


    inner class GridPhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val photoImageView: ImageView = itemView.findViewById(R.id.gridPhotoImageView)

        fun bind(photoItem: GalleryPhoto) {
            Glide.with(context)
                .load(photoItem.contentUri)
                .into(photoImageView)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridPhotoViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.list_item_grid_photo, parent, false)
        return GridPhotoViewHolder(view)
    }

    override fun onBindViewHolder(holder: GridPhotoViewHolder, position: Int) {
        holder.bind(photoList[position])
    }

    override fun getItemCount(): Int {
        return photoList.size
    }
}