package com.android.cameraapp.ui.base_activity.photos_fragment

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.cameraapp.databinding.PhotosListLayoutBinding
import com.bumptech.glide.Glide

class PhotosAdapter(val data: List<String>, val context: Context) :
    RecyclerView.Adapter<PhotosAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            PhotosListLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(
            binding
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Log.d("TEST", "${data[position]}")
        Glide.with(context).load(data[position]).centerCrop()


            .into(holder.binding.AuthorName)
    }

    class MyViewHolder(val binding: PhotosListLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }
}
