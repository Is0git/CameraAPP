package com.android.cameraapp.ui.base_activity.likes_fragment

import androidx.paging.DataSource
import com.android.cameraapp.data.data_models.DataFlat
import com.android.cameraapp.di.base_activity.likes_fragment.LikesFragmentScope
import javax.inject.Inject

@LikesFragmentScope
class LikesDataSourceFactory @Inject constructor(val dataSource: LikesFragmentDataSource) :
    DataSource.Factory<Int, DataFlat.Likes>() {
    override fun create(): DataSource<Int, DataFlat.Likes> = dataSource
}