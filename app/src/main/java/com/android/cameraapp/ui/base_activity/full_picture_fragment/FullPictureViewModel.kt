package com.android.cameraapp.ui.base_activity.full_picture_fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.cameraapp.data.data_models.DataFlat
import com.android.cameraapp.di.base_activity.full_picture_fragment.FullPictureScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@FullPictureScope
class FullPictureViewModel @Inject constructor(val repo : FullPictureRepository): ViewModel() {
        val list = repo.likes

        fun getLikes(photosWithUser: DataFlat.PhotosWithUser) : LiveData<List<DataFlat.Likes>> {
                viewModelScope.launch { repo.getLimitedLikes(photosWithUser) }
                return list
        }
}