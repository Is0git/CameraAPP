package com.android.cameraapp.ui.base_activity.photos_fragment

import androidx.lifecycle.ViewModel
import com.android.cameraapp.di.base_activity.home_fragment.HomeFragmentScope
import com.android.cameraapp.di.base_activity.photo_fragment.PhotoFragmentScope
import javax.inject.Inject

@PhotoFragmentScope
class PhotosFragmentViewModel @Inject constructor(val repo: PhotosFragmentRepository) : ViewModel() {
    val photoPagedList = repo.pagedList
}