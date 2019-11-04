package com.android.cameraapp.di.base_activity.following_fragment

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.android.cameraapp.data.data_models.UserCollection
import com.android.cameraapp.di.base_activity.FragmentsBuilder_FollowersFragment
import com.android.cameraapp.di.base_activity.followers_fragment.FollowersFragmentScope
import com.android.cameraapp.ui.base_activity.followers_fragment.FollowersDataSourceFactory
import com.android.cameraapp.ui.base_activity.following_fragment.FollowingDataSourceFactory
import dagger.Module
import dagger.Provides

@Module(subcomponents = [FragmentsBuilder_FollowersFragment.FollowersFragmentSubcomponent::class])
object FollowingModule {

    @Provides
    @FollowingFragmentScope
    @JvmStatic
    fun getPagedListLiveData(pagedListConfig: PagedList.Config, dataSourceFactory: FollowingDataSourceFactory) : LiveData<PagedList<UserCollection.Following>> {
        return LivePagedListBuilder(dataSourceFactory, pagedListConfig).build()
    }


}