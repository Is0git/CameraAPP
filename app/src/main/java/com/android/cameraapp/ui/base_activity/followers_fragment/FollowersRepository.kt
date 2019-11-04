package com.android.cameraapp.ui.base_activity.followers_fragment

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.android.cameraapp.data.data_models.UserCollection
import com.android.cameraapp.di.base_activity.followers_fragment.FollowersFragmentScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

@FollowersFragmentScope
class FollowersRepository @Inject constructor(val pagedList: LiveData<PagedList<UserCollection.Followers>>) {



}