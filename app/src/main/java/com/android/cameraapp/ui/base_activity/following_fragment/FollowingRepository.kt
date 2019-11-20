package com.android.cameraapp.ui.base_activity.following_fragment

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.android.cameraapp.data.data_models.DataFlat
import com.android.cameraapp.data.data_models.UserCollection
import com.android.cameraapp.di.base_activity.following_fragment.FollowingFragmentScope
import com.android.cameraapp.util.States
import javax.inject.Inject

@FollowingFragmentScope
class FollowingRepository @Inject constructor(val pagedList: LiveData<PagedList<DataFlat.Following>>, val dataSource: FollowingDataSource) {
    val statesLiveData: LiveData<States> = dataSource.taskState
}