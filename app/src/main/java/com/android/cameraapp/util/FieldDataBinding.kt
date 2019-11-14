package com.android.cameraapp.util

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.android.cameraapp.data.data_models.DataFlat

object FieldDataBinding {
    @JvmStatic
    @BindingAdapter("app:setComments")
    fun setComments(textView: TextView, item:DataFlat.PhotosWithUser) {

    }
}