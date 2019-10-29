package com.android.cameraapp.di

import com.android.cameraapp.di.base_activity.BaseViewModelModule
import com.android.cameraapp.di.base_activity.FirebaseAuthModule
import com.android.cameraapp.ui.base_activity.BaseActivity
import com.android.cameraapp.di.base_activity.FragmentsBuilder
import com.android.cameraapp.di.base_activity.NavigationModule
import com.android.cameraapp.di.scopes.BaseActivityScope
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = [FirebaseAuthModule::class, NavigationModule::class, BaseViewModelModule::class, FragmentsBuilder::class])
    @BaseActivityScope
    abstract fun baseActivity() : BaseActivity
}