package com.android.cameraapp.ui.base_activity.photos_fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.cameraapp.data.data_models.UserCollection
import com.android.cameraapp.databinding.PhotosListLayoutBinding
import com.android.cameraapp.di.base_activity.photo_fragment.PhotoFragmentScope
import javax.inject.Inject
@PhotoFragmentScope
class PhotosAdapter @Inject constructor() :
    PagedListAdapter<UserCollection.Photos, PhotosAdapter.MyViewHolder>(diffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            PhotosListLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.photoData = item
    }

    class MyViewHolder(val binding: PhotosListLayoutBinding) : RecyclerView.ViewHolder(binding.root)


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
