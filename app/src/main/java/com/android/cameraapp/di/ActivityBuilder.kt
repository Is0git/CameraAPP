package com.android.cameraapp.di

import com.android.cameraapp.di.base_activity.*
import com.android.cameraapp.ui.base_activity.BaseActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector
import javax.inject.Singleton

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = [FirebaseAuthModule::class, NavigationModule::class, BaseViewModelModule::class, FragmentsBuilder::class, FirebaseFirestoreModule::class])
    @BaseActivityScope
    @Singleton
    abstract fun baseActivity(): BaseActivity
}