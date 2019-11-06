package com.android.cameraapp.di

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Job
import javax.inject.Singleton

@Module
object JobsModule {
    @Provides
    @JvmStatic
    fun getJobs() : Job = Job()
}