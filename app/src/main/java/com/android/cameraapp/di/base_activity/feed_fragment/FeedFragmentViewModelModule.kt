package com.android.cameraapp.di.base_activity.feed_fragment

import androidx.lifecycle.ViewModel
import com.android.cameraapp.di.ViewModelKey
import com.android.cameraapp.ui.base_activity.feed_fragment.FeedFragmentViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class FeedFragmentViewModelModule {
    @FeedFragmentScope
    @Binds
    @IntoMap
    @ViewModelKey(FeedFragmentViewModel::class)
    abstract fun getFeedViewModel(viewModel: FeedFragmentViewModel) : ViewModel
}