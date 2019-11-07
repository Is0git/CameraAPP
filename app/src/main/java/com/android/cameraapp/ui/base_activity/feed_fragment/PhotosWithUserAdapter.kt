package com.android.cameraapp.ui.base_activity.feed_fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.view.postDelayed
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.cameraapp.R
import com.android.cameraapp.data.data_models.DataFlat
import com.android.cameraapp.databinding.FeedListLayoutBinding
import com.android.cameraapp.di.base_activity.feed_fragment.FeedFragmentScope
import com.android.cameraapp.util.FeedFragmentOnClickListener
import javax.inject.Inject

@FeedFragmentScope
class PhotosWithUserAdapter @Inject constructor() :
    PagedListAdapter<DataFlat.PhotosWithUser, PhotosWithUserAdapter.MyViewHolder>(
        callback
    ) {
    lateinit var onClickHandler: FeedFragmentOnClickListener

    class MyViewHolder(val binding: FeedListLayoutBinding, var isStarted: Boolean = false) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.feedCardShape.setOnClickListener {
                if (!isStarted) {
                    binding.constraintLayout5.apply {
                        setTransition(R.id.start, R.id.end)
                        transitionToEnd()
                    }
                    isStarted = true
                } else {
                    binding.constraintLayout5.apply {
                        setTransition(R.id.end2, R.id.start)
                        transitionToEnd()
                    }
                    isStarted = false

                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PhotosWithUserAdapter.MyViewHolder {
        val binding =
            FeedListLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.click = onClickHandler
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