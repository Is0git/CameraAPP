package com.android.cameraapp.ui.base_activity.photos_fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.cameraapp.data.data_models.DataFlat
import com.android.cameraapp.data.data_models.UserCollection
import com.android.cameraapp.databinding.PhotosListLayoutBinding
import com.android.cameraapp.di.base_activity.photo_fragment.PhotoFragmentScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@PhotoFragmentScope
class PhotosAdapter @Inject constructor() :
    RecyclerView.Adapter<PhotosAdapter.MyViewHolder>() {
    var items = mutableListOf<DataFlat.PhotosWithUser>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            PhotosListLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.clickListener = listeners
        return MyViewHolder(binding)
    }

     lateinit var listeners:PhotosFragmentListeners
    fun addItems(item: List<DataFlat.PhotosWithUser>) {

        if (item.size == 1) {
            items.add(0, item[0])
            notifyItemInserted(0)
        } else if (item.size > 1) {
            items = item.toMutableList()
            notifyDataSetChanged()
        }

    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = items[position]
        holder.binding.photoData = item
    }

    class MyViewHolder(val binding: PhotosListLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int = items.size

    suspend fun oldestFilter() = coroutineScope {
        items.sortBy { it.time_in_long }
        launch(Dispatchers.Main) { notifyDataSetChanged() }

    }


    suspend fun newestFilter() = coroutineScope {
        items.sortByDescending { it.time_in_long }
        launch(Dispatchers.Main) { notifyDataSetChanged() }

    }

    suspend fun mostPopularFilter() = coroutineScope {
        items.sortByDescending { it.comments_number  }
        launch(Dispatchers.Main) { notifyDataSetChanged() }

    }
}


val diffUtil = object : DiffUtil.ItemCallback<DataFlat.PhotosWithUser>() {
    override fun areItemsTheSame(
        oldItem: DataFlat.PhotosWithUser,
        newItem: DataFlat.PhotosWithUser
    ): Boolean = oldItem.photo_id == newItem.photo_id


    override fun areContentsTheSame(
        oldItem: DataFlat.PhotosWithUser,
        newItem: DataFlat.PhotosWithUser
    ): Boolean = oldItem == newItem

}
