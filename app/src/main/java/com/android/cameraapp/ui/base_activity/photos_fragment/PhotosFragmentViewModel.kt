package com.android.cameraapp.ui.base_activity.photos_fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.cameraapp.data.data_models.DataFlat
import com.android.cameraapp.di.base_activity.photo_fragment.PhotoFragmentScope
import com.android.cameraapp.util.States
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@PhotoFragmentScope
class PhotosFragmentViewModel @Inject constructor(val repo: PhotosFragmentRepository) :
    ViewModel() {
    val mediatorLiveData: MediatorLiveData<List<DataFlat.PhotosWithUser>> = repo.mediatorLiveData
    val statesLiveData: LiveData<States> = repo.taskState
    lateinit var job: Job


    fun init(userId: String?) {
        repo.setListener(userId)
        job = viewModelScope.launch { repo.getAllPhotos(userId) }
    }

    override fun onCleared() {
        super.onCleared()
        repo.clearListener()
    }
}