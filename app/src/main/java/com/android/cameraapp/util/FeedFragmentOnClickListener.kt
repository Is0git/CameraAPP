package com.android.cameraapp.util

import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.motion.widget.MotionLayout
import com.android.cameraapp.data.data_models.DataFlat

interface FeedFragmentOnClickListener {
    fun imageOnClick(photo: View, item: DataFlat.PhotosWithUser, motionLayout: MotionLayout)
}