package com.android.cameraapp.ui.base_activity.likes_fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.cameraapp.data.data_models.DataFlat
import com.android.cameraapp.databinding.LikesRecyclerviewBinding

class LikesAdapter : PagedListAdapter<DataFlat.Likes, LikesAdapter.MyViewHolder>(callback) {
     class MyViewHolder(val binding: LikesRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LikesAdapter.MyViewHolder {
                val binding =
            LikesRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
    override fun areItemsTheSame(oldItem: DataFlat.Likes, newItem: DataFlat.Likes): Boolean = oldItem.user.uid == newItem.user.uid
    override fun areContentsTheSame(oldItem: DataFlat.Likes, newItem: DataFlat.Likes): Boolean = oldItem == newItem

}