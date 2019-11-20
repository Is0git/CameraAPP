package com.android.cameraapp.di.base_activity.following_fragment

import androidx.lifecycle.ViewModel
import com.android.cameraapp.di.ViewModelKey
import com.android.cameraapp.ui.base_activity.following_fragment.FollowingViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class FollowingViewModelModule {
    @FollowingFragmentScope
    @Binds
    @IntoMap
    @ViewModelKey(FollowingViewModel::class)
    abstract fun getFollowingViewModel(viewModel: FollowingViewModel): ViewModel
}