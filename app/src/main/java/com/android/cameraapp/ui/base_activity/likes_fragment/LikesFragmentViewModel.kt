package com.android.cameraapp.ui.base_activity.likes_fragment

import androidx.lifecycle.ViewModel
import com.android.cameraapp.di.base_activity.likes_fragment.LikesFragmentScope
import javax.inject.Inject

@LikesFragmentScope
class LikesFragmentViewModel @Inject constructor(val repo: LikesFragmentRepository): ViewModel() {

    val likes = repo.liveLikeList

    override fun onCleared() {
        super.onCleared()
        repo.clearListener()
    }
}