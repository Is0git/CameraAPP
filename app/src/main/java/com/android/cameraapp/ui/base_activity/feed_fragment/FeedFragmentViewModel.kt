package com.android.cameraapp.ui.base_activity.feed_fragment

import androidx.lifecycle.ViewModel
import com.android.cameraapp.di.base_activity.feed_fragment.FeedFragmentScope
import javax.inject.Inject

@FeedFragmentScope
class FeedFragmentViewModel @Inject constructor(val repo: FeedFragmentRepository) : ViewModel() {

    val pagedList = repo.pagedList

    override fun onCleared() {
        super.onCleared()
        repo.dataSource.cancelJob()
    }
}