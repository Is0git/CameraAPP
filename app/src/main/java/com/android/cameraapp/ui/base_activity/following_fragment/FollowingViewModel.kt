package com.android.cameraapp.ui.base_activity.following_fragment

import androidx.lifecycle.ViewModel
import com.android.cameraapp.di.base_activity.following_fragment.FollowingFragmentScope
import javax.inject.Inject

@FollowingFragmentScope
class FollowingViewModel @Inject constructor(val repo: FollowingRepository) : ViewModel() {
    val pagelist = repo.pagedList
}