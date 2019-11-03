package com.android.cameraapp.ui.base_activity.home_fragment

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.android.cameraapp.data.data_models.UserCollection
import com.android.cameraapp.di.base_activity.home_fragment.HomeFragmentScope
import javax.inject.Inject

@HomeFragmentScope
class HomeFragmentRepository @Inject constructor(val pagedList: LiveData<PagedList<UserCollection.Photos>>)