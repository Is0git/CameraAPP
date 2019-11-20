package com.android.cameraapp.ui.base_activity.followers_fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.cameraapp.data.data_models.DataFlat
import com.android.cameraapp.data.data_models.UserCollection
import com.android.cameraapp.databinding.FollowersRecyclerviewBinding
import com.android.cameraapp.di.base_activity.followers_fragment.FollowersFragmentScope
import com.android.cameraapp.ui.base_activity.home_fragment.HomeFragmentListener
import javax.inject.Inject

@FollowersFragmentScope
class FollowersAdapter @Inject constructor() :
    ListAdapter<DataFlat.Followers, FollowersAdapter.MyViewHolder>(differ) {

    lateinit var listener: HomeFragmentListener<UserCollection.User>

    class MyViewHolder(val binding: FollowersRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            FollowersRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.listener = this.listener
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.item = item
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

val differ = AsyncDifferConfig.Builder<DataFlat.Followers>(callback).build()