package com.android.cameraapp.ui.base_activity.likes_fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.android.cameraapp.di.base_activity.likes_fragment.LikesFragmentScope
import com.android.cameraapp.util.States
import javax.inject.Inject

@LikesFragmentScope
class LikesFragmentViewModel @Inject constructor(val repo: LikesFragmentRepository) : ViewModel() {
    val statesLiveData: LiveData<States> = repo.taskState
    val likes = repo.liveLikeList

    override fun onCleared() {
        super.onCleared()
        repo.clearListener()
    }
}