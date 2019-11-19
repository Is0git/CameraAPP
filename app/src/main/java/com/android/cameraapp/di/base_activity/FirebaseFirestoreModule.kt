package com.android.cameraapp.di.base_activity

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object FirebaseFirestoreModule {

    @Provides
    @Singleton
    @JvmStatic
    fun fireStoreInstance() : FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    @JvmStatic
    fun fireStorage() : FirebaseStorage = FirebaseStorage.getInstance()

}