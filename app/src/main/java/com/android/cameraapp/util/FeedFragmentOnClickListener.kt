package com.android.cameraapp.util

import android.widget.ImageView
import com.android.cameraapp.data.data_models.DataFlat

interface FeedFragmentOnClickListener {
    fun imageOnClick(item: DataFlat.PhotosWithUser, photo: ImageView)
}