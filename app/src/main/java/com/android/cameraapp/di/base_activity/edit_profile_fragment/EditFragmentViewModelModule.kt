package com.android.cameraapp.di.base_activity.edit_profile_fragment

import androidx.lifecycle.ViewModel
import com.android.cameraapp.di.ViewModelKey
import com.android.cameraapp.ui.base_activity.edit_profile_fragment.EditProfileViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class EditFragmentViewModelModule {
    @Binds
    @EditProfileScope
    @IntoMap
    @ViewModelKey(EditProfileViewModel::class)
    abstract fun getEditViewModel(viewModel: EditProfileViewModel): ViewModel
}