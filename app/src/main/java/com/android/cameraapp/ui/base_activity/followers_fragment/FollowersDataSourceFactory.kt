package com.android.cameraapp.ui.base_activity.followers_fragment

import androidx.paging.DataSource
import com.android.cameraapp.data.data_models.DataFlat
import com.android.cameraapp.di.base_activity.followers_fragment.FollowersFragmentScope
import javax.inject.Inject

@FollowersFragmentScope
class FollowersDataSourceFactory @Inject constructor(val dataSource: FollowersDataSource) :
    DataSource.Factory<Int, DataFlat.Followers>() {
    override fun create(): DataSource<Int, DataFlat.Followers> {
        return dataSource
    }
}