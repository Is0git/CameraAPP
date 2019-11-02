package com.android.cameraapp.di.base_activity.add_photo_fragments

import androidx.lifecycle.ViewModel
import com.android.cameraapp.di.ViewModelKey
import com.android.cameraapp.ui.base_activity.add_photo_fragments.AddFragmentsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class AddFragmentViewModelModule {
    @Binds
    @IntoMap
    @AddPhotoFragmentsScope
    @ViewModelKey(AddFragmentsViewModel::class)
    abstract fun getAddFragmentsViewmodel(viewmodel: AddFragmentsViewModel) : ViewModel
}