package com.android.cameraapp.util.firebase

import com.android.cameraapp.data.data_models.DataFlat
import com.android.cameraapp.util.getCurrentDateInFormat
import com.android.cameraapp.util.getCurrentTime
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.await

abstract class FollowingResolver(val firebaseAuth: FirebaseAuth, val fireStore: FirebaseFirestore) :
    CurrentUser(firebaseAuth, fireStore) {
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

    suspend fun followUser(userUID: String, state: Int, resolve: (Int) -> Unit) = coroutineScope {
        val user = super.getCurrentUser()
        fireStore.collection("$userCollection/$userUID/$userFollowersCollection")
            .add(
                DataFlat.Followers(
                    user?.username!!,
                    user.uid,
                    user.photo_url,
                    getCurrentDateInFormat(),
                    getCurrentTime()
                )
            ).await()
        resolve(state)
    }

    suspend fun unfollowUser(userUID: String, state: Int, resolve: (Int) -> Unit) = coroutineScope {
        val user = super.getCurrentUser()
       val followingDocId = fireStore.collection("$userCollection/$userUID/$userFollowersCollection").whereEqualTo("follower_uid", user?.uid).get().await().documents.first().id
        fireStore.document("$userCollection/$userUID/$userFollowersCollection/$followingDocId").delete().await()
        resolve(state)
    }
}