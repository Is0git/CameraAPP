package com.android.cameraapp.di.base_activity.followers_fragment

import androidx.lifecycle.ViewModel
import com.android.cameraapp.di.ViewModelKey
import com.android.cameraapp.ui.base_activity.followers_fragment.FollowersViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class  FollowersViewModelModule {

    @Binds
    @FollowersFragmentScope
    @IntoMap
    @ViewModelKey(FollowersViewModel::class)
    abstract fun getViewModel(viewmodel: FollowersViewModel) : ViewModel
}