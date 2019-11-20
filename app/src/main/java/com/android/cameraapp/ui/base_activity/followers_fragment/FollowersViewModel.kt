package com.android.cameraapp.ui.base_activity.followers_fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.android.cameraapp.di.base_activity.followers_fragment.FollowersFragmentScope
import com.android.cameraapp.util.States
import javax.inject.Inject

@FollowersFragmentScope
class FollowersViewModel @Inject constructor(
    val repo: FollowersRepository
) : ViewModel() {
    val statesLiveData: LiveData<States> = repo.taskState
    val mediatorFollowers = repo.mediatorLiveData

    fun init(userId: String?) {
        repo.queryUserID = userId
    }

    override fun onCleared() {
        super.onCleared()
        repo.clearListener()
    }


}
