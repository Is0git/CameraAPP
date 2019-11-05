package com.android.cameraapp.util

import com.android.cameraapp.data.data_models.DataFlat

interface FollowersDataSourceCallback  {
    fun getDataSource(data: List<DataFlat.Followers>): List<DataFlat.Followers> {
        return  data
    }
}