package com.android.cameraapp

import android.util.Log
import com.android.cameraapp.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class App : DaggerApplication() {


    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        Log.d("APPTEST", "2")
        return DaggerAppComponent.builder().application(this).build()
    }

    override fun onCreate() {
        Log.d("APPTEST", "1")
        super.onCreate()
    }

}