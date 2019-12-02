package com.android.cameraapp.util

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.android.cameraapp.R
import com.bumptech.glide.Glide
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

object ImageLoader {
    @JvmStatic
    @BindingAdapter("app:loadImageFromUrl")
    fun loadImage(view: ImageView, url: String?) {
        Glide.with(view.context).load(url).centerCrop().placeholder(R.drawable.image_placeholder)
            .into(view)
    }

    @JvmStatic
    @BindingAdapter("app:loadImageWithProgressBar", "app:setProgressBar")
    fun loadImageWithProgressBar(view: CircleImageView, url: String?, progressBar: View) {
        if (progressBar.visibility != View.VISIBLE) progressBar.visibility = View.VISIBLE
        if (url != null) {
            Picasso.with(view.context).load(url).into(view, object : Callback {
                override fun onSuccess() {
                    progressBar.visibility = View.INVISIBLE
                }

                override fun onError() {
                    progressBar.visibility = View.INVISIBLE
                }

            })
        }
    }
}