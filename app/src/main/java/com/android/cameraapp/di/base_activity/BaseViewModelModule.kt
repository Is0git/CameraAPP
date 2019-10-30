package com.android.cameraapp.di.base_activity

import androidx.lifecycle.ViewModel
import com.android.cameraapp.di.ViewModelKey
import com.android.cameraapp.di.scopes.BaseActivityScope
import com.android.cameraapp.ui.base_activity.BaseViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class BaseViewModelModule {
    @Binds
    @IntoMap
    @BaseActivityScope
    @ViewModelKey(BaseViewModel::class)
    abstract fun baseViewModel(baseViewModel: BaseViewModel): ViewModel
}