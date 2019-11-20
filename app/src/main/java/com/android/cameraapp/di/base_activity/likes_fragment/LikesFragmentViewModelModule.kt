package com.android.cameraapp.di.base_activity.likes_fragment

import androidx.lifecycle.ViewModel
import com.android.cameraapp.di.ViewModelKey
import com.android.cameraapp.ui.base_activity.likes_fragment.LikesFragmentViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class LikesFragmentViewModelModule {
    @LikesFragmentScope
    @Binds
    @IntoMap
    @ViewModelKey(LikesFragmentViewModel::class)
    abstract fun getLikesFragmentViewmodel(viewmodel: LikesFragmentViewModel): ViewModel
}