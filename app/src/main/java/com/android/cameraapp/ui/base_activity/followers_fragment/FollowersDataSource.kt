package com.android.cameraapp.ui.base_activity.followers_fragment

import android.annotation.SuppressLint
import androidx.paging.PositionalDataSource
import com.android.cameraapp.data.data_models.DataFlat
import com.android.cameraapp.data.data_models.UserCollection
import com.android.cameraapp.di.base_activity.followers_fragment.FollowersFragmentScope
import com.android.cameraapp.util.firebase.userCollection
import com.android.cameraapp.util.firebase.userFollowersCollection
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

const val TAG = "FollowersTAG"

@FollowersFragmentScope
class FollowersDataSource @Inject constructor(
    val auth: FirebaseAuth,
    val firestore: FirebaseFirestore,
    val jobs: Job
) :
    PositionalDataSource<DataFlat.Followers>() {
    var lastDocument: DocumentSnapshot? = null
    var finalChannel: Channel<DataFlat.Followers> = Channel()
    override fun loadRange(
        params: LoadRangeParams,
        callback: LoadRangeCallback<DataFlat.Followers>
    ) {
        val list = mutableListOf<DataFlat.Followers>()
        lastDocument?.let {
            CoroutineScope(Dispatchers.Main).launch {
                getFollowers(list, params)
                callback.onResult(list)

            }
        }
    }

    @SuppressLint("CheckResult")
    override fun loadInitial(
        params: LoadInitialParams,
        callback: LoadInitialCallback<DataFlat.Followers>
    ) {
        val list = mutableListOf<DataFlat.Followers>()
        CoroutineScope(Dispatchers.Main).launch {
            getFollowers(list!!, params)
            callback.onResult(list, 0, list.size)

        }

    }

    suspend fun <T> getFollowers(
        list: MutableList<DataFlat.Followers>,
        params: T
    ) {
        val result: List<DataFlat.Followers>? = when (params) {
            is LoadInitialParams -> {
                firestore.collection("$userCollection/${auth.uid}/$userFollowersCollection")
                    .limit(params.requestedLoadSize.toLong())
                    .orderBy("following_time_long", Query.Direction.DESCENDING)
                    .get().await().also {
                        lastDocument =
                            if (it?.documents != null && it?.documents.size > 0) it.documents.last() else throw CancellationException(
                                "Empty"
                            )
                    }.toObjects(DataFlat.Followers::class.java)
            }

            is LoadRangeParams -> {
                firestore.collection("$userCollection/${auth.uid}/$userFollowersCollection")
                    .orderBy("following_time_long", Query.Direction.DESCENDING)
                    .startAfter(lastDocument!!)
                    .limit(params.loadSize.toLong())
                    .get().await().also {
                        lastDocument =
                            if (it?.documents != null && it?.documents.size > 0) it.documents.last() else throw CancellationException(
                                "Empty"
                            )
                    }.toObjects(DataFlat.Followers::class.java)
            }
            else -> null
        }
        streamFollowers(result!!).map { getUsers(it) }.collect { list.add(it) }


    }


    suspend fun streamFollowers(result: List<DataFlat.Followers>): Flow<DataFlat.Followers> =
        flow { for (i in result) emit(i) }

    suspend fun getUsers(i: DataFlat.Followers): DataFlat.Followers {
        if (i.follower_uid != null) {
            val result: UserCollection.User =
                firestore.document("$userCollection/${i.follower_uid}").get().await()
                    .let { it.toObject(UserCollection.User::class.java)!! }
            i.user = result
        }
        return i
    }

    fun cancelJobs() {
        if (jobs.isActive) {
            jobs.cancel()
        }
    }
}

