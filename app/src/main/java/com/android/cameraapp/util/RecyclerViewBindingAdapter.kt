package com.android.cameraapp.util

import android.view.View
import androidx.databinding.BindingAdapter

object RecyclerViewBindingAdapter {
    @BindingAdapter("app:showPlaceHolder")
    @JvmStatic
    fun showPlaceHolder(view: View, size: Int?) {
        if (size != null && size == 0) view.visibility = View.INVISIBLE else View.VISIBLE

    }
}