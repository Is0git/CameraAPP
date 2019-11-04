package com.android.cameraapp.ui.base_activity.following_fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.cameraapp.data.data_models.UserCollection
import com.android.cameraapp.databinding.FollowingRecyclerviewBinding

class FollowingAdapter : PagedListAdapter<UserCollection.Following, FollowingAdapter.MyViewHolder>(
    diffUtil) {
    class MyViewHolder(val binding: FollowingRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val binding =
        FollowingRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return MyViewHolder(
        binding
    )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.item = item

    }


}
    val diffUtil = object : DiffUtil.ItemCallback<UserCollection.Following>() {
        override fun areItemsTheSame(
            oldItem: UserCollection.Following,
            newItem: UserCollection.Following
        ): Boolean = oldItem.user_uid == newItem.user_uid
        override fun areContentsTheSame(
            oldItem: UserCollection.Following,
            newItem: UserCollection.Following
        ): Boolean = oldItem == newItem

    }
