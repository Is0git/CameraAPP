package com.android.cameraapp.ui.base_activity.search_fragment


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.cameraapp.data.data_models.UserCollection
import com.android.cameraapp.databinding.SearchUserListBinding
import com.android.cameraapp.di.base_activity.search_fragment.SearchFragmentScope
import javax.inject.Inject

@SearchFragmentScope
class SearchAdapter @Inject constructor() :
    ListAdapter<UserCollection.User, SearchAdapter.MyViewHolder>(
        callback
    ) {
    class MyViewHolder(val binding: SearchUserListBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            SearchUserListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(
            binding
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.item = item
    }
}

val callback = object : DiffUtil.ItemCallback<UserCollection.User>() {
    override fun areItemsTheSame(
        oldItem: UserCollection.User,
        newItem: UserCollection.User
    ): Boolean = oldItem.uid == newItem.uid

    override fun areContentsTheSame(
        oldItem: UserCollection.User,
        newItem: UserCollection.User
    ): Boolean = oldItem == newItem
}