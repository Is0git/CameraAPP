package com.android.cameraapp.di

import com.android.cameraapp.ui.base_activity.BaseActivity
import com.android.cameraapp.di.base_activity.FragmentsBuilder
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [FragmentsBuilder::class])
abstract class ActivityBuilder {
    @Binds
    @ContributesAndroidInjector
    abstract fun baseActivity() : BaseActivity
}