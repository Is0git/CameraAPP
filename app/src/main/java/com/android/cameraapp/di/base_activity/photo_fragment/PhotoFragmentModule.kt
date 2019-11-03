package com.android.cameraapp.di.base_activity.photo_fragment

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.android.cameraapp.data.data_models.UserCollection
import com.android.cameraapp.di.base_activity.photo_fragment.PhotoFragmentScope
import com.android.cameraapp.ui.base_activity.home_fragment.PhotoDataFactory
import com.android.cameraapp.ui.base_activity.home_fragment.PhotosDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
object PhotoFragmentModule {

    @PhotoFragmentScope
    @Provides
    @JvmStatic
    fun pagedConfig() : PagedList.Config{
        return PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(10)
            .setInitialLoadSizeHint(10)
            .build()
    }

    @PhotoFragmentScope
    @Provides
    @JvmStatic
    fun getPagedListLiveData(dataSourceFactory: PhotoDataFactory , config: PagedList.Config) : LiveData<PagedList<UserCollection.Photos>> {
       return LivePagedListBuilder(dataSourceFactory, config).build()
    }
}