package com.android.cameraapp.di.base_activity

import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides

@Module

object FirebaseAuthModule {
    @Provides
    @JvmStatic
    @BaseActivityScope
    fun getFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()


}