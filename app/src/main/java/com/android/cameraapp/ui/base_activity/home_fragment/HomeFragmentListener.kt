package com.android.cameraapp.ui.base_activity.home_fragment

import android.view.View
import android.widget.ImageView

interface HomeFragmentListener<T> {

    fun onUserClick(userData: T, imageView: View, nameTextView: View)

}