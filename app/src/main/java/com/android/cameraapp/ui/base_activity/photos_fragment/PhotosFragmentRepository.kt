package com.android.cameraapp.ui.base_activity.photos_fragment

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.android.cameraapp.data.data_models.UserCollection
import com.android.cameraapp.di.base_activity.home_fragment.HomeFragmentScope
import com.android.cameraapp.di.base_activity.photo_fragment.PhotoFragmentScope
import javax.inject.Inject

@PhotoFragmentScope
class PhotosFragmentRepository @Inject constructor(val pagedList: LiveData<PagedList<UserCollection.Photos>>)