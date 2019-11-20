package com.android.cameraapp.di.base_activity.full_picture_fragment

import androidx.lifecycle.ViewModel
import com.android.cameraapp.di.ViewModelKey
import com.android.cameraapp.ui.base_activity.full_picture_fragment.FullPictureViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module

abstract class FullPictureViewModelModule {
    @Binds
    @IntoMap
    @FullPictureScope
    @ViewModelKey(FullPictureViewModel::class)
    abstract fun getFullPictureViewModel(viewModel: FullPictureViewModel): ViewModel
}