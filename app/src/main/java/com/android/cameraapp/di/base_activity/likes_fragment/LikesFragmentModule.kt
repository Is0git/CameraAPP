package com.android.cameraapp.di.base_activity.likes_fragment

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.android.cameraapp.data.data_models.DataFlat
import com.android.cameraapp.di.base_activity.following_fragment.FollowingFragmentScope
import com.android.cameraapp.ui.base_activity.following_fragment.FollowingAdapter
import com.android.cameraapp.ui.base_activity.following_fragment.FollowingDataSourceFactory
import com.android.cameraapp.ui.base_activity.likes_fragment.LikesAdapter
import com.android.cameraapp.ui.base_activity.likes_fragment.LikesDataSourceFactory
import com.android.cameraapp.ui.base_activity.likes_fragment.LikesFragmentDataSource
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Job

@Module
object LikesFragmentModule {
    @Provides
    @LikesFragmentScope
    @JvmStatic
    fun getPageConfig(): PagedList.Config = PagedList.Config.Builder().setPageSize(10).setEnablePlaceholders(false).build()


    @Provides
    @LikesFragmentScope
    @JvmStatic
    fun getPagedListLiveData(pagedListConfig: PagedList.Config, dataSourceFactory: LikesDataSourceFactory) : LiveData<PagedList<DataFlat.Likes>> {
        return LivePagedListBuilder(dataSourceFactory, pagedListConfig).build()
    }


    @Provides
    @LikesFragmentScope
    @JvmStatic
    fun getJob()  : Job= Job()

}