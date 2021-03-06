package com.android.cameraapp.ui.base_activity.following_fragment

import androidx.lifecycle.MutableLiveData
import androidx.paging.PositionalDataSource
import com.android.cameraapp.data.data_models.DataFlat
import com.android.cameraapp.data.data_models.UserCollection
import com.android.cameraapp.di.base_activity.following_fragment.FollowingFragmentScope
import com.android.cameraapp.util.States
import com.android.cameraapp.util.firebase.userCollection
import com.android.cameraapp.util.firebase.userFollowingCollection
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.concurrent.CancellationException
import javax.inject.Inject

const val TAG = "FollowingTAG"

@FollowingFragmentScope
class FollowingDataSource @Inject constructor(
    val auth: FirebaseAuth,
    val firestore: FirebaseFirestore,
    val job: Job
) :
    PositionalDataSource<DataFlat.Following>() {
    var taskState = MutableLiveData<States>()
    var lastDocument: DocumentSnapshot? = null
    override fun loadRange(
        params: LoadRangeParams,
        callback: LoadRangeCallback<DataFlat.Following>
    ) {
        val list = mutableListOf<DataFlat.Following>()
        lastDocument?.let {
            CoroutineScope(Dispatchers.Main + job).launch {
                taskState.postValue(States.START)
                getFollowing(list, params)
                taskState.postValue(States.FINISH)
                callback.onResult(list)
            }

        }
    }

    override fun loadInitial(
        params: LoadInitialParams,
        callback: LoadInitialCallback<DataFlat.Following>
    ) {
        val list = mutableListOf<DataFlat.Following>()
        CoroutineScope(Dispatchers.Main + job).launch {
            taskState.postValue(States.START)
            getFollowing(list, params)
            taskState.postValue(States.FINISH)
            callback.onResult(list, 0, list.size)
        }
    }

    suspend fun <T> getFollowing(
        list: MutableList<DataFlat.Following>,
        params: T
    ) {
        val result: List<DataFlat.Following>? = when (params) {
            is LoadInitialParams -> {
                firestore.collection("$userCollection/${auth.uid}/$userFollowingCollection")
                    .limit(params.requestedLoadSize.toLong())
                    .orderBy("following_time_long", Query.Direction.DESCENDING)
                    .get().await().also {
                        lastDocument =
                            if (it?.documents != null && it?.documents.size > 0) it.documents.last() else throw CancellationException(
                                "Empty"
                            )
                    }.toObjects(DataFlat.Following::class.java)
            }

            is LoadRangeParams -> {
                firestore.collection("$userCollection/${auth.uid}/$userFollowingCollection")
                    .orderBy("following_time_long", Query.Direction.DESCENDING)
                    .startAfter(lastDocument!!)
                    .limit(params.loadSize.toLong())
                    .get().await().also {
                        lastDocument =
                            if (it?.documents != null && it?.documents.size > 0) it.documents.last() else throw CancellationException(
                                "Empty"
                            )
                    }.toObjects(DataFlat.Following::class.java)
            }
            else -> null
        }
        streamFollowing(result!!).map { getUsers(it) }.collect { list.add(it) }


    }


    suspend fun streamFollowing(result: List<DataFlat.Following>): Flow<DataFlat.Following> =
        flow { for (i in result) emit(i) }

    suspend fun getUsers(i: DataFlat.Following): DataFlat.Following {
        if (i.user_uid != null) {
            val result: UserCollection.User =
                firestore.collection(userCollection).document("${i.user_uid}").get().await()
                    .let { it?.toObject(UserCollection.User::class.java)!! }
            i.user = result
        }
        return i
    }

    fun cancelJobs() {
        if (job.isActive) {
            job.cancel()
        }
    }
}




