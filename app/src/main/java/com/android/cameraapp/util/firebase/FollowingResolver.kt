package com.android.cameraapp.util.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.tasks.await

abstract class FollowingResolver(val firebaseAuth: FirebaseAuth, val fireStore: FirebaseFirestore) {
    companion object {
        const val IS_FOLLOWING: Int = 1
        const val IS_NOT_FOLLOWING: Int = 0
    }

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

    abstract fun isFollowing()

    abstract fun isNotFollowing()


}