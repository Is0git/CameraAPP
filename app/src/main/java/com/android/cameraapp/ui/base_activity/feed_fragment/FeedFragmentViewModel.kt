package com.android.cameraapp.ui.base_activity.feed_fragment

import android.view.View
import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.cameraapp.data.data_models.DataFlat
import com.android.cameraapp.di.base_activity.feed_fragment.FeedFragmentScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@FeedFragmentScope
class FeedFragmentViewModel @Inject constructor(val repo: FeedFragmentRepository) : ViewModel() {
    val states = repo.states
    val pagedList = repo.pagedList
    val loadStates = repo.dataSource.state

    override fun onCleared() {
        super.onCleared()
        repo.dataSource.cancelJob()
    }

    fun likePhoto(data: DataFlat.PhotosWithUser, likesCount: TextView, icon: View) =
        viewModelScope.launch {
            repo.likePhoto(data, likesCount, icon)
        }

    fun getFirstDocument() = viewModelScope.launch { repo.getFirstDocument() }

    fun removeLike(data: DataFlat.PhotosWithUser, likesCount: TextView, icon: View) =
        viewModelScope.launch { repo.dislikePhoto(data, likesCount, icon) }
}