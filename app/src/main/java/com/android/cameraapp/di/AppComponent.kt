package com.android.cameraapp.di

import android.app.Application
import com.android.cameraapp.App
import com.android.cameraapp.di.base_activity.FirebaseAuthModule
import com.android.cameraapp.di.base_activity.FirebaseFirestoreModule
import com.android.cameraapp.di.base_activity.WorkManagerModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Component(modules = [AndroidInjectionModule::class, ActivityBuilder::class, ViewModuleFactory::class, WorkManagerModule::class, JobsModule::class, FirebaseAuthModule::class, FirebaseFirestoreModule::class])
@Singleton
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    interface Builder {
        fun build(): AppComponent
        @BindsInstance

        fun application(application: Application): Builder
    }
}