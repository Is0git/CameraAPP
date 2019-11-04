package com.android.cameraapp.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.android.cameraapp.R
import com.bumptech.glide.Glide

object ImageLoader {
    @JvmStatic
    @BindingAdapter("app:loadImageFromUrl")
    fun loadImage(view: ImageView, url:String?) {
        Glide.with(view.context).load(url).centerCrop().placeholder(R.drawable.image_placeholder).into(view)
    }
}