package com.android.cameraapp.ui.base_activity.photos_fragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.android.cameraapp.data.data_models.UserCollection
import com.android.cameraapp.di.base_activity.home_fragment.HomeFragmentScope
import com.android.cameraapp.di.base_activity.photo_fragment.PhotoFragmentScope
import javax.inject.Inject

@PhotoFragmentScope
class PhotosFragmentViewModel @Inject constructor(val repo: PhotosFragmentRepository) : ViewModel() {
    val photoPageList = repo.pagedList.also { liveData -> liveData.value?.get(1)?.storage_url  }

    init {
        Log.d(TAG, "LOAD VIEWMODEL")
        repo.pagedList.value?.get(1)?.storage_url
    }
}