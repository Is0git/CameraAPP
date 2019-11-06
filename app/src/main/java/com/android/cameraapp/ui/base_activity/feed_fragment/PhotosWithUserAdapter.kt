package com.android.cameraapp.ui.base_activity.feed_fragment

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.cameraapp.data.data_models.DataFlat
import com.android.cameraapp.databinding.FeedListLayoutBinding
import javax.inject.Inject

class PhotosWithUserAdapter @Inject constructor() : PagedListAdapter<DataFlat.PhotosWithUser, PhotosWithUserAdapter.MyViewHolder>(
    callback) {
    class MyViewHolder(val binding: FeedListLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PhotosWithUserAdapter.MyViewHolder {
        ]
    }

    override fun onBindViewHolder(holder: PhotosWithUserAdapter.MyViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

val callback = object : DiffUtil.ItemCallback<DataFlat.PhotosWithUser>() {
    override fun areItemsTheSame(
        oldItem: DataFlat.PhotosWithUser,
        newItem: DataFlat.PhotosWithUser
    ): Boolean = oldItem.photo_id == newItem.photo_id
    override fun areContentsTheSame(
        oldItem: DataFlat.PhotosWithUser,
        newItem: DataFlat.PhotosWithUser
    ): Boolean = oldItem == newItem

}