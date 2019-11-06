package com.android.cameraapp.ui.base_activity.feed_fragment

import androidx.paging.DataSource
import com.android.cameraapp.data.data_models.DataFlat
import com.android.cameraapp.di.base_activity.feed_fragment.FeedFragmentScope
import javax.inject.Inject

@FeedFragmentScope
class PhotosWithUserFactory @Inject constructor(val dataSource: PhotosWithUserDataSource) : DataSource.Factory<Int, DataFlat.PhotosWithUser>() {
    override fun create(): DataSource<Int, DataFlat.PhotosWithUser> {
        return dataSource
    }
}