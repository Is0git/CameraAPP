package com.android.cameraapp.util.firebase

import com.android.cameraapp.data.data_models.DataFlat
import com.android.cameraapp.data.data_models.UserCollection
import com.android.cameraapp.util.getCurrentDateInFormat
import com.android.cameraapp.util.getCurrentTime
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

abstract class FollowingResolver(val firebaseAuth: FirebaseAuth, val fireStore: FirebaseFirestore) :
    CurrentUser(firebaseAuth, fireStore), EventListener<QuerySnapshot> {
    val userJob: Job by lazy { CoroutineScope(Dispatchers.Main).launch { user = getCurrentUser() } }
    var user: UserCollection.User? = null

    companion object {
        const val IS_FOLLOWING: Int = 1
        const val IS_NOT_FOLLOWING: Int = 0
    }

    lateinit var photoDocID: String
    var registration: ListenerRegistration? = null

    open suspend fun checkIfFollow(followerUID: String) {
        fireStore.collection("$userCollection/$followerUID/$userFollowersCollection")
            .whereEqualTo("follower_uid", firebaseAuth.uid).get().await().documents.apply {
            when {
                size == 1 -> isFollowing()
                size == 0 -> isNotFollowing()
                else -> throw CancellationException("Something went wrong with collection, query size can't be bigger than 1")
            }
        }
    }

    open fun getComments(photo: DataFlat.PhotosWithUser) {
        registration =
            fireStore.collection("$userCollection/${photo.user_uid}/$userPhotosCollection/${photo.doc_id}/$photoCommentsCollection")
                .orderBy("comment_date_long", Query.Direction.ASCENDING).addSnapshotListener(this)
    }

    abstract fun isFollowing()

    abstract fun isNotFollowing()

    suspend fun followUser(userUID: String, state: Int, resolve: (Int) -> Unit) = coroutineScope {
        if (userUID != auth.uid) {
            fireStore.collection("$userCollection/$userUID/$userFollowersCollection")
                .add(
                    DataFlat.Followers(
                        user?.username!!,
                        user?.uid,
                        user?.photo_url,
                        getCurrentDateInFormat(),
                        getCurrentTime()
                    )
                ).await()
            resolve(state)
            fireStore.collection("$userCollection/${user?.uid}/$userFollowingCollection").add(
                UserCollection.Following(
                    getCurrentDateInFormat(), "", userUID, "N/A", getCurrentTime()
                )
            ).await()
        }
        resolve(state)
    }

    suspend fun unfollowUser(userUID: String, state: Int, resolve: (Int) -> Unit) = coroutineScope {
        if (userUID != auth.uid) {
            val followingDocId =
                fireStore.collection("$userCollection/$userUID/$userFollowersCollection")
                    .whereEqualTo("follower_uid", user?.uid).get().await().let {
                        if (it.documents.isNotEmpty()) it.documents.first().id else throw CancellationException(
                            "EMPTY QUERY LIST"
                        )
                    }
            fireStore.document("$userCollection/$userUID/$userFollowersCollection/$followingDocId")
                .delete().await()
            removeFollowing(userUID, user)
            resolve(state)

        }
    }

    suspend fun removeFollowing(userUID: String, user: UserCollection.User?) {
        val docId = fireStore.collection("$userCollection/${user?.uid}/$userFollowingCollection")
            .whereEqualTo("user_uid", userUID).get().await()
            .let { if (it.documents.size > 0) it.documents.first().id else CancellationException("NULL") }
        fireStore.document("$userCollection/${user?.uid}/$userFollowingCollection/$docId").delete()
    }


    inline fun removeListeners(job: Job, clearJob: (Job) -> Unit) {
        registration?.remove()
        if (userJob.isActive) userJob.cancel()
        clearJob(job)
    }
}