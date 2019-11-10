package com.android.cameraapp.util.firebase

import com.android.cameraapp.data.data_models.UserCollection
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

abstract class CurrentUser(val auth: FirebaseAuth, private val fireStore: FirebaseFirestore) {

    suspend fun getCurrentUser() : UserCollection.User? = fireStore.document("$userCollection/${auth.uid}").get().await().toObject(UserCollection.User::class.java)
}