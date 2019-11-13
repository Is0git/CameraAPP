package com.android.cameraapp.ui.base_activity.likes_fragment

import com.android.cameraapp.data.data_models.DataFlat
import com.android.cameraapp.data.data_models.UserCollection
import com.android.cameraapp.di.base_activity.likes_fragment.LikesFragmentScope
import com.android.cameraapp.util.firebase.userCollection
import com.android.cameraapp.util.firebase.userLikesCollection
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

@LikesFragmentScope
class LikesFragmentRepository @Inject constructor(
    val firebaseAuth: FirebaseAuth,
    val firestore: FirebaseFirestore,
    val adapter: LikesAdapter
) : EventListener<QuerySnapshot> {
    lateinit var job: Job

    var listenerRegistration: ListenerRegistration = firestore.collection("$userCollection/${firebaseAuth.uid}/$userLikesCollection")
    .orderBy("time_in_long", Query.Direction.DESCENDING).addSnapshotListener(this)

    override fun onEvent(p0: QuerySnapshot?, p1: FirebaseFirestoreException?) {
        job = CoroutineScope(Dispatchers.Main).launch {

            if (p0?.documents?.isNotEmpty()!!) {
                val items = p0.toObjects(DataFlat.Likes::class.java)
                getLikers(items)
                adapter.submitList(items)
            }
        }
    }

    suspend fun getLikers(items: List<DataFlat.Likes>) {
        streamFollowers(items).map { getUsers(it) }.collect()
    }

    suspend fun streamFollowers(result: List<DataFlat.Likes>): Flow<DataFlat.Likes> =
        flow { for (i in result) emit(i) }

    suspend fun getUsers(i: DataFlat.Likes): DataFlat.Likes {
        if (i.liker_id != null) {
            val result: UserCollection.User =
                firestore.document("$userCollection/${i.liker_id}").get().await()
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