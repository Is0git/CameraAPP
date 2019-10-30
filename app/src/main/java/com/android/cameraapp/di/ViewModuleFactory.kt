package com.android.cameraapp.di

import androidx.lifecycle.ViewModelProvider
import com.android.nbaapp.data.vms.ViewModelFactory
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class ViewModuleFactory {
    @Binds
    @Singleton
    abstract fun viewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}