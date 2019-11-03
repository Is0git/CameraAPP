package com.android.cameraapp.ui.base_activity.photos_fragment

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.cameraapp.data.data_models.UserCollection
import com.android.cameraapp.databinding.PhotosListLayoutBinding
import com.bumptech.glide.Glide
import java.util.*

class PhotosAdapter : PagedListAdapter<UserCollection.Photos, PhotosAdapter.MyViewHolder>(diffUtil) {
    class MyViewHolder : RecyclerView.ViewHolder() {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}


val diffUtil =  object : DiffUtil.ItemCallback<UserCollection.Photos>() {
    override fun areItemsTheSame(
        oldItem: UserCollection.Photos,
        newItem: UserCollection.Photos
    ): Boolean = oldItem.photo_id == newItem.photo_id


    override fun areContentsTheSame(
        oldItem: UserCollection.Photos,
        newItem: UserCollection.Photos
    ): Boolean = oldItem == newItem

}
//override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
//        val binding =
//            PhotosListLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return MyViewHolder(
//            binding
//        )
//    }
//
//    override fun getItemCount(): Int {
//        return data.size
//    }
//
//    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        Log.d("TEST", "${data[position]}")
//        Glide.with(context).load(data[position]).centerCrop()
//
//
//            .into(holder.binding.AuthorName)
//    }
//
//    class MyViewHolder(val binding: PhotosListLayoutBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//
//    }