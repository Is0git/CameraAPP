package com.android.cameraapp.ui.base_activity.search_fragment

import androidx.lifecycle.MutableLiveData
import com.android.cameraapp.data.data_models.UserCollection
import com.android.cameraapp.di.base_activity.search_fragment.SearchFragmentScope
import com.android.cameraapp.util.firebase.userCollection
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@SearchFragmentScope
class SearchRepository @Inject constructor(
    val firebaseAuth: FirebaseAuth,
    val fireStore: FirebaseFirestore
) {
    lateinit var searchJob: Job
    var searchResults: MutableLiveData<List<UserCollection.User>> =
        MutableLiveData<List<UserCollection.User>>()

    suspend fun searchForUsers(searchKey: String) {
        if (!::searchJob.isInitialized || !searchJob.isActive) {
            startASearch(searchKey)
        } else {
            searchJob.cancel()
            startASearch(searchKey)
        }
    }

    suspend fun startASearch(searchKey: String) {
        searchJob = coroutineScope {
            launch {
                delay(1000)
                fireStore.collection("$userCollection/").orderBy("username").startAt(searchKey).endAt("$searchKey+\uf8ff").get()
                    .await()
                    .let { querySnapshot ->
                        if (querySnapshot.documents.isNotEmpty()) querySnapshot.toObjects(
                            UserCollection.User::class.java
                        ).also {
                            searchResults.value= it
                        } else throw CancellationException("Empty search")
                    }
            }

        }
    }
}