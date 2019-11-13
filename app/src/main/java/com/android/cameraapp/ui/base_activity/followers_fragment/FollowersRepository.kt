package com.android.cameraapp.ui.base_activity.followers_fragment

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.android.cameraapp.data.data_models.DataFlat
import com.android.cameraapp.data.data_models.UserCollection
import com.android.cameraapp.di.base_activity.followers_fragment.FollowersFragmentScope
import com.android.cameraapp.util.firebase.userCollection
import com.android.cameraapp.util.firebase.userFollowersCollection
import com.android.cameraapp.util.firebase.userPhotosCollection
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
class FollowersRepository @Inject constructor(val firebaseAuth: FirebaseAuth, val firestore: FirebaseFirestore, val adapter: FollowersAdapter) : EventListener<QuerySnapshot> {
    val follower = MutableLiveData<List<DataFlat.Followers>>()
    val followers = MutableLiveData<List<DataFlat.Followers>>()
    val mediatorLiveData = MediatorLiveData<List<DataFlat.Followers>>()
    var counter = 0
    lateinit var job: Job
    var listenerRegistration: ListenerRegistration = firestore.collection("$userCollection/${firebaseAuth.uid}/$userFollowersCollection")
        .orderBy("following_time_long", Query.Direction.DESCENDING).limit(1).addSnapshotListener(this)

    override fun onEvent(p0: QuerySnapshot?, p1: FirebaseFirestoreException?) {
     job =   CoroutineScope(Dispatchers.Main).launch {
            if (counter > 0) {
                if(p0?.documents?.isNotEmpty()!!) {
                    val item = p0.toObjects(DataFlat.Followers::class.java).first().let { getUsers(it) }
                    adapter.setNewItem(item)
                }
            }
            counter++
        }

    }

    fun getData() {
        mediatorLiveData.apply {
            addSource(follower) { data -> mediatorLiveData.value = data }
            addSource(followers) { data -> mediatorLiveData.value = data }
        }
    }

    suspend fun getAllFollowers() {
        val firstLoad =  firestore.collection("$userCollection/${firebaseAuth.uid}/$userFollowersCollection")
            .orderBy("following_time_long", Query.Direction.DESCENDING).get().await().toObjects(DataFlat.Followers::class.java)
        followers.value = firstLoad
        streamFollowers(firstLoad).map { getUsers(it) }.collect { adapter.addUserToFollower(it) }
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
        listenerRegistration.remove()
        if(job.isActive) job.cancel()
    }
}
