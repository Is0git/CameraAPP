package com.android.cameraapp.ui.base_activity.followers_fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import com.android.cameraapp.data.data_models.DataFlat
import com.android.cameraapp.di.base_activity.followers_fragment.FollowersFragmentScope
import com.android.cameraapp.util.States
import kotlinx.coroutines.launch
import javax.inject.Inject

@FollowersFragmentScope
class FollowersViewModel @Inject constructor(
    val repo: FollowersRepository) : ViewModel() {
    val statesLiveData: LiveData<States> = repo.taskState
    val mediatorFollowers = repo.mediatorLiveData

    fun init(userId:String?) {
        repo.queryUserID = userId
    }

    override fun onCleared() {
        super.onCleared()
        repo.clearListener()
    }


}
