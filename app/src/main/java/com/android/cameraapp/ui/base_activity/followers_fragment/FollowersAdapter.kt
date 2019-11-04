package com.android.cameraapp.ui.base_activity.followers_fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.cameraapp.data.data_models.UserCollection
import com.android.cameraapp.databinding.FollowersRecyclerviewBinding

class FollowersAdapter :
    PagedListAdapter<UserCollection.Followers, FollowersAdapter.MyViewHolder>(callback) {
    class MyViewHolder(val binding: FollowersRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = FollowersRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val item = getItem(position)
            holder.binding.item = item
    }


}

val callback = object : DiffUtil.ItemCallback<UserCollection.Followers>() {
    override fun areItemsTheSame(
        oldItem: UserCollection.Followers,
        newItem: UserCollection.Followers
    ): Boolean = oldItem.follower_uid == newItem.follower_uid

    override fun areContentsTheSame(
        oldItem: UserCollection.Followers,
        newItem: UserCollection.Followers
    ): Boolean = oldItem == newItem

}
