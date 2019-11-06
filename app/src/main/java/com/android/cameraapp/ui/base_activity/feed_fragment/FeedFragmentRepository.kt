package com.android.cameraapp.ui.base_activity.feed_fragment

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.android.cameraapp.data.data_models.DataFlat
import com.android.cameraapp.di.base_activity.feed_fragment.FeedFragmentScope
import javax.inject.Inject

@FeedFragmentScope
data class FeedFragmentRepository @Inject constructor(val pagedList: LiveData<PagedList<DataFlat.PhotosWithUser>>, val dataSource: PhotosWithUserDataSource)