package com.android.cameraapp.ui.base_activity.map_fragment

import androidx.lifecycle.MutableLiveData
import com.android.cameraapp.data.data_models.UserCollection
import com.android.cameraapp.di.base_activity.map_fragment.MapFragmentScope
import com.android.cameraapp.util.firebase.userCollection
import com.android.cameraapp.util.firebase.userPhotosCollection
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@MapFragmentScope
class MapRepository @Inject constructor(
    val firebaseAuth: FirebaseAuth,
    val fireStore: FirebaseFirestore
) {

    val photos = MutableLiveData<List<UserCollection.Photos>>()

    suspend fun getPhotos() =
        fireStore.collection("$userCollection/${firebaseAuth.uid}/$userPhotosCollection").get().await().toObjects(
            UserCollection.Photos::class.java
        ).also { photos.postValue(it) }

}