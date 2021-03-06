package com.android.cameraapp.ui.base_activity.feed_fragment

import androidx.lifecycle.MutableLiveData
import androidx.paging.PositionalDataSource
import com.android.cameraapp.data.data_models.DataFlat
import com.android.cameraapp.data.data_models.UserCollection
import com.android.cameraapp.di.base_activity.feed_fragment.FeedFragmentScope
import com.android.cameraapp.util.States
import com.android.cameraapp.util.firebase.photosLikesCollection
import com.android.cameraapp.util.firebase.userCollection
import com.android.cameraapp.util.firebase.userPhotosCollection
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@FeedFragmentScope
class PhotosWithUserDataSource @Inject constructor(
    val auth: FirebaseAuth,
    val firestore: FirebaseFirestore,
    val job: Job
) : PositionalDataSource<DataFlat.PhotosWithUser>() {
    var document: DocumentSnapshot? = null
    val state = MutableLiveData<States>()
    override fun loadRange(
        params: LoadRangeParams,
        callback: LoadRangeCallback<DataFlat.PhotosWithUser>
    ) {
        document?.let {
            val list = mutableListOf<DataFlat.PhotosWithUser>()
            CoroutineScope(Dispatchers.Main + job).launch {
                state.value = States.START
                getPhotos(list, params)
                callback.onResult(list)
                state.value = States.FINISH
            }
        }

    }

    override fun loadInitial(
        params: LoadInitialParams,
        callback: LoadInitialCallback<DataFlat.PhotosWithUser>
    ) {
        val list = mutableListOf<DataFlat.PhotosWithUser>()
        CoroutineScope(Dispatchers.Main + job).launch {
            state.value = States.START
            getPhotos(list, params)
            callback.onResult(list, 0, list.size)
            state.value = States.FINISH
        }

    }


    suspend fun <T> getPhotos(list: MutableList<DataFlat.PhotosWithUser>, params: T) {
        val result: MutableList<DataFlat.PhotosWithUser>? = when (params) {
            is LoadInitialParams -> {
                firestore.collectionGroup("photos")
                    .orderBy("time_in_long", Query.Direction.DESCENDING)
                    .whereEqualTo("private", false)
                    .limit(params.requestedLoadSize.toLong()).get().await()
                    .also {
                        if (it?.documents != null && it.documents.size > 0) document =
                            it.documents.last() else throw CancellationException()
                    }.let { it.toObjects(DataFlat.PhotosWithUser::class.java) }
            }
            is LoadRangeParams -> {
                firestore.collectionGroup("photos")
                    .orderBy("time_in_long", Query.Direction.DESCENDING)
                    .whereEqualTo("private", false)
                    .startAfter(document!!)
                    .limit(params.loadSize.toLong()).get().await()
                    .also {
                        if (it?.documents != null && it.documents.size > 0) document =
                            it.documents.last() else throw CancellationException()
                    }.toObjects(DataFlat.PhotosWithUser::class.java)
            }
            else -> null
        }



        mapUsers(result!!).map { getUsers(it) }.map { checkPhotoIsLiked(it) }
            .collect { list.add(it) }

    }

    suspend fun mapUsers(list: MutableList<DataFlat.PhotosWithUser>): Flow<DataFlat.PhotosWithUser> =
        flow { for (a in list) emit(a) }


    suspend fun getUsers(i: DataFlat.PhotosWithUser): DataFlat.PhotosWithUser {
        val userObject: UserCollection.User? = i.user_uid?.let {
            firestore.document("$userCollection/${i.user_uid}").get().await()
                .let {
                    if (it != null) it.toObject(UserCollection.User::class.java) else throw CancellationException(
                        "PROB EMTY"
                    )
                }
        }
        i.user = userObject
        return i
    }

    suspend fun checkPhotoIsLiked(dataFlat: DataFlat.PhotosWithUser) =
        withContext(Dispatchers.Main) {
            val documentId =
                firestore.collection("$userCollection/${dataFlat.user_uid}/$userPhotosCollection/")
                    .whereEqualTo("photo_id", dataFlat.photo_id).get().await()
                    .also { dataFlat.doc_id = it.documents.firstOrNull()?.id!! }
            dataFlat.me_liked =
                firestore.collection("$userCollection/${dataFlat.user_uid}/$userPhotosCollection/${documentId.documents.firstOrNull()?.id}/$photosLikesCollection")
                    .whereEqualTo("liker_id", auth.uid).get().await().documents.let { it.size > 0 }

            dataFlat

        }


    fun cancelJob() {
        if (job.isActive) {
            job.cancel()
        }
    }
}