package com.android.cameraapp.ui.base_activity.photos_fragment

import android.util.Log
import androidx.paging.PositionalDataSource
import com.android.cameraapp.data.data_models.UserCollection
import com.android.cameraapp.di.base_activity.photo_fragment.PhotoFragmentScope
import com.android.cameraapp.ui.base_activity.BaseActivity
import com.android.cameraapp.util.firebase.userCollection
import com.android.cameraapp.util.firebase.userPhotosCollection
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import javax.inject.Inject

const val TAG = "HomeFragmentTAG"

//Callbacky code cause coroutines apparently are not suited for android architecture pagination library... :(
@PhotoFragmentScope
class PhotosDataSource @Inject constructor(
    val auth: FirebaseAuth,
    val fireStore: FirebaseFirestore,
    val currentUser: FirebaseAuth,
    val activity: BaseActivity

) : PositionalDataSource<UserCollection.Photos>() {
    var triggered: Boolean = false
    var listener: EventListener<QuerySnapshot>? = null
    lateinit var lastDocument: DocumentSnapshot
    override fun loadRange(
        params: LoadRangeParams,
        callback: LoadRangeCallback<UserCollection.Photos>
    ) {

        fireStore.collection("$userCollection/${auth.uid}/$userPhotosCollection")
            .limit(params.loadSize.toLong())
            .startAfter(lastDocument)
            .get().addOnCompleteListener { task ->
                when {
                    task.isSuccessful && task.result?.size() != 0 -> {
                        task.result?.also { lastDocument = it.documents.last() }
                            ?.toObjects(UserCollection.Photos::class.java).let {
                                callback.onResult(it!!)
                            }
                    }
                }
            }
    }

    override fun loadInitial(
        params: LoadInitialParams,
        callback: LoadInitialCallback<UserCollection.Photos>
    ) {
        Log.d(TAG, "LOAD INITIAL")


        fireStore.collection("$userCollection/${auth.uid}/$userPhotosCollection")
            .limit(params.requestedLoadSize.toLong()).get().addOnCompleteListener { task ->
                when {
                    task.isSuccessful && task.result?.size() != 0 -> {
                        task.result?.also { lastDocument = it.documents.last() }
                            ?.toObjects(UserCollection.Photos::class.java).let {
                                callback.onResult(it!!, 0, it.size)
                            }
                    }
                }
            }

    }

}