package com.android.cameraapp.ui.base_activity.followers_fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.android.cameraapp.data.data_models.DataFlat
import com.android.cameraapp.di.base_activity.followers_fragment.FollowersFragmentScope
import javax.inject.Inject

@FollowersFragmentScope
class FollowersViewModel @Inject constructor(
    val repo: FollowersRepository,
    val dataSource: FollowersDataSource
) : ViewModel() {
    var pagedList: LiveData<PagedList<DataFlat.Followers>> = repo.pagedList

    override fun onCleared() {
        dataSource.cancelJobs()
        super.onCleared()
    }


}
