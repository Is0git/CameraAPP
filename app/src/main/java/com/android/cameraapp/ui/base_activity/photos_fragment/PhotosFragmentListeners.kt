package com.android.cameraapp.ui.base_activity.photos_fragment

import android.widget.ImageView
import com.android.cameraapp.data.data_models.DataFlat

interface PhotosFragmentListeners {

    fun onPhotoClick(view: ImageView, photoData: DataFlat.PhotosWithUser)
}