package com.android.cameraapp.ui.base_activity.following_fragment

import androidx.paging.DataSource
import com.android.cameraapp.data.data_models.DataFlat
import com.android.cameraapp.di.base_activity.following_fragment.FollowingFragmentScope
import javax.inject.Inject

@FollowingFragmentScope
class FollowingDataSourceFactory @Inject constructor(val dataSource: FollowingDataSource) :
    DataSource.Factory<Int, DataFlat.Following>() {
    override fun create(): DataSource<Int, DataFlat.Following> {
        return dataSource
    }
}