package com.android.cameraapp.ui.base_activity.following_fragment

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.android.cameraapp.data.data_models.UserCollection
import com.android.cameraapp.di.base_activity.following_fragment.FollowingFragmentScope
import javax.inject.Inject

@FollowingFragmentScope
class FollowingRepository @Inject constructor(val pagedList: LiveData<PagedList<UserCollection.Following>>)