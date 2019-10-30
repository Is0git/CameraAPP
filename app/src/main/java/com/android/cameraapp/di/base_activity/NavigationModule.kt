package com.android.cameraapp.di.base_activity

import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.android.cameraapp.R
import com.android.cameraapp.ui.base_activity.BaseActivity
import dagger.Module
import dagger.Provides


@Module
class NavigationModule {
    @Provides
    @BaseActivityScope
    fun getNavController(activity: BaseActivity): NavController =
        activity.findNavController(R.id.main_fragment_container)
}