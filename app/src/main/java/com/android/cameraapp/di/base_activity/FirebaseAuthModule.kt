package com.android.cameraapp.di.base_activity

import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module

object FirebaseAuthModule {
    @Provides
    @JvmStatic
    @Singleton
    fun getFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()


}