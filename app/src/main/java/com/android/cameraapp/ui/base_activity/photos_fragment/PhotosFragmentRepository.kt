package com.android.cameraapp.ui.base_activity.photos_fragment

import androidx.lifecycle.*
import com.android.cameraapp.data.data_models.DataFlat
import com.android.cameraapp.di.base_activity.photo_fragment.PhotoFragmentScope
import com.android.cameraapp.util.States
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
    val photoFragment: PhotosFragment
) : EventListener<QuerySnapshot>, LifecycleObserver {
    val photo = MutableLiveData<List<DataFlat.PhotosWithUser>>()
    val photos = MutableLiveData<List<DataFlat.PhotosWithUser>>()
    val mediatorLiveData = MediatorLiveData<List<DataFlat.PhotosWithUser>>()
    var counter = 0
    var listenerRegistration: ListenerRegistration? = null
    var taskState = MutableLiveData<States>()

    fun setListener(userId: String?) {
        val queryID = userId ?: firebaseAuth.uid
        listenerRegistration =
            firestore.collection("$userCollection/${queryID}/$userPhotosCollection")
                .orderBy("time_in_long", Query.Direction.DESCENDING).addSnapshotListener(this)

    }

    override fun onEvent(p0: QuerySnapshot?, p1: FirebaseFirestoreException?) {
        if (counter > 0) {
            taskState.postValue(States.START)
            if (p0?.documents?.isNotEmpty()!!) {
                val item = p0.toObjects(DataFlat.PhotosWithUser::class.java)
                photo.value = item
                taskState.postValue(States.FINISH)
            }
        }

        counter++
    }

    suspend fun getAllPhotos(userId: String?) {
        taskState.postValue(States.START)
        val queryID = userId ?: firebaseAuth.uid
        val firstLoad = firestore.collection("$userCollection/${queryID}/$userPhotosCollection")
            .orderBy("time_in_long", Query.Direction.DESCENDING).get().await()
        val photosFirstLoad = firstLoad.toObjects(DataFlat.PhotosWithUser::class.java)
        photosFirstLoad.forEachIndexed { i, o -> o.doc_id = firstLoad.documents[i].id }
        photos.value = photosFirstLoad
        taskState.postValue(States.FINISH)
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun addMediatorListeners() {
        mediatorLiveData.apply {
            addSource(photo) { data -> mediatorLiveData.value = data }
            addSource(photos) { data -> mediatorLiveData.value = data }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun removeMediatorListeners() {
        mediatorLiveData.apply {
            removeSource(photo)
            removeSource(photos)
        }
    }


    fun clearListener() {
        listenerRegistration?.remove()
    }
}