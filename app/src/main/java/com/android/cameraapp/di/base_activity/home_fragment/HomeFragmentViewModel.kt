package com.android.cameraapp.di.base_activity.home_fragment

import androidx.lifecycle.ViewModel
import com.android.cameraapp.ui.base_activity.home_fragment.HomeFragmentRepository
import javax.inject.Inject

@HomeFragmentScope
class HomeFragmentViewModel @Inject constructor(private val repo: HomeFragmentRepository) : ViewModel() {
    val photoPagedList = repo.pagedList
}