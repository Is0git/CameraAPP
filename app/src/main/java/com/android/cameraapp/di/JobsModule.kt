package com.android.cameraapp.di

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Job

@Module
object JobsModule {
    @Provides
    @JvmStatic
    fun getJobs(): Job = Job()
}