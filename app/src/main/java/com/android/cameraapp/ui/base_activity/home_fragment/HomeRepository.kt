package com.android.cameraapp.ui.base_activity.home_fragment

import com.android.cameraapp.data.data_models.UserCollection
import com.android.cameraapp.di.base_activity.home_fragment.HomeFragmentScope
import com.android.cameraapp.util.userCollection
import com.android.cameraapp.util.userPhotosCollection
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HomeFragmentScope
class HomeRepository @Inject constructor(val firebaseAuth: FirebaseAuth, val firestore: FirebaseFirestore) {

  suspend fun getCounts() {
        var countsArray = IntArray(4)
        val photosCount = firestore.collection("$userCollection/${firebaseAuth.uid}/$userPhotosCollection").get().await().size()

    }
}