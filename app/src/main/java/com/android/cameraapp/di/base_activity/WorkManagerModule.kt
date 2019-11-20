package com.android.cameraapp.di.base_activity

import android.app.Application
import androidx.work.WorkManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object WorkManagerModule {

    @Provides
    @JvmStatic
    @Singleton
    fun getWorkManager(application: Application): WorkManager = WorkManager.getInstance(application)
}