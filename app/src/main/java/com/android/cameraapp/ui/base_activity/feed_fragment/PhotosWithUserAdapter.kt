package com.android.cameraapp.ui.base_activity.feed_fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.cameraapp.data.data_models.DataFlat
import com.android.cameraapp.databinding.FeedListLayoutBinding
import com.android.cameraapp.di.base_activity.feed_fragment.FeedFragmentScope
import javax.inject.Inject
@FeedFragmentScope
class PhotosWithUserAdapter @Inject constructor() : PagedListAdapter<DataFlat.PhotosWithUser, PhotosWithUserAdapter.MyViewHolder>(
    callback) {
    class MyViewHolder(val binding: FeedListLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PhotosWithUserAdapter.MyViewHolder {
        val binding = FeedListLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotosWithUserAdapter.MyViewHolder, position: Int) {
       val item = getItem(position)
        holder.binding.item = item
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