package com.android.cameraapp.di.base_activity.map_fragment

import androidx.lifecycle.ViewModel
import com.android.cameraapp.di.ViewModelKey
import com.android.cameraapp.ui.base_activity.map_fragment.MapFragmentViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
@Module
abstract class MapViewModelModule {
    @MapFragmentScope
    @Binds
    @IntoMap
    @ViewModelKey(MapFragmentViewModel::class)
    abstract fun getMapViewModel(viewModel: MapFragmentViewModel) : ViewModel
}