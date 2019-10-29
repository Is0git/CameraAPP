package com.android.cameraapp.di.base_activity

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.android.cameraapp.R
import com.android.cameraapp.di.scopes.BaseActivityScope
import com.android.cameraapp.ui.base_activity.BaseActivity
import dagger.Module
import dagger.Provides
import kotlinx.android.synthetic.main.activity_main.*


@Module
class NavigationModule {
    @Provides
    @BaseActivityScope
    fun getNavController(activity: BaseActivity) : NavController = activity.findNavController(R.id.main_fragment_container)
}