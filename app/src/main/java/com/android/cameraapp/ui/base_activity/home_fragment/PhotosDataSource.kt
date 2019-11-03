package com.android.cameraapp.ui.base_activity.home_fragment

import android.util.Log
import androidx.paging.PositionalDataSource
import com.android.cameraapp.data.data_models.UserCollection
import com.android.cameraapp.di.base_activity.home_fragment.HomeFragmentScope
import com.android.cameraapp.util.userCollection
import com.android.cameraapp.util.userPhotosCollection
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

const val TAG = "HomeFragmentTAG"

@HomeFragmentScope
class PhotosDataSource @Inject constructor(
    val user: FirebaseUser,
    val fireStore: FirebaseFirestore
) : PositionalDataSource<UserCollection.Photos>() {

    lateinit var lastDocument: DocumentSnapshot
    override fun loadRange(
        params: LoadRangeParams,
        callback: LoadRangeCallback<UserCollection.Photos>
    ) {
        CoroutineScope(Dispatchers.Main).launch {
            val snapshot = fireStore.collection("$userCollection/${user.uid}/$userPhotosCollection")
                .startAfter(lastDocument)
                .limit(params.loadSize.toLong())
                .get().apply {
                    when {
                        isSuccessful -> Log.i(TAG, "succesfully uploaded")
                        else -> throw CancellationException("FAILED TO UPLOAD: ${exception?.message}")
                    }

                }.await().also { lastDocument = it.documents.last() }
                .toObjects(UserCollection.Photos::class.java).let { callback.onResult(it) }
        }
    }

    override fun loadInitial(
        params: LoadInitialParams,
        callback: LoadInitialCallback<UserCollection.Photos>
    ) {
        CoroutineScope(Dispatchers.Main).launch {
            val querySnapshot =
                fireStore.collection("$userCollection/${user.uid}/$userPhotosCollection")
                    .limit(params.pageSize.toLong()).get()
                    .apply {
                        when {
                            isSuccessful -> Log.i(TAG, "succesfully uploaded")
                            else -> throw CancellationException("FAILED TO UPLOAD: ${exception?.message}")
                        }
                    }.await()

            callback.onResult(
                querySnapshot.toObjects(UserCollection.Photos::class.java),
                params.requestedStartPosition,
                params.pageSize
            ).also { lastDocument = querySnapshot.documents.last() }
        }

    }

}