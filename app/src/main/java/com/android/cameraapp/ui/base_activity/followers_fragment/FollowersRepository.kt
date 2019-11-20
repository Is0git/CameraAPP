package com.android.cameraapp.ui.base_activity.followers_fragment

import androidx.lifecycle.*
import com.android.cameraapp.data.data_models.DataFlat
import com.android.cameraapp.data.data_models.UserCollection
import com.android.cameraapp.di.base_activity.followers_fragment.FollowersFragmentScope
import com.android.cameraapp.util.States
import com.android.cameraapp.util.firebase.userCollection
import com.android.cameraapp.util.firebase.userFollowersCollection
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@FollowersFragmentScope
class FollowersRepository @Inject constructor(
    val firebaseAuth: FirebaseAuth,
    val firestore: FirebaseFirestore,
    val adapter: FollowersAdapter
) : EventListener<QuerySnapshot>, LifecycleObserver {
    val follower = MutableLiveData<List<DataFlat.Followers>>()
    val followers = MutableLiveData<List<DataFlat.Followers>>()
    val mediatorLiveData = MediatorLiveData<List<DataFlat.Followers>>()
    var job: Job? = null
    var listenerRegistration: ListenerRegistration? = null
    var queryUserID: String? = null
    val taskState = MutableLiveData<States>()

    override fun onEvent(p0: QuerySnapshot?, p1: FirebaseFirestoreException?) {
        job = CoroutineScope(Dispatchers.Main).launch {
            taskState.postValue(States.START)
            if (p0?.documents?.isNotEmpty()!!) {
                val items = p0.toObjects(DataFlat.Followers::class.java)
//                    followers.value = items
                getAllFollowers(items)
                followers.value = items
                taskState.postValue(States.FINISH)
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun setListener() {
        mediatorLiveData.apply {
            addSource(followers) { data -> mediatorLiveData.value = data }
        }
        val queryID = queryUserID ?: firebaseAuth.uid
        firestore.collection("$userCollection/${queryID}/$userFollowersCollection")
            .orderBy("following_time_long", Query.Direction.DESCENDING).addSnapshotListener(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun stopListener() {
        mediatorLiveData.removeSource(followers)
        listenerRegistration?.remove()
        if (job?.isActive!!) job?.cancel()

    }

    suspend fun getAllFollowers(items: List<DataFlat.Followers>) {
        streamFollowers(items).map { getUsers(it) }.collect()
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


    fun clearListener() {
        listenerRegistration?.remove()
        if (job?.isActive!!) job?.cancel()
    }
}
