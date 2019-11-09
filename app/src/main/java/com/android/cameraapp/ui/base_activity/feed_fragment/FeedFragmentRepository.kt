package com.android.cameraapp.ui.base_activity.feed_fragment

import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.android.cameraapp.data.data_models.DataFlat
import com.android.cameraapp.data.data_models.UserCollection
import com.android.cameraapp.di.base_activity.feed_fragment.FeedFragmentScope
import com.android.cameraapp.util.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@FeedFragmentScope
data class FeedFragmentRepository @Inject constructor(
    val pagedList: LiveData<PagedList<DataFlat.PhotosWithUser>>,
    val dataSource: PhotosWithUserDataSource,
    val firebaseAuth: FirebaseAuth,
    val fireStore: FirebaseFirestore
) {

    suspend fun likePhoto(photo: DataFlat.PhotosWithUser, likesCount: TextView) = coroutineScope {
        launch {increaseCounterUI(likesCount)}
        val photoDocument = fireStore.collection("$userCollection/${photo.user_uid}/$userPhotosCollection").whereEqualTo("photo_id", photo.photo_id).get().await().documents.firstOrNull()
        launch { addLikedPhoto(photoDocument!!) }
        launch { increaseLikeCounter(photoDocument!!, likesCount) }

    }

    suspend fun dislikePhoto(photo: DataFlat.PhotosWithUser, likesCount: TextView) {

    }
    suspend fun addLikedPhoto(photoDocument: DocumentSnapshot) {
        fireStore.collection("$userCollection/${photoDocument.get("user_uid")}/$userPhotosCollection/${photoDocument.id}/$photosLikesCollection")
            .add(
                UserCollection.PictureLikes(
                    firebaseAuth.uid,
                    firebaseAuth.currentUser?.displayName,
                    getCurrentDateInFormat(),
                    photoDocument.id,
                    getCurrentTime()
                )
            ).await()
    }

    suspend fun increaseLikeCounter(photoDocument: DocumentSnapshot, likesCount: TextView) {
        fireStore.document("$userCollection/${photoDocument.get("user_uid")}/$userPhotosCollection/${photoDocument.id}")
            .update("likes_number", FieldValue.increment(1)).await()
    }

    fun increaseCounterUI(likesCount: TextView) {
        val newLikesCount = likesCount.apply {
            val newValue = text.toString().toInt() + 1
            text = newValue.toString()
        }
    }
}