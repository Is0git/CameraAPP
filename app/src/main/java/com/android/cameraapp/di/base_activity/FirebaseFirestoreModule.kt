package com.android.cameraapp.di.base_activity

import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object FirebaseFirestoreModule {

    @Provides
    @Singleton
    @JvmStatic
    fun fireStoreInstance() : FirebaseFirestore = FirebaseFirestore.getInstance()

}