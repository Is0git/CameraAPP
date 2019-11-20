package com.android.cameraapp.di.base_activity.home_fragment

import androidx.lifecycle.ViewModel
import com.android.cameraapp.di.ViewModelKey
import com.android.cameraapp.ui.base_activity.photos_fragment.PhotosFragmentViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class HomeFragmentViewModelModule {
    @Binds
    @IntoMap
    @HomeFragmentScope
    @ViewModelKey(PhotosFragmentViewModel::class)
    abstract fun getHomeViewModel(viewModel: PhotosFragmentViewModel): ViewModel
}