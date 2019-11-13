package com.android.cameraapp.ui.base_activity.followers_fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import com.android.cameraapp.data.data_models.DataFlat
import com.android.cameraapp.di.base_activity.followers_fragment.FollowersFragmentScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@FollowersFragmentScope
class FollowersViewModel @Inject constructor(
    val repo: FollowersRepository) : ViewModel() {

    val mediatorFollowers = repo.mediatorLiveData

    init {
        repo.getData()

        viewModelScope.launch { repo.getAllFollowers() }
    }
    override fun onCleared() {
        super.onCleared()
        repo.clearListener()
    }


}
