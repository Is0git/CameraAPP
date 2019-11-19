package com.android.cameraapp.util

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import com.android.cameraapp.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

object ImageLoader {
    @JvmStatic
    @BindingAdapter("app:loadImageFromUrl")
    fun loadImage(view: ImageView, url:String?) {
        Glide.with(view.context).load(url).centerCrop().placeholder(R.drawable.image_placeholder).into(view)
    }

    @JvmStatic
    @BindingAdapter("app:loadImageWithProgressBar", "app:setProgressBar")
    fun loadImageWithProgressBar(view: ImageView, url:String?, progressBar: View) {
        if(progressBar.visibility != View.VISIBLE) progressBar.visibility = View.VISIBLE
        Glide.with(view.context).load(url).centerCrop().placeholder(R.drawable.image_placeholder).listener(object  :
            RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                progressBar.visibility = View.INVISIBLE
                return true
            }
        }).into(view)
    }
}