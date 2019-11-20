package com.android.cameraapp.di.base_activity.following_fragment

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.android.cameraapp.data.data_models.DataFlat
import com.android.cameraapp.ui.base_activity.following_fragment.FollowingDataSourceFactory
import dagger.Module
import dagger.Provides

@Module
object FollowingModule {

    @Provides
    @FollowingFragmentScope
    @JvmStatic
    fun getPageConfig(): PagedList.Config =
        PagedList.Config.Builder().setPageSize(10).setEnablePlaceholders(false).build()


    @Provides
    @FollowingFragmentScope
    @JvmStatic
    fun getPagedListLiveData(
        pagedListConfig: PagedList.Config,
        dataSourceFactory: FollowingDataSourceFactory
    ): LiveData<PagedList<DataFlat.Following>> {
        return LivePagedListBuilder(dataSourceFactory, pagedListConfig).build()
    }

}