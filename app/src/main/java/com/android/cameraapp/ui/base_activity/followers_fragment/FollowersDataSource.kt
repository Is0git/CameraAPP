package com.android.cameraapp.ui.base_activity.followers_fragment

import android.util.Log
import androidx.paging.PositionalDataSource
import com.android.cameraapp.data.data_models.UserCollection
import com.android.cameraapp.di.base_activity.followers_fragment.FollowersFragmentScope
import com.android.cameraapp.util.userCollection
import com.android.cameraapp.util.userPhotosCollection
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

const val TAG = "FollowersTAG"

@FollowersFragmentScope
class FollowersDataSource @Inject constructor(
    val auth: FirebaseAuth,
    val firestore: FirebaseFirestore
) :
    PositionalDataSource<UserCollection.Followers>() {

    lateinit var lastDocument: DocumentSnapshot
    override fun loadRange(
        params: LoadRangeParams,
        callback: LoadRangeCallback<UserCollection.Followers>
    ) {
        firestore.collection("$userCollection/${auth.uid}/$userPhotosCollection")
            .startAfter(lastDocument)
            .limit(params.loadSize.toLong())
            .get()
            .addOnCompleteListener {
                when {
                    it.isSuccessful -> {
                        lastDocument = it.result?.documents?.last()!!
                        it.result?.toObjects(UserCollection.Followers::class.java)
                            .also { callback.onResult(it!!) }
                    }
                    it.isCanceled -> Log.i(TAG, "FAILED ON loadRANGE: ${it.exception?.message}")
                }
            }

    }

    override fun loadInitial(
        params: LoadInitialParams,
        callback: LoadInitialCallback<UserCollection.Followers>
    ) {
        firestore.collection("$userCollection/${auth.uid}/$userPhotosCollection").get()
            .addOnCompleteListener {
                when {
                    it.isSuccessful -> {
                        lastDocument = it.result?.documents?.last()!!
                        it.result?.toObjects(UserCollection.Followers::class.java)
                            .also { callback.onResult(it!!, 0, it.size) }
                    }
                    it.isCanceled -> Log.i(TAG, "FAILED: ${it.exception?.message}")
                }
            }
    }

}