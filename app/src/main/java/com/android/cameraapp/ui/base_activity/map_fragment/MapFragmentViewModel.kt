package com.android.cameraapp.ui.base_activity.map_fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.cameraapp.data.data_models.UserCollection
import com.android.cameraapp.di.base_activity.map_fragment.MapFragmentScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@MapFragmentScope
class MapFragmentViewModel @Inject constructor(val repo: MapRepository) : ViewModel() {
   val photos = repo.photos

    fun getPhotos() : LiveData<List<UserCollection.Photos>> {
        viewModelScope.launch { repo.getPhotos() }
        return photos
    }
}