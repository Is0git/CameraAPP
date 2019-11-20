package com.android.cameraapp.ui.base_activity.following_fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.android.cameraapp.di.base_activity.following_fragment.FollowingFragmentScope
import com.android.cameraapp.util.States
import javax.inject.Inject

@FollowingFragmentScope
class FollowingViewModel @Inject constructor(val repo: FollowingRepository, val dataSource: FollowingDataSource) : ViewModel() {
    val pagelist = repo.pagedList
    val statesLiveData: LiveData<States> = repo.statesLiveData
    override fun onCleared() {
        dataSource.cancelJobs()
        super.onCleared()
    }
}