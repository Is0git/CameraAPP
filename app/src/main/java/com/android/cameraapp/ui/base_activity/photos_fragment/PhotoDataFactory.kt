package com.android.cameraapp.ui.base_activity.photos_fragment

import androidx.paging.DataSource
import com.android.cameraapp.data.data_models.UserCollection
import com.android.cameraapp.di.base_activity.photo_fragment.PhotoFragmentScope
import com.android.cameraapp.ui.base_activity.photos_fragment.PhotosDataSource
import javax.inject.Inject

@PhotoFragmentScope
class PhotoDataFactory @Inject constructor(val dataSource: PhotosDataSource) : DataSource.Factory<Int, UserCollection.Photos>() {
    override fun create(): DataSource<Int, UserCollection.Photos> {
       return dataSource
    }
}