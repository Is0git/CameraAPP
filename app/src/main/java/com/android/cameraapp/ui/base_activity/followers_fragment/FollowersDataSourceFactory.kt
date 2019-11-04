package com.android.cameraapp.ui.base_activity.followers_fragment

import androidx.paging.DataSource
import com.android.cameraapp.data.data_models.UserCollection
import com.android.cameraapp.di.base_activity.followers_fragment.FollowersFragmentScope
import javax.inject.Inject

@FollowersFragmentScope
class FollowersDataSourceFactory @Inject constructor(val dataSource: FollowersDataSource) : DataSource.Factory<Int, UserCollection.Followers>() {
    override fun create(): DataSource<Int, UserCollection.Followers> {
        return dataSource
    }
}