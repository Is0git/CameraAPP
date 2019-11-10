package com.android.cameraapp.ui.base_activity.full_picture_fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.cameraapp.data.data_models.DataFlat
import com.android.cameraapp.databinding.FullPhotoListBinding
import com.android.cameraapp.di.base_activity.full_picture_fragment.FullPictureScope
import javax.inject.Inject

@FullPictureScope
class CommentsListAdapter @Inject constructor() : ListAdapter<DataFlat.CommentsWithUser, CommentsListAdapter.MyViewHolder>(
    callback) {
    class MyViewHolder(val binding: FullPhotoListBinding) : RecyclerView.ViewHolder(binding.root)



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CommentsListAdapter.MyViewHolder {
        val binding = FullPhotoListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentsListAdapter.MyViewHolder, position: Int) {
            val item = getItem(position)
            holder.binding.item = item
    }
}

val callback = object : DiffUtil.ItemCallback<DataFlat.CommentsWithUser>() {
    override fun areItemsTheSame(
        oldItem: DataFlat.CommentsWithUser,
        newItem: DataFlat.CommentsWithUser
    ): Boolean = oldItem.comment_id == newItem.comment_id
    override fun areContentsTheSame(
        oldItem: DataFlat.CommentsWithUser,
        newItem: DataFlat.CommentsWithUser
    ): Boolean = oldItem == newItem

}