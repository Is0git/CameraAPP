package com.android.cameraapp.ui.base_activity.likes_fragment

import androidx.paging.PositionalDataSource
import com.android.cameraapp.data.data_models.DataFlat
import com.android.cameraapp.data.data_models.UserCollection
import com.android.cameraapp.di.base_activity.likes_fragment.LikesFragmentScope
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
import javax.inject.Inject

@LikesFragmentScope
class LikesFragmentDataSource @Inject constructor(
    val auth: FirebaseAuth,
    val firestore: FirebaseFirestore,
    val job: Job
) : PositionalDataSource<DataFlat.Likes>() {
    var document: DocumentSnapshot? = null

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<DataFlat.Likes>) {
        document?.let {
            val list = mutableListOf<DataFlat.Likes>()
            CoroutineScope(Dispatchers.Main + job).launch {
                getLikes(list, params)
                callback.onResult(list)
            }
        }

    }

    override fun loadInitial(
        params: LoadInitialParams,
        callback: LoadInitialCallback<DataFlat.Likes>
    ) {
        val list = mutableListOf<DataFlat.Likes>()
        CoroutineScope(Dispatchers.Main + job).launch {
            getLikes(list, params)
            callback.onResult(list, 0, list.size)
        }
    }

    suspend fun <T> getLikes(list: MutableList<DataFlat.Likes>, params: T) {
        val result: MutableList<DataFlat.Likes>? = when (params) {
            is LoadInitialParams -> {
                firestore.collection("$userCollection/${auth.uid}/$userLikesCollection")
                    .orderBy("liked_time_long", Query.Direction.DESCENDING)
                    .limit(params.requestedLoadSize.toLong()).get().await()
                    .also {
                        if (it?.documents != null && it.documents.size > 0) document =
                            it.documents.last() else throw CancellationException()
                    }.let { it.toObjects(DataFlat.Likes::class.java) }
            }
            is LoadRangeParams -> {
                firestore.collection("$userCollection/${auth.uid}/$userLikesCollection")
                    .orderBy("liked_time_long", Query.Direction.DESCENDING)
                    .startAfter(document!!)
                    .limit(params.loadSize.toLong()).get().await()
                    .also {
                        if (it?.documents != null && it.documents.size > 0) document =
                            it.documents.last() else throw CancellationException()
                    }.let { it.toObjects(DataFlat.Likes::class.java) }
            }
            else -> null
        }



        mapUsers(result!!).map { getUsers(it) }.collect { list.add(it) }

    }

    suspend fun mapUsers(list: MutableList<DataFlat.Likes>): Flow<DataFlat.Likes> = flow { for (a in list) emit(a) }


    suspend fun getUsers(i: DataFlat.Likes): DataFlat.Likes {
        val userObject: UserCollection.User? = i.user_uid?.let {
            firestore.document("$userCollection/${i.user_uid}").get().await()
                .let {
                    if (it != null) it.toObject(UserCollection.User::class.java) else throw CancellationException("PROB EMTY")
                }
        }
        i.user = userObject
        return i
    }

   fun cancelJob() {
        if(job.isActive) {
            job.cancel()
        }
    }
}