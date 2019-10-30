package com.android.cameraapp.di.base_activity

import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides

@Module
object FirebaseFirestoreModule {

    @Provides
    @BaseActivityScope
    @JvmStatic
    fun fireStoreInstance() : FirebaseFirestore = FirebaseFirestore.getInstance()

}