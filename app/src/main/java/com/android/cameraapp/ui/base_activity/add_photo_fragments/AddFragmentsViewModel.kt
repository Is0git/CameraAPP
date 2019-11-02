package com.android.cameraapp.ui.base_activity.add_photo_fragments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.cameraapp.di.base_activity.add_photo_fragments.AddPhotoFragmentsScope
import kotlinx.coroutines.launch
import javax.inject.Inject
@AddPhotoFragmentsScope
class AddFragmentsViewModel @Inject constructor(val repository: AddFragmentsRepository) : ViewModel() {

    fun uploadPhoto() = viewModelScope.launch { repository.uploadPhoto() }

}