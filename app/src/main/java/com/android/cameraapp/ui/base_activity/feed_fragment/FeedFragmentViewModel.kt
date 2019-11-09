package com.android.cameraapp.ui.base_activity.feed_fragment

import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.cameraapp.data.data_models.DataFlat
import com.android.cameraapp.di.base_activity.feed_fragment.FeedFragmentScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@FeedFragmentScope
class FeedFragmentViewModel @Inject constructor(val repo: FeedFragmentRepository) : ViewModel() {

    val pagedList = repo.pagedList

    override fun onCleared() {
        super.onCleared()
        repo.dataSource.cancelJob()
    }

    fun likePhoto(data: DataFlat.PhotosWithUser, likesCount: TextView) = viewModelScope.launch {
        repo.likePhoto(data, likesCount)
    }
}