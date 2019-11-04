package com.android.cameraapp.ui.base_activity.following_fragment

import android.util.Log
import androidx.paging.PositionalDataSource
import com.android.cameraapp.data.data_models.UserCollection
import com.android.cameraapp.di.base_activity.following_fragment.FollowingFragmentScope
import com.android.cameraapp.ui.base_activity.followers_fragment.TAG
import com.android.cameraapp.util.userCollection
import com.android.cameraapp.util.userFollowingCollection
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import javax.inject.Inject
const val TAG = "FollowingTAG"
@FollowingFragmentScope
class FollowingDataSource @Inject constructor(val auth: FirebaseAuth, val firestore: FirebaseFirestore) :
    PositionalDataSource<UserCollection.Following>() {

    var lastDocument: DocumentSnapshot? = null
    override fun loadRange(
        params: LoadRangeParams,
        callback: LoadRangeCallback<UserCollection.Following>
    ) {
        lastDocument?.let {
            firestore.collection("$userCollection/$auth.uid/$userFollowingCollection")
                .limit(params.loadSize.toLong())
                .orderBy("following_time_long", Query.Direction.DESCENDING)
                .get().addOnCompleteListener {
                    when {
                        it.isSuccessful && it.result?.size() != 0 -> {
                            lastDocument = it.result?.documents?.last()!!
                            it.result?.toObjects(UserCollection.Following::class.java)
                                .also { callback.onResult(it!!) }
                        }
                        it.isCanceled -> Log.i(TAG, "FAILED: ${it.exception?.message}")
                    }
                }
        }

    }

    override fun loadInitial(
        params: LoadInitialParams,
        callback: LoadInitialCallback<UserCollection.Following>
    ) {
        firestore.collection("$userCollection/${auth.uid}/$userFollowingCollection")
            .limit(params.requestedLoadSize.toLong())
            .orderBy("following_time_long", Query.Direction.DESCENDING)
            .get().addOnCompleteListener {
                Log.d(TAG, "RES: ${it.result?.size()}")
                when {
                    it.isSuccessful && it.result?.size() != 0 -> {
                        lastDocument = it.result?.documents?.last()!!
                        it.result?.toObjects(UserCollection.Following::class.java)
                            .also { callback.onResult(it!!, 0, it.size) }

                    }
                    it.isCanceled -> Log.i(TAG, "FAILED: ${it.exception?.message}")
                }
            }
    }
}