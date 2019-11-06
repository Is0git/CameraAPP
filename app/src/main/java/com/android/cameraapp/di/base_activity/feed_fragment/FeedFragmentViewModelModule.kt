package com.android.cameraapp.di.base_activity.feed_fragment

import com.android.cameraapp.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
annotation class FeedFragmentViewModelModule {
    @FeedFragmentScope
    @Binds
    @IntoMap
    @ViewModelKey()
}