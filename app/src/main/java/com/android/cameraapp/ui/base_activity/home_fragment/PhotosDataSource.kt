package com.android.cameraapp.ui.base_activity.home_fragment

import androidx.paging.PositionalDataSource
import com.android.cameraapp.data.data_models.UserCollection
import com.android.cameraapp.di.base_activity.home_fragment.HomeFragmentScope
import com.android.cameraapp.util.userCollection
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

@HomeFragmentScope
class PhotosDataSource @Inject constructor(val user: FirebaseUser, val fireStore: FirebaseFirestore) : PositionalDataSource<UserCollection.Photos>() {
    override fun loadRange(
        params: LoadRangeParams,
        callback: LoadRangeCallback<UserCollection.Photos>
    ) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadInitial(
        params: LoadInitialParams,
        callback: LoadInitialCallback<UserCollection.Photos>
    ) {
        fireStore.collection("$userCollection/${user.displayName}")
    }
}