package com.android.cameraapp.ui.base_activity.following_fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.cameraapp.data.data_models.DataFlat
import com.android.cameraapp.data.data_models.UserCollection
import com.android.cameraapp.databinding.FollowingRecyclerviewBinding
import com.android.cameraapp.di.base_activity.following_fragment.FollowingFragmentScope
import com.android.cameraapp.ui.base_activity.home_fragment.HomeFragmentListener
import javax.inject.Inject

@FollowingFragmentScope
class FollowingAdapter @Inject constructor() :
    PagedListAdapter<DataFlat.Following, FollowingAdapter.MyViewHolder>(
        diffUtil
    ) {

    lateinit var listener: HomeFragmentListener<UserCollection.User>

    class MyViewHolder(val binding: FollowingRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            FollowingRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.listener = listener
        return MyViewHolder(
            binding
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.item = item

    }


}

val diffUtil = object : DiffUtil.ItemCallback<DataFlat.Following>() {
    override fun areItemsTheSame(
        oldItem: DataFlat.Following,
        newItem: DataFlat.Following
    ): Boolean = oldItem.user?.uid == newItem.user?.uid

    override fun areContentsTheSame(
        oldItem: DataFlat.Following,
        newItem: DataFlat.Following
    ): Boolean = oldItem == newItem

}
