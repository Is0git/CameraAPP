package com.android.cameraapp.ui.base_activity.home_fragment

import androidx.paging.DataSource
import com.android.cameraapp.data.data_models.UserCollection
import com.android.cameraapp.di.base_activity.home_fragment.HomeFragmentScope
import com.android.cameraapp.di.base_activity.photo_fragment.PhotoFragmentScope
import javax.inject.Inject

@PhotoFragmentScope
class PhotoDataFactory @Inject constructor(private val dataSource: PhotosDataSource) : DataSource.Factory<Int, UserCollection.Photos>() {
    override fun create(): DataSource<Int, UserCollection.Photos> {
       return dataSource
    }
}