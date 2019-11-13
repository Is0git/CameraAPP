package com.android.cameraapp.ui.base_activity.followers_fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.cameraapp.data.data_models.DataFlat
import com.android.cameraapp.data.data_models.UserCollection
import com.android.cameraapp.databinding.FollowersRecyclerviewBinding
import com.android.cameraapp.di.base_activity.followers_fragment.FollowersFragmentScope
import javax.inject.Inject

@FollowersFragmentScope
class FollowersAdapter @Inject constructor() :
    RecyclerView.Adapter<FollowersAdapter.MyViewHolder>() {

    var followers = mutableListOf<DataFlat.Followers>()

    class MyViewHolder(val binding: FollowersRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            FollowersRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = followers[position]
        holder.binding.item = item
    }

    override fun getItemCount(): Int =
        followers.size


    fun addItems(items: List<DataFlat.Followers>) {
        if (items.isNotEmpty()) {
            followers = items.toMutableList()
            notifyDataSetChanged()
        }

    }

    fun setNewItem(item: DataFlat.Followers) {
        if (followers.isNotEmpty()) {
            followers.add(0, item)
            notifyItemChanged(0)
        }
    }



    fun addUserToFollower(item: DataFlat.Followers) {

            followers.filterIndexed { index, followers ->
                notifyItemChanged(index)
                followers.follower_uid == item.user?.uid }.forEach { it.user = item.user }
        }

}

val callback = object : DiffUtil.ItemCallback<DataFlat.Followers>() {
    override fun areItemsTheSame(
        oldItem: DataFlat.Followers,
        newItem: DataFlat.Followers
    ): Boolean = oldItem.follower_uid == newItem.follower_uid

    override fun areContentsTheSame(
        oldItem: DataFlat.Followers,
        newItem: DataFlat.Followers
    ): Boolean = oldItem == newItem

}
