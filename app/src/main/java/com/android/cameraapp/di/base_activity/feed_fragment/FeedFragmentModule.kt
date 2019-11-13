package com.android.cameraapp.di.base_activity.feed_fragment

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.android.cameraapp.data.data_models.DataFlat
import com.android.cameraapp.di.base_activity.followers_fragment.FollowersFragmentScope
import com.android.cameraapp.di.base_activity.photo_fragment.PhotoFragmentScope
import com.android.cameraapp.ui.base_activity.feed_fragment.PhotosWithUserDataSource
import com.android.cameraapp.ui.base_activity.feed_fragment.PhotosWithUserFactory
import com.android.cameraapp.ui.base_activity.followers_fragment.FollowersDataSourceFactory
import dagger.Module
import dagger.Provides

@Module
object FeedFragmentModule {

    @Provides
    @FeedFragmentScope
    @JvmStatic
    fun getPageConfig(): PagedList.Config =
        PagedList.Config.Builder().setPageSize(3).setInitialLoadSizeHint(4).setEnablePlaceholders(false).build()

    @Provides
    @FeedFragmentScope
    @JvmStatic
    fun getPagedListLiveData(
        pagedListConfig: PagedList.Config,
        dataSourceFactory: PhotosWithUserFactory
    ): LiveData<PagedList<DataFlat.PhotosWithUser>> {
        return LivePagedListBuilder(dataSourceFactory, pagedListConfig).build()
    }
}