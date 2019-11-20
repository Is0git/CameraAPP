package com.android.cameraapp.ui.base_activity.likes_fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.cameraapp.data.data_models.DataFlat
import com.android.cameraapp.data.data_models.UserCollection
import com.android.cameraapp.databinding.LikesRecyclerviewBinding
import com.android.cameraapp.di.base_activity.likes_fragment.LikesFragmentScope
import com.android.cameraapp.ui.base_activity.home_fragment.HomeFragmentListener
import javax.inject.Inject

@LikesFragmentScope
class LikesAdapter @Inject constructor() : ListAdapter<DataFlat.Likes, LikesAdapter.MyViewHolder>(
    async
) {
    class MyViewHolder(val binding: LikesRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    lateinit var listener: HomeFragmentListener<UserCollection.User>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LikesAdapter.MyViewHolder {
        val binding =
            LikesRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.listener = this.listener
        return MyViewHolder(
            binding
        )
    }

    override fun onBindViewHolder(holder: LikesAdapter.MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.item = item
    }


}

val callback = object : DiffUtil.ItemCallback<DataFlat.Likes>() {
    override fun areItemsTheSame(oldItem: DataFlat.Likes, newItem: DataFlat.Likes): Boolean =
        oldItem.user?.uid == newItem.user?.uid

    override fun areContentsTheSame(oldItem: DataFlat.Likes, newItem: DataFlat.Likes): Boolean =
        oldItem == newItem

}

val async = AsyncDifferConfig.Builder<DataFlat.Likes>(callback).build()