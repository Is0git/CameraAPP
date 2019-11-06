package com.android.cameraapp.ui.base_activity.likes_fragment

import androidx.lifecycle.ViewModel
import com.android.cameraapp.di.base_activity.likes_fragment.LikesFragmentScope
import javax.inject.Inject

@LikesFragmentScope
class LikesFragmentViewModel @Inject constructor(val repo: LikesFragmentRepository, val dataSource: LikesFragmentDataSource): ViewModel() {
    val pagedList = repo.pagelist

    override fun onCleared() {
        super.onCleared()
        dataSource.cancelJob()
    }
}