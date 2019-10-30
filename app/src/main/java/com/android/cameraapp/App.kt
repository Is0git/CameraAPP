package com.android.cameraapp

import com.android.cameraapp.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class App : DaggerApplication() {

    var rememberUser:Boolean = false

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }

    override fun onTerminate() {
        super.onTerminate()

    }
}