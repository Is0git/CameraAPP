package com.android.cameraapp.util

import android.view.View
import android.widget.ImageView
import com.android.cameraapp.data.data_models.DataFlat

interface FeedFragmentOnClickListener {
    fun imageOnClick(photo: View, item: DataFlat.PhotosWithUser)
}