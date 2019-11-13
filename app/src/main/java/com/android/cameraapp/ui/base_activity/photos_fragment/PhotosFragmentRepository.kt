package com.android.cameraapp.ui.base_activity.photos_fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.android.cameraapp.data.data_models.UserCollection
import com.android.cameraapp.di.base_activity.photo_fragment.PhotoFragmentScope
import com.android.cameraapp.util.firebase.userCollection
import com.android.cameraapp.util.firebase.userPhotosCollection
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@PhotoFragmentScope
class PhotosFragmentRepository @Inject constructor(
    val firebaseAuth: FirebaseAuth,
    val firestore: FirebaseFirestore,
    val photosAdapter: PhotosAdapter,
    val photoFragment:PhotosFragment
) : EventListener<QuerySnapshot> {
    val photo = MutableLiveData<List<UserCollection.Photos>>()
    val photos = MutableLiveData<List<UserCollection.Photos>>()
    val mediatorLiveData = MediatorLiveData<List<UserCollection.Photos>>()
    var counter = 0
    var listenerRegistration: ListenerRegistration = firestore.collection("$userCollection/${firebaseAuth.uid}/$userPhotosCollection")
           .orderBy("time_in_long", Query.Direction.DESCENDING).limit(1).addSnapshotListener(this)

    override fun onEvent(p0: QuerySnapshot?, p1: FirebaseFirestoreException?) {
        if (counter > 0) {
            if(p0?.documents?.isNotEmpty()!!) {
                val item = p0.toObjects(UserCollection.Photos::class.java)
                photo.value = item
            }
        }

        counter++
    }

  suspend  fun getAllPhotos() {
      val firstLoad =  firestore.collection("$userCollection/${firebaseAuth.uid}/$userPhotosCollection")
            .orderBy("time_in_long", Query.Direction.DESCENDING).get().await()
      photos.value = firstLoad.toObjects(UserCollection.Photos::class.java)
    }

    fun getData() {
        mediatorLiveData.apply {
            addSource(photo, Observer {data -> mediatorLiveData.value = data })
            addSource(photos, Observer { data -> mediatorLiveData.value = data })
        }
    }

    fun clearListener() {
        listenerRegistration.remove()
    }
}