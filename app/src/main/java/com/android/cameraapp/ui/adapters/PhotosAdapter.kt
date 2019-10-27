package com.android.cameraapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.cameraapp.databinding.PhotosListLayoutBinding

class PhotosAdapter(val data:List<String>) : RecyclerView.Adapter<PhotosAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
   val binding = PhotosListLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.AuthorName.text = data[position]

    }

    class MyViewHolder(val binding: PhotosListLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

    }
}
