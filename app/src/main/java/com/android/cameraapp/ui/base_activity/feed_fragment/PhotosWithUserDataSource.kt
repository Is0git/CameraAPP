package com.android.cameraapp.ui.base_activity.feed_fragment

import androidx.paging.PositionalDataSource
import com.android.cameraapp.data.data_models.DataFlat
import com.android.cameraapp.data.data_models.UserCollection
import com.android.cameraapp.util.userCollection
import com.android.cameraapp.util.userLikesCollection
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

class PhotosWithUserDataSource(
    val auth: FirebaseAuth,
    val firestore: FirebaseFirestore,
    val job: Job
) : PositionalDataSource<DataFlat.PhotosWithUser>() {
    var document: DocumentSnapshot? = null
    override fun loadRange(
        params: LoadRangeParams,
        callback: LoadRangeCallback<DataFlat.PhotosWithUser>
    ) {
        document?.let {
            val list = mutableListOf<DataFlat.PhotosWithUser>()
            CoroutineScope(Dispatchers.Main + job).launch {
                getPhotos(list, params)
                callback.onResult(list)
            }
        }

    }

    override fun loadInitial(
        params: LoadInitialParams,
        callback: LoadInitialCallback<DataFlat.PhotosWithUser>
    ) {
        val list = mutableListOf<DataFlat.PhotosWithUser>()
        CoroutineScope(Dispatchers.Main + job).launch {
            getPhotos(list, params)
            callback.onResult(list, 0, list.size)
        }

    }


    suspend fun <T> getPhotos(list: MutableList<DataFlat.PhotosWithUser>, params: T) {
        val result: MutableList<DataFlat.PhotosWithUser>? = when (params) {
            is LoadInitialParams -> {
                firestore.collectionGroup("photos").whereEqualTo("private", true)
                    .orderBy("time_in_long", Query.Direction.DESCENDING)
                    .orderBy("liked_time_long", Query.Direction.DESCENDING)
                    .limit(params.requestedLoadSize.toLong()).get().await()
                    .also {
                        if (it?.documents != null && it.documents.size > 0) document =
                            it.documents.last() else throw CancellationException()
                    }.let { it.toObjects(DataFlat.PhotosWithUser::class.java) }
            }
            is LoadRangeParams -> {
                firestore.collection("$userCollection/${auth.uid}/$userLikesCollection")
                    .orderBy("time_in_long", Query.Direction.DESCENDING)
                    .orderBy("liked_time_long", Query.Direction.DESCENDING)
                    .startAfter(document!!)
                    .limit(params.loadSize.toLong()).get().await()
                    .also {
                        if (it?.documents != null && it.documents.size > 0) document =
                            it.documents.last() else throw CancellationException()
                    }.let { it.toObjects(DataFlat.PhotosWithUser::class.java) }
            }
            else -> null
        }



        mapUsers(result!!).map { getUsers(it) }.collect { list.add(it) }

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

    fun cancelJob() {
        if (job.isActive) {
            job.cancel()
        }
    }
}