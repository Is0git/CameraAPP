package com.android.cameraapp.di

import com.android.cameraapp.di.base_activity.BaseActivityScope
import com.android.cameraapp.di.base_activity.BaseViewModelModule
import com.android.cameraapp.di.base_activity.FragmentsBuilder
import com.android.cameraapp.di.base_activity.NavigationModule
import com.android.cameraapp.di.base_activity.start_fragment.StartViewModelModule
import com.android.cameraapp.ui.base_activity.BaseActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = [NavigationModule::class, BaseViewModelModule::class, FragmentsBuilder::class, StartViewModelModule::class])
    @BaseActivityScope
    abstract fun baseActivity(): BaseActivity
}