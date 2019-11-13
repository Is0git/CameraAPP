package com.android.cameraapp.di.base_activity.start_fragment

import androidx.lifecycle.ViewModel
import com.android.cameraapp.di.ViewModelKey
import com.android.cameraapp.di.base_activity.BaseActivityScope
import com.android.cameraapp.ui.base_activity.start_fragment.StartFragmentViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class StartViewModelModule {
    @IntoMap
    @BaseActivityScope
    @ViewModelKey(StartFragmentViewModel::class)
    @Binds
    abstract fun getStartViewModel(viewModel: StartFragmentViewModel): ViewModel
}