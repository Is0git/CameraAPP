package com.android.cameraapp.di.base_activity

import com.android.cameraapp.di.scopes.BaseActivityScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.Module
import dagger.Provides

@Module

object FirebaseAuthModule {
    @Provides
    @JvmStatic
    @BaseActivityScope
    fun getFirebaseAuth() : FirebaseAuth = FirebaseAuth.getInstance()



}