package com.android.cameraapp.ui.base_activity.photos_fragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.android.cameraapp.data.data_models.UserCollection
import com.android.cameraapp.di.base_activity.photo_fragment.PhotoFragmentScope
import com.android.cameraapp.ui.base_activity.BaseActivity
import com.android.cameraapp.util.userCollection
import com.android.cameraapp.util.userPhotosCollection
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

@PhotoFragmentScope
class PhotosFragmentRepository @Inject constructor(
    val pagedList: LiveData<PagedList<UserCollection.Photos>>

) {
    init {
        Log.d(TAG, "LOAD REPO")

    }




}