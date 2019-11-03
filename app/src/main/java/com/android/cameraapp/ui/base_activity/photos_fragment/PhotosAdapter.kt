package com.android.cameraapp.ui.base_activity.photos_fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.cameraapp.data.data_models.UserCollection
import com.android.cameraapp.databinding.PhotosListLayoutBinding

class PhotosAdapter :
    PagedListAdapter<UserCollection.Photos, PhotosAdapter.MyViewHolder>(diffUtil) {
    class MyViewHolder(val binding: PhotosListLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            PhotosListLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}


val diffUtil = object : DiffUtil.ItemCallback<UserCollection.Photos>() {
    override fun areItemsTheSame(
        oldItem: UserCollection.Photos,
        newItem: UserCollection.Photos
    ): Boolean = oldItem.photo_id == newItem.photo_id


    override fun areContentsTheSame(
        oldItem: UserCollection.Photos,
        newItem: UserCollection.Photos
    ): Boolean = oldItem == newItem

}
