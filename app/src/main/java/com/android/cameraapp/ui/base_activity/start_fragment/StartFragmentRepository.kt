package com.android.cameraapp.ui.base_activity.start_fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.cameraapp.data.data_models.UserCollection
import com.android.cameraapp.di.base_activity.start_fragment.StartFragmentScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@StartFragmentScope
class StartFragmentRepository @Inject constructor(
    val firestore: FirebaseFirestore,
    val auth: FirebaseAuth
) {
    var data: MutableLiveData<UserCollection.User> = MutableLiveData()

    suspend fun getUserData() : LiveData<UserCollection.User> {
        val uid = auth.currentUser?.uid
        CoroutineScope(Dispatchers.IO).launch {
            val documents = firestore.collection("users").whereEqualTo("uid", uid).get().await()
            launch(Dispatchers.Main) { documents.documents.firstOrNull()?.toObject(UserCollection.User::class.java).also { data.postValue(it) } }
        }
        return data
    }

}
